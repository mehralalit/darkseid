package edu.immune.boardgames.ludo;

public enum SquareType {

	HOME('H'), PATH('P'), START('S'), END('E'), BLOCK('B');

	private SquareType(char type) {
		this.type = type;
	}

	private char type;

	public char getType() {
		return type;
	}

}
