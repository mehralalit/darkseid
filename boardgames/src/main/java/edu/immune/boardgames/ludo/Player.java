package edu.immune.boardgames.ludo;

public class Player {
	
	private Location location; 
	private String name;
	
	private int currentX;
	private int currentY;
	
	private boolean active;
	private boolean reached;
	
	public Player(int x, int y) {
		name = location.getColor().name();
		deactivate();
	}
	
	public void activate() {
		currentX = location.getStartX();
		currentY = location.getStartY();
		active = true;
	}
	
	public void deactivate() {
		currentX = location.getRestX();
		currentY = location.getRestY();
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isReached() {
		return reached;
	}
	
	public String getName() {
		return name;
	}

}
