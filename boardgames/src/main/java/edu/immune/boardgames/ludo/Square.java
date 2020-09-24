package edu.immune.boardgames.ludo;

public class Square {

	private boolean blocked;
	private SquareColor color;
	private SquareType type;
	private int x;
	private int y;

	private void setupBlockSquares() {
		if (((x >= 0 && x <= 5) && (y >= 0 && y <= 5)) || (x == 6 && y == 0)) {
			color = SquareColor.RED;
			if (x == 6 && y == 0) {
				type = SquareType.START;
			} else {
				type = SquareType.BLOCK;
			}
		} else if (((x >= 0 && x <= 5) && (y >= 9 && y <= 14)) || (x == 0 && y == 8)) {
			color = SquareColor.BLUE;
			if (x == 0 && y == 8) {
				type = SquareType.START;
			} else {
				type = SquareType.BLOCK;
			}
		} else if (((x >= 9 && x <= 14) && (y >= 0 && y <= 5)) || (x == 14 && y == 6)) {
			color = SquareColor.YELLOW;
			if (x == 14 && y == 6) {
				type = SquareType.START;
			} else {
				type = SquareType.BLOCK;
			}
		} else if (((x >= 9 && x <= 14) && (y >= 9 && y <= 14)) || (x == 8 && y == 14)) {
			color = SquareColor.GREEN;
			if (x == 8 && y == 14) {
				type = SquareType.START;
			} else {
				type = SquareType.BLOCK;
			}
		}
	}

	private void setupHomeSquares() {
		if (x == 7 && (y >= 1 && y <= 5)) {
			color = SquareColor.RED;
			type = SquareType.HOME;
		} else if (x == 7 && (y >= 9 && y <= 13)) {
			color = SquareColor.GREEN;
			type = SquareType.HOME;
		} else if (y == 7 && (x >= 1 && x <= 5)) {
			color = SquareColor.BLUE;
			type = SquareType.HOME;
		} else if (y == 7 && (x >= 9 && x <= 13)) {
			color = SquareColor.YELLOW;
			type = SquareType.HOME;
		}

	}

	private void setup() {
		setupBlockSquares();
		setupHomeSquares();
	}

	public Square(int x, int y) {
		this.x = x;
		this.y = y;
		blocked = true;
		color = SquareColor.WHITE;
		type = SquareType.PATH;
		setup();
	}

	public void block() {
		blocked = true;
	}

	public void unblock() {
		blocked = false;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public char getColor() {
		return color.getColor();
	}

	public char type() {
		return type.getType();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Square [blocked=");
		builder.append(blocked);
		builder.append(", color=");
		builder.append(color);
		builder.append(", type=");
		builder.append(type);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append("]");
		return builder.toString();
	}

}
