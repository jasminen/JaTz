package model.gameMaze.solver;


import java.io.Serializable;

import org.eclipse.swt.graphics.Point;

import controller.Keys;



/*
 * Maze class - holds a data member of type int[][] that represents a 2D matrix
 * that is our maze.
 * Generates the maze randomly where the size of the maze is between 3-22 (rows or columns).
 */


public class Maze implements Serializable{

	private static final long serialVersionUID = 1L;
	private int[][] maze;
	
	public Maze() {
	
	int rows = (int) (Math.random()*20)+3;
	int columns = (int) (Math.random()*20)+3;
	maze = new int[rows][columns];
	for (int i=0; i<=(rows*columns*0.1); i++) {
		int r=(int) (Math.random() * rows);
		int c=(int) (Math.random() * columns);
		maze[r][c]=-1;
		
	}

	maze[0][0]=Keys.MOUSE;
	maze[rows-1][columns-1]=Keys.CHEESE;
		
	}
	
	
	
	public Maze(Maze maze)
	{
		this.maze=maze.getMaze();
	}
	
	
	public Maze(int[][] maze)
	{
		this.maze=maze;
	}

	
	public int get(int x, int y)
	{
		if (x >= maze.length || y>= maze[0].length || x<0 || y<0)
			return -1;
		return maze[x][y];
	}
	
	public Node getStartNode()
	{
		Point start = new Point(maze.length-1,maze[0].length-1);
		return ( new Node(start));
	}
	
	public Node getGoalNode()
	{
		Point goal = new Point(0,0);
		return ( new Node(goal));	
	}
	
	
	public int[][] getMaze() {
		return maze;
	}

	public void setMaze(int[][] maze) {
		this.maze = maze;
	}
	
	public void setMaze(Maze maze) {
		this.maze = maze.getMaze();
	}

	
	@Override
	 public String toString(){
		  String str= "Class Maze: the maze:";
		  for (int i = 0; i < maze.length; i++) {
			  str=str.concat("\n");
			  for (int j = 0; j < maze[0].length; j++) {
			      str=str.concat(maze[i][j]+"  ");
			      if(maze[i][j]!=-1)
			    	  str=str.concat(" ");
			    }
			}
		  str=str.concat("\n");
		  return str;
		  }
			  
	
	

}
