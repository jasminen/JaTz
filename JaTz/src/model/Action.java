package model;

import common.State;

public interface Action {

	public State doAction(State state);
}
