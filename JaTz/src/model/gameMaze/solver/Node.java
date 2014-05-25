package model.gameMaze.solver;

import org.eclipse.swt.graphics.Point;

/**
 * Node class - holds a data member of type Point that represents the node,
 * and g and f that are used in search algorithms .
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */


public class Node implements Comparable<Node> {
	
	private final Point node;
	private double f;
	private double g;
	
	/**
	 * C'tor
	 * @param node
	 */
	public Node(Point node) {
		this.node = node;
		this.f=0;
		this.g=0;
	}
	
	
	/**
	 * node getter
	 * @return node
	 */
	public Point getNode() {
		return node;
	}
	
	/**
	 * f getter
	 * @return f
	 */
	public double getF() {
		return f;
	}
	
	/**
	 * f setter
	 * @param f
	 */
	public void setF(double f) {
		this.f = f;
	}
	
	/**
	 * g getter
	 * @return g
	 */
	public double getG() {
		return g;
	}
	
	/**
	 * g setter
	 * @param g
	 */
	public void setG(double g) {
		this.g = g;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Node))
			return false;

		Node other = (Node) obj;
		return this.node.equals(other.node);
	}

	@Override
	public int compareTo(Node s) {
		return (int) (this.f-s.getF());
	}
		
	@Override
	public int hashCode() {
		return node.hashCode();
	}
		
	
	@Override
	 public String toString(){
		return "Class Node: "+node.toString();
	 }
	
	}
		
	
	


