package model.gameMaze.solver;

/**
 * Class that holds a node with an action
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */
public class NodeMazeActionPair {
	
	private Node Node;
	private MazeAction action;
	
	/**
	 * C'tor
	 * @param Node
	 * @param action
	 */
	public NodeMazeActionPair(Node Node, MazeAction action) {
		super();
		this.Node = Node;
		this.action = action;
	}
	
	/**
	 * node getter
	 * @return node
	 */
	public Node getNode() {
		return Node;
	}
	
	/**
	 * node setter
	 * @param Node
	 */
	public void setNode(Node Node) {
		this.Node = Node;
	}
	
	/**
	 * action getter 
	 * @return action
	 */
	public MazeAction getAction() {
		return action;
	}
	
	/**
	 * action setter
	 * @param action
	 */
	public void setAction(MazeAction action) {
		this.action = action;
	}
	

}
