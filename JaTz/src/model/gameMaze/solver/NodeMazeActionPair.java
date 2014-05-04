package model.gameMaze.solver;


public class NodeMazeActionPair {
	
	private Node Node;
	private MazeAction action;
	
	public NodeMazeActionPair(Node Node, MazeAction action) {
		super();
		this.Node = Node;
		this.action = action;
	}
	
	public Node getNode() {
		return Node;
	}
	public void setNode(Node Node) {
		this.Node = Node;
	}
	public MazeAction getAction() {
		return action;
	}
	public void setAction(MazeAction action) {
		this.action = action;
	}
	

}
