package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.graphics.Point;

/*
 * State - Two dimensional array representing the board.
 * 		   Score of this state.
 * 		   Mode - what is the status of the game. 
 */


public class State implements Serializable{

	private static final long serialVersionUID = 1L;
	private int[][] board;
	private int score;
	private int mode; // Keys.NEW_GAME, Keys.GAMEOVER, Keys.WIN, Keys.IN_PROGRESS
	private String msg = "";
	private boolean connectedToServer = false; 
	
	public State(){}

	public State(int[][] board, int score) {
		this.board = board;
		this.score = score;
		this.mode = Keys.IN_PROGRESS;
	}

	public State(int[][] board, int score, int mode) {
		this.board = board;
		this.score = score;
		this.mode = mode;
	}
	
	public State(int[][] board, int score, int mode, String msg) {
		this.board = board;
		this.score = score;
		this.mode = mode;
		this.msg = msg;
	}

	public State(State state) {
		this.board = state.getCopyBoard();
		this.score = state.getScore();
		this.mode = state.getMode();
		this.msg = state.getMsg();
	}

	public int[][] getCopyBoard() {
		int[][] newBoard = new int[this.board.length][this.board[0].length];
		for (int i = 0; i < this.board.length; i++)
			newBoard[i] = Arrays.copyOf(this.board[i], this.board[i].length);
		return newBoard;
	}
	
	public int[][] getBoard() {
		return board;
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

	public void setBoard(int[][] board) {
		this.board = board;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	public Boolean hasFreeCells() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if(board[i][j] == Keys.EMPTY) {
					return true;
				}
			}
		}

		return false;
	}
	
	public void setCell(int row, int column, int value) {
		if (cellExists(row, column))
			board[row][column] = value;
	}

	public Boolean cellExists(int row, int column) {
		if (row < board.length && row >= 0 && column < board[0].length && column >=0)
			return true;
		return false;
	}
	
	public int getCell(int r, int c) {
		if(cellExists(r, c))
			return board[r][c];
		return -1;
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

	
	public ArrayList<Point> getEmptyCellIds() {
		
		ArrayList<Point> freeCells = new ArrayList<Point>();
		
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[0].length; j++) {
				if (getCell(i, j) == Keys.EMPTY)
					freeCells.add(new Point(i, j));
			}
		}
		
		return freeCells;
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
		str = str.concat("\n Score: "+this.score+"\n");
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State))
			return false;

		State other = (State) obj;

		for (int i = 0; i < this.board.length; i++){
			for (int j = 0; j < this.board[0].length; j++) {
				if (this.board[i][j] != other.getCopyBoard()[i][j])
					return false;
			}
		
		}

		if (this.score != other.getScore())
			return false;

		return true;
	}

	public boolean isConnectedToServer() {
		return connectedToServer;
	}

	public void setConnectedToServer(boolean connectedToServer) {
		this.connectedToServer = connectedToServer;
	}
}
