package view.gameMaze;

import java.util.Observable;

import model.State;
import view.View;

public class GameMazeView extends Observable implements View, Runnable {

	@Override
	public void displayState(State state) {

	}

	@Override
	public int getUserCommand() {
		return 0;
	}

	@Override
	public void run() {
		
	}

}
