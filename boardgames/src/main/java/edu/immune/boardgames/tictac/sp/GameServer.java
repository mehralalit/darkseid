package edu.immune.boardgames.tictac.sp;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;

/**
 * Instantiates a GameServer over socket connection<br>
 * It takes a port number from the user and works as a singleton<br>
 * It only allows only two connections at any given time
 * @author Lalit Mehra
 *
 */
public class GameServer extends ServerSocket {

	private static GameServer _instance;
	
	/**
	 * 
	 * @param port
	 * @return
	 * @throws IOException
	 */
	public static GameServer getInstance(int port) throws IOException {
		return _instance!=null?_instance:new GameServer(port);
	}
	
	private GameServer(int port) throws IOException {
		super(port, 2, Inet4Address.getLocalHost());
	}
	
}
