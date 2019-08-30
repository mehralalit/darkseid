package edu.immune.boardgames.tictac.sp;

import java.io.IOException;

import edu.immune.boardgames.tictac.common.Player;

/**
 * Client Server adaptation of Tic Toc Toe game
 * The game can be played between two {@link Player} only connected via sockets<br>
 * 
 * @author Lalit Mehra
 *
 */
public class Game {

	/**
	 * 3x3 board for tic tac toe
	 */
	private char[][] board;
	
	/**
	 * count of free blocks on the board at any time t
	 */
	private static int freeBlocks = 9;
	
	/**
	 * Count of free blocks in the board. This value is immutable and is used as a reference 
	 */
	private static final int blocks = 9;
	
	
	/**
	 * default representation of the char[] elements used to check the empty blocks
	 */
	private static char u0000 = '\u0000';

	private ClientSocket cs1;
	private ClientSocket cs2;

	/**
	 * Instantiates a new game with two players
	 * 
	 * @param cs1
	 * @param cs2
	 * 
	 * @see ClientSocket
	 */
	private Game(ClientSocket cs1, ClientSocket cs2) {
		board = new char[3][3];
		this.cs1 = cs1;
		this.cs2 = cs2;
	}

	/**
	 * Prepare a new game with two {@link Player} 
	 * 
	 * @param cs1
	 * @param cs2
	 * @return
	 * 
	 * @see ClientSocket
	 */
	public static Game prepareGame(ClientSocket cs1, ClientSocket cs2) {
		return new Game(cs1, cs2);
	}

	/**
	 * @return current representation of the board marking filled and unfilled blocks
	 */
	private String getBoardStat() {
		String boardStat = "";
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				boardStat += board[r][c] == u0000 ? '.' : board[r][c];
			}
			boardStat += System.lineSeparator();
		}
		return boardStat;
	}

	/**
	 * Sends current representation of the board to both the players
	 * 
	 * @throws IOException
	 */
	private void sendBoardStat() throws IOException {
		String boardStat = "Here's the current board stat: \n" + getBoardStat();
		this.sendMessage(boardStat);
	}

	/**
	 * Send message to both the players 
	 * 
	 * @param message
	 * @throws IOException
	 */
	private void sendMessage(String message) throws IOException {
		cs1.sendMessage(message);
		cs2.sendMessage(message);
	}

	/**
	 * Starts the game play and expects input from the player(s) via socket connection<br>
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void play() throws IOException, ClassNotFoundException {
		ClientSocket ccs = this.cs1;
		int row = 0, col = 0;
		
		sendBoardStat();
		String sndmsg = "", rcvmsg = "";
		String[] xy = new String[2];
		
		while (freeBlocks > 0) {
			
			while (true) {
				sndmsg = ccs.getPlayer().getName() + ", please enter block location(x,y) separated by a comma <>" + System.lineSeparator();
				ccs.sendMessage(sndmsg);
				rcvmsg = (String) ccs.receiveMessage();
				xy = rcvmsg.split(",");
				
				row = Integer.valueOf(xy[0]) - 1;
				col = Integer.valueOf(xy[1]) - 1;

				if ((row <= 2 && row >= 0) && (col <= 2 && col >= 0)) {
					break;
				} else {
					ccs.sendMessage("Incorrect block location entered, please enter a value between 1-3");
				}
			}

			if (board[row][col] != u0000) {
				ccs.sendMessage("Block filled, please re-enter");
				continue;
			} else {
				board[row][col] = ccs.getPlayer().getMark();

				if ((u0000 != board[0][0] && board[0][0] == board[0][1] && board[0][0] == board[0][2])
						|| (u0000 != board[1][0] && board[1][0] == board[1][1] && board[1][0] == board[1][2])
						|| (u0000 != board[2][0] && board[2][0] == board[2][1] && board[2][0] == board[2][2])
						|| (u0000 != board[0][0] && board[0][0] == board[1][1] && board[0][0] == board[2][2])
						|| (u0000 != board[2][0] && board[2][0] == board[1][1] && board[2][0] == board[0][2])
						|| (u0000 != board[0][0] && board[0][0] == board[1][0] && board[0][0] == board[2][0])
						|| (u0000 != board[0][1] && board[0][1] == board[1][1] && board[0][1] == board[2][1])
						|| (u0000 != board[0][2] && board[0][2] == board[1][2] && board[0][2] == board[2][2])) {
					sendBoardStat();

					sndmsg = ccs.getPlayer().getName() + " Won! Chances Played: " + ((blocks - freeBlocks) + 1);
					sendMessage(sndmsg);
					
					break;
				}

				ccs = ccs == cs1 ? cs2 : cs1;
				freeBlocks--;
			}

			sendBoardStat();

		}

		if (freeBlocks == 0) {
			sendMessage("Match Drawn!");
		}

		cs1.close();
		cs2.close();
	}

}
