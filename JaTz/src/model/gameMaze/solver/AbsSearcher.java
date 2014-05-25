package model.gameMaze.solver;


import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;


/**
 * Abstract class that is type of searcher, this class represents all searchers
 * that uses openList and closedList.
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public abstract class AbsSearcher  {

	private int evaluatedNodesNum;
	private PriorityQueue<Node> openList;
	private HashSet<Node> closedList;
	private HashMap<Node, Node> knownNodes;
	
	/**
	 * Default C'tor
	 */
	public AbsSearcher() {
		this.openList= new PriorityQueue<Node>();
		this.closedList = new HashSet<Node>();
		this.evaluatedNodesNum=0;
		this.knownNodes= new HashMap<Node, Node>();
	}
	
	/**
	 * Get known node or add to known nodes
	 * @param node
	 * @return node
	 */
	public Node getNode(Node node) {
		if (knownNodes.containsKey(node))
			return knownNodes.get(node);
		knownNodes.put(node, node);
		return node;
	}

	
	/**
	 * Pull from open list
	 * @return node
	 */
	public Node pollFromOpenList() {
		this.evaluatedNodesNum++;
		return this.openList.poll();
	}
	
	/**
	 * Add to open list
	 * @param node
	 * @return true/false
	 */
	public boolean addToOpenList(Node node) {
		return this.openList.add(node);
	}
	
	/**
	 * Is open list empty
	 * @return true/false
	 */
	public boolean OpenListIsEmpty() {
		return this.openList.isEmpty();
	}
	
	/**
	 * Does open list contains node
	 * @param node
	 * @return true/false
	 */
	public boolean openListContains(Node node) {
		return this.openList.contains(node);
	}
	
	/**
	 * Add to closed list
	 * @param node
	 * @return true/false
	 */
	public boolean addToClosedList(Node node) {
		return this.closedList.add(node);
	}
	
	/**
	 * Remove from closed list
	 * @param node
	 * @return true/false
	 */
	public boolean removeFromClosedList(Node node) {
		return this.closedList.remove(node);
	}
	
	/**
	 * Does closed list contains node
	 * @param node
	 * @return true/false
	 */
	public boolean closedListContains(Node node) {
		return this.closedList.contains(node);
	}
	
	/**
	 * evaluatedNodesNum getter
	 * @return evaluatedNodesNum
	 */
	public int getNumOfEvaluatedNodes() {
		return this.evaluatedNodesNum;
	}
	
	/**
	 * Abstract search method
	 * @param start
	 * @param goal
	 * @return double
	 */
	public abstract double search(Node start, Node goal);
	
	
	

}
