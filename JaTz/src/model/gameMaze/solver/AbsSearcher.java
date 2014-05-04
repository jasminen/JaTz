package model.gameMaze.solver;


import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;



/*
 * Abstract class that is type of searcher, this class represents all searchers
 * that uses openList and closedList.
 */

public abstract class AbsSearcher  {

	private int evaluatedNodesNum;
	private PriorityQueue<Node> openList;
	private HashSet<Node> closedList;
	private HashMap<Node, Node> knownNodes;
	
	public AbsSearcher() {
		this.openList= new PriorityQueue<Node>();
		this.closedList = new HashSet<Node>();
		this.evaluatedNodesNum=0;
		this.knownNodes= new HashMap<Node, Node>();
	}
	
	public Node getNode(Node node) {
		if (knownNodes.containsKey(node))
			return knownNodes.get(node);
		knownNodes.put(node, node);
		return node;
	}

	
	
	public Node pollFromOpenList() {
		this.evaluatedNodesNum++;
		return this.openList.poll();
	}
	
	public boolean addToOpenList(Node node) {
		return this.openList.add(node);
	}
	
	public boolean OpenListIsEmpty() {
		return this.openList.isEmpty();
	}
	
	public boolean openListContains(Node node) {
		return this.openList.contains(node);
	}
	
	public boolean addToClosedList(Node node) {
		return this.closedList.add(node);
	}
	
	public boolean removeFromClosedList(Node node) {
		return this.closedList.remove(node);
	}
	
	public boolean closedListContains(Node node) {
		return this.closedList.contains(node);
	}
	
	
	public int getNumOfEvaluatedNodes() {
		return this.evaluatedNodesNum;
	}
	
	
	public abstract double search(Node start, Node goal);
	
	
	

}
