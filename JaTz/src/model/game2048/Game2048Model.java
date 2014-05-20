package model.game2048;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.graphics.Point;

import controller.Keys;
import model.AbsModel;
import model.State;

/*
 * Game2048Model 
 */

public class Game2048Model extends AbsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	int boardSize;
	int winNum;
	Boolean alreadyWon = false;

	public Game2048Model() {
		this.boardSize = 4;
		this.winNum = 2048;
	}

	// C'tor - gets the win number of the game (a power of two).
	public Game2048Model(int winNum) {

		if (isPowerOfTwo(winNum))
			this.winNum = winNum;
		else {
			System.out.println("Not a power of two, setting to default 2048.");
			this.winNum = 2048;
		}
		this.boardSize = 4;
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
	public void restart() {
		this.states.clear();
		this.states.add(getStartState());
		setChanged();
		notifyObservers();
	}

	@Override
	protected State getStartState() {
		int[][] board = new int[boardSize][boardSize];
		State state = new State(board, 0);
		DrawNewNumber(state);
		DrawNewNumber(state);

		return state;
	}
	

	// Check if the mode needs to be change. if not, draw a new number. Add to
	// states array and notify.
	private void endPhase(State newState) {
		if (newState != null  && !newState.equals(getState())) {
			if (newState.getMode() == Keys.WIN) // Let the game continue after winning
				newState.setMode(Keys.IN_PROGRESS);

			if (newState.hasFreeCells()) {
				DrawNewNumber(newState);
			}
			
			if (!gotAvailableMoves(newState)) {
					newState.setMode(Keys.GAMEOVER);
			}

			if (alreadyWon == false && win(newState.getCopyBoard())) { // Check winning only if didn't win before.
				newState.setMode(Keys.WIN);
			}
			
			
			this.states.add(newState);
			setChanged();
			notifyObservers();
		}
		
	}

	private void DrawNewNumber(State state) {
		ArrayList<Point> freeCells = state.getEmptyCellIds();

		// Choose a random cell out of the free cells array and update the
		// relevant array in board
		int cell = new Random().nextInt(freeCells.size());
		int row = freeCells.get(cell).x;
		int column = freeCells.get(cell).y;
		if (new Random().nextInt(10) < 9)
			state.setCell(row, column, 2);
		else
			state.setCell(row, column, 4);
	}

	
	private Boolean win(int[][] board) {
		for (int[] r : board)
			for (int c : r)
				if (c == this.winNum) {
					this.alreadyWon = true;
					return true;
				}
		return false;

	}

	private Boolean gotAvailableMoves(State state) {
		State up = new MoveUp2048Action().doAction(state);
		State down = new MoveDown2048Action().doAction(state);
		State right = new MoveRight2048Action().doAction(state);
		State left = new MoveLeft2048Action().doAction(state);

		if (state.equals(up) && state.equals(down) && state.equals(right)
				&& state.equals(left)) {
			return false;
		}

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
		return this.winNum;
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public Boolean getAlreadyWon() {
		return alreadyWon;
	}

	public void setAlreadyWon(Boolean alreadyWon) {
		this.alreadyWon = alreadyWon;
	}

}
