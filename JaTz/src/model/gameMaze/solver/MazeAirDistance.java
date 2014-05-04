package model.gameMaze.solver;


import java.io.Serializable;

import org.eclipse.swt.graphics.Point;


/*
 * Heuristic of distance - square root of rows^2 + columns^2, rows from goal and columns from goal.
 */

public class MazeAirDistance implements Distance , Serializable{


	private static final long serialVersionUID = 1L;

	@Override
	public double getDistance(Node from, Node to) {
		Point pFrom = (Point) from.getNode();
		Point pTo = (Point) to.getNode();
		
		int rows = Math.abs(pTo.x - pFrom.x);
		int columns = Math.abs(pTo.y - pFrom.y);
		
		return (Math.sqrt((rows*rows)+(columns*columns)) * 10);		
	}

	@Override
	 public String toString(){
		return "Class MazeAirDistance: square root of rows^2 + columns^2";
	 }
}
