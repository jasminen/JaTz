package model;

import java.net.InetSocketAddress;

import common.State;

/**
 * Model interface that support all possible moves and actions
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public interface Model  {
	
	/**
	 * Move Up
	 */
	public void moveUp();
	
	/**
	 * Move Down
	 */
	public void moveDown();
	
	/**
	 * Move Left
	 */
	public void moveLeft();
	
	/**
	 * Move Right
	 */
	public void moveRight();
	
	/**
	 * Move Diagonal Left Up
	 */
	public void moveDiagonalLeftUp();
	
	/**
	 * Move Diagonal Right Up
	 */
	public void moveDiagonalRightUp();
	
	/**
	 * Move Diagonal Left Down
	 */
	public void moveDiagonalLeftDown();
	
	/**
	 * Move Diagonal Right Down
	 */
	public void moveDiagonalRightDown();
	
	
	
	
	/**
	 * Save game (model) to xml file
	 * @param path
	 */
	public void save(String path);
	
	/**
	 * Load game (model) from xml file 
	 * @param path
	 */
	public void load(String path);
	
	/**
	 * Restart the game
	 */
	public void restart();
	
	/**
	 * Undo move
	 */
	public void undo();
	
	/**
	 * Start new game
	 */
	public void newGame();
	
	
	
	/**
	 * Connect to server
	 * @param socketAddress
	 */
	public void connectToServer(InetSocketAddress socketAddress);
	
	/**
	 * Disconnect from server
	 */
	public void disconnectFromServer();
	
	/**
	 * Get hints about the next best moves (as number of iterations)
	 * @param iterations
	 */
	public void getHint(Integer iterations);
	
	/**
	 * Solve the game completely
	 */
	public void fullSolver();
	
	/**
	 * Returns the last state
	 * @return lastState
	 */
	public State getState();


}
