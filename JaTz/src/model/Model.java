package model;

public interface Model {
	
	public void moveUp();
	public void moveDown();
	public void moveLeft();
	public void moveRight();
	
	public void moveDiagonalLeftUp();
	public void moveDiagonalRightUp();
	public void moveDiagonalLeftDown();
	public void moveDiagonalRightDown();
	
	public void restart();
	public void undo();
	public void newGame();
	
	public State getState();


}
