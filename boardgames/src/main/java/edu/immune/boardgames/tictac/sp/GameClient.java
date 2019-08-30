package edu.immune.boardgames.tictac.sp;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Game Client imitates a player and connects to the Server to instantiate a game<br>
 * The game continues until it is not completed. It connects to the port <b>1331</b>
 * 
 * @author Kurama
 * @see GameServer
 *
 */
public class GameClient extends Socket {

	private static int port = 1331;

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		
		try (Socket socket = new Socket(Inet4Address.getLocalHost(), port); Scanner scanner = new Scanner(System.in)) {
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject("Hi! I wish to play a game");

			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			try {
				while (!socket.isClosed()) {

					String message = (String) ois.readObject();
					System.out.println(message);

					if (message.contains("<>"))
						oos.writeObject(scanner.next());

				}
			} catch (EOFException e) {
				System.out.println("Game Completed! Quiting!");
			}
		}

	}
}