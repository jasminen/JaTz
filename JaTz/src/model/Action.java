package model;

import common.State;


/**
 * Action interface 
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */
public interface Action {

	
	/**
	 * Does an action on a state and returns the new state after the action
	 * @param state
	 * @return 
	 */
	public State doAction(State state);
}
