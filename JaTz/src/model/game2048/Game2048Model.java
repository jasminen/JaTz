package model.game2048;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import controller.Keys;
import model.Model;
import model.State;

public class Game2048Model extends Observable implements Model {

	LinkedList<State> states;
	int winNum;

	public Game2048Model(int winNum) {
		states = new LinkedList<State>();
		if (isPowerOfTwo(winNum))
			this.winNum = winNum;
		else {
			System.out.println("Not a power of two, setting to default 2048.");
			this.winNum=2048;
		}
		
	}

	@Override
	public void doAction(int action) {
		State newState = null;

		switch (action) {
		case Keys.UP:
			newState = new MoveUp2048Action().doAction(states.getLast());
			break;
		case Keys.RIGHT:
			newState = new MoveRight2048Action().doAction(states.getLast());
			break;
		case Keys.DOWN:
			newState = new MoveDown2048Action().doAction(states.getLast());
			break;
		case Keys.LEFT:
			newState = new MoveLeft2048Action().doAction(states.getLast());
			break;
		case Keys.RESTART:
			newState = getStartState();
			break;
		case Keys.UNDO:
			if (states.size() > 1)
				states.pollLast();
			break;
		default:
			System.out.println("Not valid key"); // /////////////////////////////////////////////////////////
			break;
		}

		if (newState != null) {
			if (hasFree(newState.getBoard())) {
				DrawNewNumber(newState);
			} else {
				if (!gotAvailableMoves(newState))
					newState.setMode(Keys.GAMEOVER);
			}

			if (win(newState.getBoard()))
				newState.setMode(Keys.WIN);

			states.add(newState);
		}

		setChanged();
		notifyObservers();

	}

	@Override
	public State getState() {
		return states.getLast();
	}

	private Boolean hasFree(int[][] board) {
		for (int[] r : board)
			for (int c : r)
				if (c == 0)
					return true;
		return false;
	}

	private void DrawNewNumber(State state) {
		int row = new Random().nextInt(4);
		int column = new Random().nextInt(4);
		while (state.getBoard()[row][column] != 0) {
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

	private State getStartState() {
		int[][] board = new int[4][4];
		int row = new Random().nextInt(4);
		int column = new Random().nextInt(4);
		board[row][column] = 2;

		return new State(board, 0);

	}

	private Boolean gotAvailableMoves(State state) {
		State up = new MoveUp2048Action().doAction(state);
		State down = new MoveDown2048Action().doAction(state);
		State right = new MoveRight2048Action().doAction(state);
		State left = new MoveLeft2048Action().doAction(state);

		if (state.equals(up) && state.equals(down) && state.equals(right)
				&& state.equals(left))
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

}
