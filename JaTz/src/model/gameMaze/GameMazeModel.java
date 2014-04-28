package model.gameMaze;

import java.util.HashMap;
import java.util.LinkedList;

import controller.Keys;
import model.Action;
import model.Model;
import model.State;

public class GameMazeModel implements Model {

	LinkedList<State> states;
	private HashMap<Integer, Action> actionFactory;
	
	public GameMazeModel() {
		states = new LinkedList<State>();
		actionFactory = new HashMap<Integer, Action>();
		
		actionFactory.put(Keys.UP, new MazeAction(-1, 0));
		actionFactory.put(Keys.DOWN, new MazeAction(1, 0));
		actionFactory.put(Keys.LEFT, new MazeAction(0, -1));
		actionFactory.put(Keys.RIGHT, new MazeAction(0, 1));
		
	}
	
	@Override
	public void doAction(Integer action) {
		State newState = actionFactory.get(action).doAction(states.getLast());
		
		if(!newState.equals(states.getLast()))
			states.add(newState);

	}

	@Override
	public State getState() {
	
		return null;
	}

}
