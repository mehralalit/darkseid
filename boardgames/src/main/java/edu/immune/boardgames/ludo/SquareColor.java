package edu.immune.boardgames.ludo;

public enum SquareColor {

	RED('R'), BLUE('B'), GREEN('G'), YELLOW('Y'), WHITE('W');

	private SquareColor(char color) {
		this.color = color;
	}

	private char color;

	public char getColor() {
		return color;
	}

}
