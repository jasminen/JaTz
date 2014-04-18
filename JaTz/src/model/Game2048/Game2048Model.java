package model.Game2048;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import model.Model;
import model.State;

public class Game2048Model extends Observable implements Model {

	LinkedList<State> states;
	
	public Game2048Model() {
		int[][] board = new int[4][4];
		int row = new Random().nextInt(4);
		int column = new Random().nextInt(4);
		board[row][column]=2;
		row = new Random().nextInt(4);
		column = new Random().nextInt(4);
		board[row][column]=2;
		
		states= new LinkedList<State>();
		states.add(new State(board, 0));
	}
	
	
	
	
	@Override
	public void doAction(int action) {
		State newState=null;
		
		switch (action) {
		case 1:
			newState=new MoveUp2048Action().doAction(states.getLast());
			break;
		case 2:
			newState=new MoveRight2048Action().doAction(states.getLast());
			break;
		case 3:
			newState=new MoveDown2048Action().doAction(states.getLast());
			break;
		case 4:
			newState=new MoveLeft2048Action().doAction(states.getLast());
			break;
		default:
			System.out.println("Not valid and Tzelon is kaka");  ///////////////////////////////////////////////////////////
			break;
		}
		
		int[][] board = newState.getBoard();
		if (hasFree(board)) {
			DrawNewNumber(board);
		}
		
		states.add(newState);
		notifyObservers();
		
		
	}

	@Override
	public State getState() {
		return states.getLast();
	}

	
	private Boolean hasFree(int[][] board) {
		for(int[] r : board)
			for(int c : r)
				if(c==0)
					return true;
		return false;
	}
	
	
	private void DrawNewNumber(int[][] board) {
		int row=new Random().nextInt(4);
		int column=new Random().nextInt(4);
		while(board[row][column]!= 0) {
			row=new Random().nextInt(4);
			column=new Random().nextInt(4);
		}
		if(new Random().nextInt(10) < 9 )
			board[row][column]=2;
		else
			board[row][column]=4;
	}
	
}