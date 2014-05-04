package model.gameMaze.solver;

import java.util.ArrayList;

public interface Domain {
	
	public ArrayList<MazeAction> getMazeActions(Node node); 
	public Node getStartNode();
	public Node getGoalNode();


}
