package model.gameMaze.solver;

import java.util.ArrayList;

/**
 * Domain interface
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */
public interface Domain {
	
	/**
	 * Get maze legal actions
	 * @param node
	 * @return array list of maze actions
	 */
	public ArrayList<MazeAction> getMazeActions(Node node);
	
	/**
	 * Get start node
	 * @return start node
	 */
	public Node getStartNode();
	
	/**
	 * Get goal node
	 * @return goal node
	 */
	public Node getGoalNode();


}
