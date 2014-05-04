package model.gameMaze.solver;

import org.eclipse.swt.graphics.Point;





/*
 * Holds x,y and adds them to the Node's point . x is rows and y columns.
 */

public class MazeAction {

	private int x,y;
	
	public MazeAction(int x, int y) {
		this.x=x;
		this.y=y;
		
	}
	
	public Node doAction(Node node) {
		Point oldPoint = node.getNode();
		Point newPoint = new Point(oldPoint.x+this.x, oldPoint.y+this.y);
		return new Node(newPoint);
	}


	public String getName() {
		String upDown="", leftRight="", straightDiagonal="diagonal";
		if(x==0 || y==0)
			straightDiagonal="straight";
		if(x==-1)
			upDown="up";
		else if(x==1)
			upDown="down";
		if(y==-1)
			leftRight="left";
		else if(y==1)
			leftRight="right";
		return "Go "+straightDiagonal+" "+leftRight+" "+upDown;
	}
	
	
	@Override
	 public String toString(){
		return "Class MazeAction: x=" + x + ", y=" + y + ", action="+getName();
	 }
	
	
}
