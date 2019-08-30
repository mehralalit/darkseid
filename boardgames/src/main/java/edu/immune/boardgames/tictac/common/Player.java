package edu.immune.boardgames.tictac.common;

import java.io.Serializable;

/**
 * The Player<br>
 * has three attributes:<br>
 * 1. {@link String} name<br>
 * 2. {@link Boolean} bit<br>
 * 3. {@link Character} mark<br>
 * 
 * @author Lalit Mehra
 *
 */
public class Player implements Serializable {

	/**
	 * serialized UUID
	 */
	private static final long serialVersionUID = -532850336883508839L;
	
	private String name;
	private boolean bot;
	private char mark;
	
	/**
	 * creates a player and setup complete information
	 * 
	 * @param name
	 * @param bot
	 * @param mark
	 */
	public Player(String name, boolean bot, char mark) {
		this.name = name;
		this.bot = bot;
		this.mark = mark;
	}
	
	/**
	 * create a bot and name it 'Sentinel'. This should be used in cases where a bot is playing with a human
	 * 
	 * @param mark
	 */
	public Player(char mark) {
		this.name = "Sentinel";
		this.bot = true;
		this.mark = mark;
	}
	
	/**
	 * create a non bot player with no predefined mark <br>
	 * mark should be set before starting the game
	 * 
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.bot = false;
	}

	public String getName() {
		return name;
	}
	
	public boolean isBot() {
		return bot;
	}
	
	public char getMark() {
		return mark;
	}
	
	public void setMark(char mark) {
		this.mark = mark;
	}
	
	public boolean isHuman() {
		return !isBot();
	}
	
	public String toString() {
		return name;
	}
	
}
