package edu.immune.boardgames.tictac.sp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.immune.boardgames.tictac.common.Player;

/**
 * Wrapper on {@link Player} to support socket connectivity<br>
 * Provides {@linkplain ObjectInputStream} and {@linkplain ObjectOutputStream} mapped to each player
 * 
 * @author Lalit Mehra
 * @see Player
 *
 */
public class ClientSocket {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Player player;

	public Player getPlayer() {
		return player;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Socket getSocket() {
		return this.socket;
	}

	public void sendMessage(String message) throws IOException {
		oos.writeObject(message);
	}

	public Object receiveMessage() throws ClassNotFoundException, IOException {
		return ois.readObject();
	}
	
	public void close() throws IOException {
		ois.close();
		oos.close();
		socket.close();
	}

}
