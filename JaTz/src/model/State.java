package model;

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
		return board.clone();
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
	
}
