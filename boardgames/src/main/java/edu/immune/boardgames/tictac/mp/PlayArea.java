package edu.immune.boardgames.tictac.mp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

import edu.immune.boardgames.tictac.common.Player;

/**
 * Currently not in use
 * 
 * @author Lalit Mehra
 *
 */
public class PlayArea implements Serializable {

	/**
	 * serialized UUID
	 */
	private static final long serialVersionUID = 6551596367516237728L;
	
	private Player player;
	private Scanner scanner;
	private PrintWriter writer;
	private ObjectInputStream connectingInputStream;
	private ObjectOutputStream connectingOutputStream;

	public PlayArea(Player player) {
		this.scanner = new Scanner(System.in);
		this.writer = new PrintWriter(System.out);
		this.player = player;
	}

	public void setConnectingInputStream(ObjectInputStream connectingInputStream) {
		this.connectingInputStream = connectingInputStream;
	}

	public void setConnectingOutputStream(ObjectOutputStream connectingOutputStream) {
		this.connectingOutputStream = connectingOutputStream;
	}

	public void sendMessage(String message) throws IOException {
		if(connectingOutputStream == null)
			throw new IllegalStateException("Invalid Connecting Output Stream");
		
		connectingOutputStream.writeObject(message);
	}
	
	public void receiveMessage() throws ClassNotFoundException, IOException {
		if(connectingInputStream == null)
			throw new IllegalStateException("Invalid Connecting Input Stream");
		
		String message = (String) connectingInputStream.readObject();
		writer.print(message);
		writer.flush();
	}
}
