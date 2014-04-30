package model.gameMaze;

import model.AbsModel;
import model.State;

public class GameMazeModel extends AbsModel {
	



	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void moveDiagonalLeftUp() {
		
	}
	
	@Override
	public void moveDiagonalRightUp() {
		
	}
	
	@Override
	public void moveDiagonalLeftDown(){
		
	}
	
	@Override
	public void moveDiagonalRightDown(){
		
	}
	
	

	public void doAction(int r, int c) {
		State newState = new MazeAction(r,c).doAction(getState());
		if(!newState.equals(getState()))
			states.add(newState);
	}
	
	

	@Override
	protected State getStartState() {
		// TODO Auto-generated method stub
		return null;
	}



}
