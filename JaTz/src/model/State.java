package model;

public class State {

	private int[][] board;
	private int score;
	
	public State(int[][] board, int score) {
		this.board=board;
		this.score=score;
	}

	public int[][] getBoard() {
		return board;
	}

	public int getScore() {
		return score;
	}
	
	
}
