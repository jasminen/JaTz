package model;

import java.util.Arrays;

import org.eclipse.swt.graphics.Point;

import controller.Keys;

public class State {

	private int[][] board;
	private int score;
	private int mode; // Keys.NEW_GAME, Keys.GAMEOVER, Keys.WIN, Keys.IN_PROGRESS

	public State(int[][] board, int score) {
		this.board = board;
		this.score = score;
		this.mode = Keys.NEW_GAME;
	}

	public State(int[][] board, int score, int mode) {
		this.board = board;
		this.score = score;
		this.mode = mode;
	}

	public State(State state) {
		this.board = state.getBoard();
		this.score = state.getScore();
		this.mode = state.getMode();
	}

	public int[][] getBoard() {
		int[][] newBoard = new int[this.board.length][this.board[0].length];
		for (int i = 0; i < this.board.length; i++)
			newBoard[i] = Arrays.copyOf(this.board[i], this.board[i].length);
		return newBoard;
	}

	public int getScore() {
		return score;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
			this.mode = mode;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setCell(int row, int column, int value) {
		if (cellExists(row, column))
			board[row][column] = value;
	}

	public Boolean cellExists(int row, int column) {
		if (row < board.length && column < board[0].length)
			return true;
		return false;
	}

	public Point findCell(int value) {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[0].length; j++) {
				if (board[i][j] == value) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String str = "Class State: the board:";
		for (int i = 0; i < board.length; i++) {
			str = str.concat("\n");
			for (int j = 0; j < board[0].length; j++) {
				str = str.concat(board[i][j] + "  ");
				if (board[i][j] != -1)
					str = str.concat(" ");
			}
		}
		str = str.concat("\n");
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State))
			return false;

		State other = (State) obj;

		for (int i = 0; i < this.board.length; i++)
			if (!Arrays.equals(this.board[i], other.getBoard()[i]))
				return false;

		if (this.score != other.getScore())
			return false;

		return true;
	}
}
