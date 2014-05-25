package model.gameMaze;

import org.eclipse.swt.graphics.Point;

import common.Keys;
import common.State;
import model.Action;

/**
 * Holds x,y and adds them to the State's Mouse point . x is rows and y columns.
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public class MazeAction implements Action {

	private int x,y;
	
	/**
	 * C'tor
	 * @param x
	 * @param y
	 */
	public MazeAction(int x, int y) {
		this.x=x;
		this.y=y;
	}

	/**
	 * Adds x,y to the Mouse point.
	 */
	
	@Override
	public State doAction(State state) {
		Point oldPoint = state.findCell(Keys.MOUSE);
		Point newPoint = new Point(oldPoint.x+this.x,oldPoint.y+this.y);
		State newState = new State(state);
		newState.setMode(Keys.IN_PROGRESS);
		if (newState.cellExists(newPoint.x, newPoint.y) && newState.getCell(newPoint.x, newPoint.y) != -1 && newState.getCell(newPoint.x, newPoint.y) != Keys.WALL) {
			newState.setCell(oldPoint.x,oldPoint.y , Keys.EMPTY);
			
			if(this.x == 0 ^ this.y == 0)
				newState.setScore(state.getScore()+10);
			
			else if(this.x != 0 && this.y != 0){
				newState.setScore(state.getScore()+15);
			}
			
			if(newPoint.equals(state.findCell(Keys.CHEESE))) {
				newState.setCell(newPoint.x, newPoint.y, Keys.MOUSE_AND_CHEESE);
			} else
				newState.setCell(newPoint.x, newPoint.y, Keys.MOUSE);
			
		}
		return newState;
	}

}
