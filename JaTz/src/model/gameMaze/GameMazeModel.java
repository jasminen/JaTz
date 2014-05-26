package model.gameMaze;

import java.io.Serializable;
import java.util.Random;

import org.eclipse.swt.graphics.Point;

import common.Keys;
import common.SLhelper;
import common.State;
import model.AbsModel;
import model.gameMaze.solver.Maze;
import model.gameMaze.solver.MazeAirDistance;
import model.gameMaze.solver.MazeDistanceG;
import model.gameMaze.solver.MazeDomain;
import model.gameMaze.solver.Node;
import model.gameMaze.solver.astar.Astar;

/**
 * Model of the Maze game. Extends AbsModel and implements  Serializable.
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public class GameMazeModel extends AbsModel implements  Serializable {

	private static final long serialVersionUID = 1L;
	int rows, columns, winScore=0;

	/**
	 * Default C'tor - updates rows to 6 and columns to 7
	 */
	public GameMazeModel() {
		this.rows = 6;
		this.columns = 7;
		
	}

	@Override
	public void moveUp() {
		doAction(-1, 0);

	}

	@Override
	public void moveDown() {
		doAction(1, 0);

	}

	@Override
	public void moveLeft() {
		doAction(0, -1);

	}

	@Override
	public void moveRight() {
		doAction(0, 1);

	}

	@Override
	public void moveDiagonalLeftUp() {
		doAction(-1, -1);
	}

	@Override
	public void moveDiagonalRightUp() {
		doAction(-1, 1);
	}

	@Override
	public void moveDiagonalLeftDown() {
		doAction(1, -1);
	}

	@Override
	public void moveDiagonalRightDown() {
		doAction(1, 1);
	}

	@Override
	public void restart() {
		while (this.states.size() > 1) {
			this.states.pollLast();	
		}
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void newGame() {
		winScore = 0;
		super.newGame();
		
	}
	
	
	/**
	 * Calls MazeAction according to number of columns and rows. Update mode if needed.
	 * @param r - rows
	 * @param c - columns
	 */
	public void doAction(int r, int c) {	
		if (getState().getMode() != Keys.WIN && getState().getMode() != Keys.GAMEOVER) {
			State newState = new MazeAction(r, c).doAction(getState());
			if (!newState.equals(getState())) {
				if (newState.findCell(Keys.MOUSE_AND_CHEESE) != null) {
					if (newState.getScore() == this.winScore) {
						newState.setMode(Keys.WIN);
						newState.setMsg("Congratulation!!");
					} else {
						newState.setMode(Keys.GAMEOVER);
						newState.setMsg("Your score is not the best score ("+winScore+"). Try again!");
					}
				}
				
				states.add(newState);
			}
			setChanged();
			notifyObservers();
		}
	}

	
	@Override
	protected State getStartState() {
		int[][] board = null;	
		
		while (winScore == 0) {
			board = new int[this.rows][this.columns];
			board[0][0] = Keys.MOUSE;
			board[this.rows - 1][this.columns - 1] = Keys.CHEESE;
			
			for (int i = 0; i <= (rows * columns * 0.5); i++) {
				int r = (int) (new Random().nextInt(rows));
				int c = (int) (new Random().nextInt(columns));
				if(board[r][c] != Keys.MOUSE && board[r][c] != Keys.CHEESE )
					board[r][c] = Keys.WALL;
			}

			Astar as = new Astar(new MazeDomain(new Maze(board)),new MazeDistanceG(), new MazeAirDistance());
			winScore = (int) as.search(new Node(new Point(0, 0)), new Node(new Point(this.rows - 1, this.columns - 1)));
		}
	
		return new State(board, 0, Keys.NEW_GAME, "Goal Score is: "+winScore+"         Straight moves cost 10, Diagonal cost 15.");
	}
	
	
/**
 * rows getter
 * @return rows
 */
	public int getRows() {
		return rows;
	}

	/**
	 * rows setter
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * columns getter
	 * @return columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * columns setter
	 * @param columns
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * winScore getter
	 * @return winScore
	 */
	public int getWinScore() {
		return winScore;
	}

	/**
	 * winScore setter
	 * @param winScore
	 */
	public void setWinScore(int winScore) {
		this.winScore = winScore;
	}

	@Override
	public void load(String path) {
		try {
			GameMazeModel model = (GameMazeModel) SLhelper.load(path);
			this.winScore = model.winScore;
			this.states = model.states;
			this.rows = model.rows;
			this.columns = model.columns;
			setChanged();
			notifyObservers();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
