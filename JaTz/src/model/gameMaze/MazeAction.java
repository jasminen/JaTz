package model.gameMaze;

import org.eclipse.swt.graphics.Point;

import model.Action;
import model.State;
import controller.Keys;


/*
 * Holds x,y and adds them to the State's point . x is rows and y columns.
 */

public class MazeAction implements Action {

	private int x,y;
	
	public MazeAction(int x, int y) {
		this.x=x;
		this.y=y;
		
	}

	
	
	@Override
	public State doAction(State state) {
		
		Point oldPoint = state.findCell(Keys.MOUSE);
		Point newPoint = new Point(oldPoint.x+this.x,oldPoint.y+this.y);
		State newState = new State(state);
		if (newState.cellExists(newPoint.x, newPoint.y)) {
			newState.setCell(oldPoint.x,oldPoint.y , 0);
			newState.setCell(newPoint.x, newPoint.y, Keys.MOUSE);
			
			if(this.x - oldPoint.x == 0 || this.y - oldPoint.y == 0)
				newState.setScore(state.getScore()+10);
			
			else if(this.x - oldPoint.x != 0 && this.y - oldPoint.y != 0){
				newState.setScore(state.getScore()+15);
			}
			
		}
		
		return newState;
	}

}
