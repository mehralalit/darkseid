package edu.immune.boardgames.ludo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.immune.boardgames.ludo.Board.BoardBuilder;

public class BoardTest {

	@Test
	public void buildBoard() {
		BoardBuilder builder = new BoardBuilder();
		Board board = builder.prepare().build();

		Square[][] matrix = board.getMatrix();
		
		System.out.println("Color ... ");
		
		printHeaderRow();
		
		for (int x = 0; x < matrix.length; x++) {
			printFirstColumn(x);
			for (int y = 0; y < matrix.length; y++) {
				printElement(y, matrix[x][y].getColor());
			}
			System.out.println();
		}

		System.out.println();

		System.out.println("Blocked or Not ... ");
		
		printHeaderRow();
		
		for (int x = 0; x < matrix.length; x++) {
			printFirstColumn(x);
			for (int y = 0; y < matrix.length; y++) {
				printElement(y, matrix[x][y].isBlocked() ? 'T' : 'F');
			}
			System.out.println();
		}

		System.out.println();
		
		System.out.println("Type ... ");
		
		printHeaderRow();

		for (int x = 0; x < matrix.length; x++) {
			printFirstColumn(x);
			for (int y = 0; y < matrix.length; y++) {
				printElement(y, matrix[x][y].type());
			}
			System.out.println();
		}

		assertTrue(board instanceof Board);
	}
	
	private void printHeaderRow() {
		System.out.print("  ");
		for (int x = 0; x < 15; x++) {
			System.out.print(" " + x);
		}
		System.out.println();
	}

	private void printFirstColumn(int x) {
		System.out.print(x<10?x + "  ":x + " ");
	}
	
	private void printElement(int y, char e) {
		System.out.print(y<10?e + " ":e + "  ");
	}
}
