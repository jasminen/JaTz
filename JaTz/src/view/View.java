package view;

import model.State;

public interface View {

	public void displayState(State state);
	public int getUserCommand();
	
}
