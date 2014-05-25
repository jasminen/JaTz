package model.gameMaze.solver;


import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import common.Keys;


/**
 * Maze Domain class. holds a data member of maze.
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public class MazeDomain implements Domain, Serializable {
	
	private static final long serialVersionUID = 1L;
	private Maze maze;
	
	/**
	 * Default C'tor - default maze c'tor
	 */
	public MazeDomain() {
		this.maze = new Maze();
	}
	
	/**
	 * C'tor
	 * @param maze
	 */
	public MazeDomain(Maze maze) {
		this.maze=maze;
	}
	
	
	
	@Override
	public ArrayList<MazeAction> getMazeActions(Node node) {
		Point point = (Point) node.getNode();
		ArrayList<MazeAction> actions = new ArrayList<MazeAction>();
		
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++) {
				if(i==0 && j==0)
					continue;
				int x= point.x + i;
				int y= point.y + j;
				if(maze.get(x,y)!= Keys.WALL && maze.get(x, y)!= -1)
					actions.add(new MazeAction(i,j));
			}
		}
		return actions;
		
	}



	@Override
	public Node getStartNode() {
		return maze.getStartNode();
	}



	@Override
	public Node getGoalNode() {
		return maze.getGoalNode();
	}


	/**
	 * maze getter
	 * @return maze
	 */
	public Maze getMaze() {
		return maze;
	}



	/**
	 * maze setter
	 * @param maze
	 */
	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	
	@Override
	 public String toString(){
		return "Class MazeDomain: "+maze.toString();
	 }

}
