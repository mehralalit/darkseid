package edu.immune.boardgames.ludo;

public enum Location {

	RED(SquareColor.RED, 6, 0, 0, 0), 
	BLUE(SquareColor.BLUE, 0, 8, 0, 14), 
	GREEN(SquareColor.GREEN, 8, 14, 14, 14),
	YELLOW(SquareColor.YELLOW, 14, 6, 14, 0);

	private SquareColor color;
	private int startX;
	private int startY;
	private int restX;
	private int restY;

	Location(SquareColor color, int sx, int sy, int rx, int ry) {
		this.color = color;
		this.startX = sx;
		this.startY = sy;
		this.restX = rx;
		this.restY = ry;
	}

	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public int getRestX() {
		return restX;
	}
	
	public int getRestY() {
		return restY;
	}
	
	public SquareColor getColor() {
		return color;
	}
}
