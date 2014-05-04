package model.gameMaze.solver;


import java.io.Serializable;

import org.eclipse.swt.graphics.Point;


/*
 * Straight moves cost 10 and diagonal cost 15.
 */

public class MazeDistanceG implements Distance, Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	public double getDistance(Node from, Node to) {
		Point pFrom = (Point) from.getNode();
		Point pTo = (Point) to.getNode();
		
		if(pTo.x - pFrom.x == 0 && pTo.y - pFrom.y == 0)
			return 0;
		
		if(pTo.x - pFrom.x == 0 || pTo.y - pFrom.y == 0)
			return 10;
		
		return 15;
	}

	@Override
	 public String toString(){
		return "Class MazeDistanceG: Straight = 10 and diagonal = 15 ";
	 }
}
