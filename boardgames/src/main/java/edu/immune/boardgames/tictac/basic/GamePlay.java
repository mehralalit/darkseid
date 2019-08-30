package edu.immune.boardgames.tictac.basic;

import edu.immune.boardgames.tictac.common.Player;

/**
 * Used to prepare and instantiate a game by creating and passing {@link Player} 
 * 
 * @author Lalit Mehra
 *
 */
public class GamePlay {

	public static void main(String[] args) {
		Player player1 = new Player("Deepak", true, 'x');
		Player player2 = new Player("Raman", true, 'o');
		Game game = Game.prepareGame(player1, player2);
		game.play();
	}
	
}