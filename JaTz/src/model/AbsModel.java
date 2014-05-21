package model;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Observable;

import common.Keys;
import common.State;

/*
 * Abstract model - define the models structure and handle the state list.
 */


public abstract class AbsModel extends Observable implements Model , Serializable {

	
	private static final long serialVersionUID = 1L;
	protected LinkedList<State> states;
	
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
	public void getHint() {}
	
	@Override
	public void disconnectFromServer() {}
	
	@Override
	public void connectToServer(InetSocketAddress socketAddress){}

	@Override
	public abstract void restart();
	

	@Override
	public void undo() {
		if (this.states.size() > 1)
			this.states.pollLast();
		setChanged();
		notifyObservers();
	}

	@Override
	public void newGame() {
		this.states.clear();
		State newState = getStartState();
		newState.setMode(Keys.NEW_GAME);
		this.states.add(newState);
		setChanged();
		notifyObservers();
	}

	
	@Override
	public State getState() {
		return this.states.getLast();
	}
	
	protected abstract State getStartState();



	public void setStates(LinkedList<State> states) {
		this.states = states;
	}
	
	public LinkedList<State> getStates() {
		return this.states;
	}
	
}
