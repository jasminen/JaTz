package view;

import common.State;

public interface View {

	public void displayState(State state);
	public int getUserCommand();
	
}
