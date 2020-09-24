package edu.immune.boardgames.ludo;

public class Board {

	public static class BoardBuilder {

		private Board board;

		public BoardBuilder() {
			this.board = new Board();
		}

		public BoardBuilder prepare() {
			Square[][] matrix = board.matrix;

			for (int x = 0; x < boardSize; x++) {
				for (int y = 0; y < boardSize; y++) {

					// place square object in matrix section
					matrix[x][y] = new Square(x, y);

					if ((x > 5 && x < 9) || (y > 5 && y < 9)) {
						if ((x > 5 && x < 9) && (y > 5 && y < 9))
							continue;

						matrix[x][y].unblock();
					}
				}
			}

			return this;
		}

		public Board build() {
			return this.board;
		}
	}

	private static final int boardSize = 15;
	private Square[][] matrix;

	private Board() {
		this.matrix = new Square[boardSize][boardSize];
	}

	public Square[][] getMatrix() {
		return matrix;
	}

}
