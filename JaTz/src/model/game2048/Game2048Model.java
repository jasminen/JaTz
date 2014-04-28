package model.game2048;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import controller.Keys;
import model.Action;
import model.Model;
import model.State;

public class Game2048Model extends Observable implements Model {

	LinkedList<State> states;
	int winNum;
	private HashMap<Integer, Action> actionFactory;
	

	//C'tor - gets the win number of the game (a power of two).
	public Game2048Model(int winNum) {
		states = new LinkedList<State>();
		actionFactory = new HashMap<Integer, Action>();
		
		if (isPowerOfTwo(winNum))
			this.winNum = winNum;
		else {
			System.out.println("Not a power of two, setting to default 2048.");
			this.winNum=2048;
		}
		
		actionFactory.put(Keys.UP, new MoveUp2048Action());
		actionFactory.put(Keys.DOWN, new MoveDown2048Action());
		actionFactory.put(Keys.RIGHT, new MoveRight2048Action());
		actionFactory.put(Keys.LEFT, new MoveLeft2048Action());
	}

	
	
	
	@Override
	public void doAction(Integer action) {
		State newState = null;
		Action a;

		switch (action) {
		case Keys.RESTART:
			states.clear();
			newState = getStartState();
			break;
		case Keys.UNDO:
			if (states.size() > 1)
				states.pollLast();
			break;
		default:
			a=this.actionFactory.get(action); //Get relevant Action
			if(a!=null)
				newState=a.doAction(states.getLast()); //start doAction method
			else
				System.out.println("Not valid key"); ///////////////////////////////////////////////////////////////////////////////////////////
			break;
		}

		//Check if the mode of the game needs to be changed to game over or win. if not, draw a new number.
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
