package model.game2048;

import java.io.Serializable;
import java.util.Random;
import controller.Keys;
import model.AbsModel;
import model.Model;
import model.State;

public class Game2048Model extends AbsModel implements Model, Serializable {

	private static final long serialVersionUID = 1L;
	int winNum;
	
	
	public Game2048Model() {}

	//C'tor - gets the win number of the game (a power of two).
	public Game2048Model(int winNum) {
		
		if (isPowerOfTwo(winNum))
			this.winNum = winNum;
		else {
			System.out.println("Not a power of two, setting to default 2048.");
			this.winNum=2048;
		}

	}

	
	
	
	@Override
	public void moveUp() {
		endPhase(new MoveUp2048Action().doAction(getState()));
		
	}

	@Override
	public void moveDown() {
		endPhase(new MoveDown2048Action().doAction(getState()));
		
	}

	@Override
	public void moveLeft() {
		endPhase(new MoveLeft2048Action().doAction(getState()));
		
	}

	@Override
	public void moveRight() {
		endPhase(new MoveRight2048Action().doAction(getState()));
		
	}

	
	@Override
	protected State getStartState() {
		int[][] board = new int[4][4];
		int row = new Random().nextInt(4);
		int column = new Random().nextInt(4);
		board[row][column] = 2;
		
		row = new Random().nextInt(4);
		column = new Random().nextInt(4);
		board[row][column] = 2;
		
		return new State(board, 0);
	}

	
	//Check if the mode needs to be change. if not, draw a new number. Add to states array and notify.
	private void endPhase(State newState) {
		if (newState != null) {
			if ((newState.hasFreeCells())) {
				DrawNewNumber(newState);
			} else {
				if (!gotAvailableMoves(newState)) {
					newState.setMode(Keys.GAMEOVER);
				}
			}
			if (win(newState.getCopyBoard()))
				newState.setMode(Keys.WIN);
			states.add(newState);
		}
		setChanged();
		notifyObservers();
	}
	
	
	
	private void DrawNewNumber(State state) {
		int row = new Random().nextInt(4);
		int column = new Random().nextInt(4);
		while (state.getCopyBoard()[row][column] != 0) {
			row = new Random().nextInt(4);
			column = new Random().nextInt(4);
		}
		if (new Random().nextInt(10) < 9)
			state.setCell(row, column, 2);
		else
			state.setCell(row, column, 4);
	}

	
	private Boolean win(int[][] board) {
		for (int[] r : board)
			for (int c : r)
				if (c == this.winNum)
					return true;
		return false;

	}

	
	private Boolean gotAvailableMoves(State state) {
		State up = new MoveUp2048Action().doAction(state);
		State down = new MoveDown2048Action().doAction(state);
		State right = new MoveRight2048Action().doAction(state);
		State left = new MoveLeft2048Action().doAction(state);

		if (state.equals(up) && state.equals(down) && state.equals(right) && state.equals(left))
			return false;

		return true;

	}

	private static boolean isPowerOfTwo(int number) {
		if (number <= 0) {
			return false;
		}
		if ((number & -number) == number) {
			return true;
		}
		return false;
	}


	public int getWinNum() {
		return winNum;
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	





	
	
	
	
}
