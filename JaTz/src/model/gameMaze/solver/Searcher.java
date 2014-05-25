package model.gameMaze.solver;

/**
 * Searcher interface that represents algorithms of search
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public interface Searcher {

	/**
	 * Search for way from start to goal
	 * @param start
	 * @param goal
	 * @return
	 */
	public double search(Node start, Node goal) ;
	
	/**
	 * How many nodes were evaluated in the search
	 * @return NumOfEvaluatedNodes
	 */
	public int getNumOfEvaluatedNodes();

}
