package model.gameMaze.solver;

/**
 * Distance interface - distance from node to node
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */
public interface Distance {
	
	/**
	 * Distance from node to node
	 * @param from
	 * @param to
	 * @return double
	 */
	public double getDistance(Node from, Node to);

}
