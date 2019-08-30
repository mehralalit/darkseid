package edu.immune.boardgames.tictac.basic;

import java.util.Random;
import java.util.Scanner;

import edu.immune.boardgames.tictac.common.Player;

/**
 * Basic Adaptation of a Tic Tac Toe game<br>
 * The game can be played between two {@link Player} only where one of the player can be a bot as well<br>
 * Tic Tac Toe Basic currently uses random picking mechanism for <b>bot</b> to select the next possible coordinate to mark<br>
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
	private static Random random;

	private Player player1;
	private Player player2;
	
	private Scanner scanner = null;

	/**
	 * Instantiates a new game with two players
	 * 
	 * @param player1
	 * @param player2
	 */
	private Game(Player player1, Player player2) {
		board = new char[3][3];
		random = new Random();
		this.player1 = player1;
		this.player2 = player2;
		
		if(player1.isHuman() || player2.isHuman()) {
			scanner = new Scanner(System.in);
		}
	}

	/**
	 * Print the current placed markers on the board
	 */
	private void printBoard() {
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				System.out.print(board[r][c] == u0000 ? '.' : board[r][c]);
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Prepare a new game with two {@link Player} 
	 * 
	 * @param player1
	 * @param player2
	 * @return
	 */
	public static Game prepareGame(Player player1, Player player2) {
		return new Game(player1, player2);
	}

	/**
	 * Starts the game play and expects input from the player(s) through standard input mechanism<br>
	 * Input for the bot is managed by the game itself<br>
	 */
	public void play() {
		Player currentPlayer = this.player1;
		int row = 0, col = 0;
		while (freeBlocks > 0) {
			if (currentPlayer.isBot()) {
				row = random.nextInt(3);
				col = random.nextInt(3);
			} else {
				while(true) { 
					System.out.printf("%s, please enter block location(x,y) separated by a space \n", currentPlayer.getName());
					row = scanner.nextInt() - 1;
					col = scanner.nextInt() - 1;
					
					if((row <= 2 && row >= 0) && (col <= 2 && col >= 0)) {
						break;
					} else {
						System.out.println("Incorrect block location entered, please enter a value betweeb 1-3");
					}
				}
			}

			if (board[row][col] != u0000) {
				if(currentPlayer.isHuman()) {
					System.out.println("block filled, please re-enter");
				}
				continue;
			} else {
				board[row][col] = currentPlayer.getMark();

				if ((u0000 != board[0][0] && board[0][0] == board[0][1] && board[0][0] == board[0][2])
						|| (u0000 != board[1][0] && board[1][0] == board[1][1] && board[1][0] == board[1][2])
						|| (u0000 != board[2][0] && board[2][0] == board[2][1] && board[2][0] == board[2][2])
						|| (u0000 != board[0][0] && board[0][0] == board[1][1] && board[0][0] == board[2][2])
						|| (u0000 != board[2][0] && board[2][0] == board[1][1] && board[2][0] == board[0][2])
						|| (u0000 != board[0][0] && board[0][0] == board[1][0] && board[0][0] == board[2][0])
						|| (u0000 != board[0][1] && board[0][1] == board[1][1] && board[0][1] == board[2][1])
						|| (u0000 != board[0][2] && board[0][2] == board[1][2] && board[0][2] == board[2][2])) {
					printBoard();

					System.out.printf("%s Won! Chances Played: %s", currentPlayer.getName(),
							((blocks - freeBlocks) + 1));
					break;
				}

				currentPlayer = currentPlayer == player1 ? player2 : player1;
				freeBlocks--;
			}

			printBoard();

		}

		if (freeBlocks == 0) {
			System.out.println("Match Drawn!");
		}
		
		if(scanner != null) {
			scanner.close();
		}
		
	}

}
