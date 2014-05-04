package controller;
import view.gamesMaze2048.GamesMaze2048View;
import model.game2048.Game2048Model;
import model.gameMaze.GameMazeModel;



public class Run {

	public static void main(String[] args) {
	
		
		GameMazeModel modelMaze = new GameMazeModel();
		GamesMaze2048View GUI2048 = new GamesMaze2048View("maze");
		Game2048Model model2048 = new Game2048Model(2048);
		Presenter presenter = new Presenter(modelMaze,model2048, GUI2048);
		GUI2048.addObserver(presenter);
		modelMaze.addObserver(presenter);
		model2048.addObserver(presenter);
		
		Thread th = new Thread(GUI2048);
		
		th.start();
		
		
	}

}
