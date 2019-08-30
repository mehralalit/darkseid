package edu.immune.boardgames.tictac.sp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.immune.boardgames.tictac.common.Player;

/**
 * Game Arena starts the {@link GameServer} and waits for the two {@link Player} to prepare and instantiate the game<br>
 * Once both the players connect and provide their names the game is prepared and played
 * 
 * @author Lalit Mehra
 * @see GameServer
 * @see Game
 * @see GameClient
 * @see Player
 *
 */
public class GameArena {

	private static GameServer gameServer = null;
	private static int port = 1331;
	private static Game game = null;

	public static void main(String[] args) throws ClassNotFoundException, UnknownHostException {

		char mark = 'x';

		try {
			ClientSocket player1 = new ClientSocket();
			ClientSocket player2 = new ClientSocket();
			ClientSocket current = null;

			gameServer = GameServer.getInstance(port);

			while (player1.getSocket() == null || player2.getSocket() == null) {
				System.out.println("Waiting for a player");
				Socket socket = gameServer.accept();

				if (player1.getSocket() == null) {
					player1.setSocket(socket);
					player1.setOis(new ObjectInputStream(socket.getInputStream()));
					player1.setOos(new ObjectOutputStream(socket.getOutputStream()));
					current = player1;
				} else if (player2.getSocket() == null) {
					player2.setSocket(socket);
					player2.setOis(new ObjectInputStream(socket.getInputStream()));
					player2.setOos(new ObjectOutputStream(socket.getOutputStream()));
					current = player2;
				}

				String rcvmsg = (String) current.receiveMessage();
				System.out.println(rcvmsg);

				current.sendMessage("Please enter your Name <>");
				String name = (String) current.receiveMessage();

				Player player = new Player(name, false, mark);
				current.setPlayer(player);
				System.out.println(name + "! entered the game");

				String sndmsg = "Hi " + name + "! Welcome to the Game. Your mark is '" + mark + "'.";
				current.sendMessage(sndmsg);
				
				mark = 'o';

			}

			game = Game.prepareGame(player1, player2);
			game.play();
			
			System.out.println("Game Completed! Quiting!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
