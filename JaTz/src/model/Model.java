package model;

import java.net.InetSocketAddress;

import common.State;

public interface Model  {
	
	public void moveUp();
	public void moveDown();
	public void moveLeft();
	public void moveRight();
	
	public void moveDiagonalLeftUp();
	public void moveDiagonalRightUp();
	public void moveDiagonalLeftDown();
	public void moveDiagonalRightDown();
	
	
	public void save(String path);
	public void load(String path);
	public void restart();
	public void undo();
	public void newGame();
	
	public void connectToServer(InetSocketAddress socketAddress);
	public void disconnectFromServer();
	public void getHint(Integer iterations);
	public void fullSolver();
	public State getState();


}
