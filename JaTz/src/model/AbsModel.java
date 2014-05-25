package model;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Observable;
import common.SLhelper;
import common.State;

/**
 * Abstract model - define the models structure and handle the state list. Implements Model and Serializable.
 * @author Tzelon Machluf and Jasmine Nouriel
 * 
 */

public abstract class AbsModel extends Observable implements Model , Serializable{

	
	private static final long serialVersionUID = 1L;
	protected LinkedList<State> states;
	
	/**
	 * Default C'tor - creates a new state linked list
	 */
	
	public AbsModel() {
		this.states = new LinkedList<State>();
	}

	
	@Override
	public abstract void moveUp() ;

	@Override
	public abstract void moveDown(); 

	@Override
	public abstract void moveLeft();

	@Override
	public abstract void moveRight();

	@Override
	public void moveDiagonalLeftUp() {}

	@Override
	public void moveDiagonalRightUp() {}

	@Override
	public void moveDiagonalLeftDown() {}

	@Override
	public void moveDiagonalRightDown() {}
	
	@Override
	public void getHint(Integer iterations) {}
	
	@Override
	public void disconnectFromServer() {}
	
	@Override
	public void connectToServer(InetSocketAddress socketAddress) {}
	
	@Override
	public void fullSolver() {}

	@Override
	public abstract void restart();
	
/**
 * Undo last move - pulls the last state out of the state linked list
 */
	@Override
	public void undo() {
		if (this.states.size() > 1)
			this.states.pollLast();
		setChanged();
		notifyObservers();
	}

/**
 * Clears the state linked list and gets a new start state
 */
	@Override
	public void newGame() {
		this.states.clear();
		State newState = getStartState();
		this.states.add(newState);
		setChanged();
		notifyObservers();
	}

/**
 * Returns the last state in list
 * @return 	
 */	
	@Override
	public State getState() {
		return this.states.getLast();
	}
	
	
	/**
	 * Saves the model at the path specified
	 * @param path 
	 */
	@Override
	public void save(String path) {
		try {
			SLhelper.save(this, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load a model from the path specified
	 * @param path 
	 */
	@Override
	public abstract void load(String path);
	
	
	/**
	 * Get a start state of the game
	 * @return startState
	 */
	protected abstract State getStartState();


/**
 * State linked list setter
 * @param states
 */
	public void setStates(LinkedList<State> states) {
		this.states = states;
	}
	
	
	/**
	 * State linked list getter
	 * @return
	 */
	public LinkedList<State> getStates() {
		return this.states;
	}
	
}
