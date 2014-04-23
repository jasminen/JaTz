package model;

import java.util.Arrays;

public class State {

	private int[][] board;
	private int score;
	private int mode; //0-game still on, 1-game over, 2-win.
	
	
	public State(int[][] board, int score) {
		this.board=board;
		this.score=score;
		this.mode=0;
	}
	
	public State(int[][] board, int score, int mode) {
		this.board=board;
		this.score=score;
		this.mode=mode;
	}

	public int[][] getBoard() {
		int[][] newBoard = new int[this.board.length][this.board[0].length];
		for(int i=0; i<this.board.length; i++)
			newBoard[i]=Arrays.copyOf(this.board[i], this.board[i].length);
		return newBoard;
	}

	public int getScore() {
		return score;
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setMode(int mode) {
		if(mode <= 2 && mode >=0)
			this.mode=mode;
	}
	
	public void setCell(int row, int column, int value) {
		if (row<board.length && column<board[0].length)
			board[row][column]=value;
	}
	
	@Override
	 public String toString(){
		  String str= "Class State: the board:";
		  for (int i = 0; i < board.length; i++) {
			  str=str.concat("\n");
			  for (int j = 0; j < board[0].length; j++) {
			      str=str.concat(board[i][j]+"  ");
			      if(board[i][j]!=-1)
			    	  str=str.concat(" ");
			    }
			}
		  str=str.concat("\n");
		  return str;
		  }
	
}
