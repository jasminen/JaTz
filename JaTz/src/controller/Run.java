package controller;
import view.Game2048View;
import model.Game2048.Game2048Model;



public class Run {

	public static void main(String[] args) {
		Game2048Model model2048 = new Game2048Model();
		Game2048View GUI2048 = new Game2048View();
		Presenter presenter2048 = new Presenter(model2048, GUI2048);
		GUI2048.addObserver(presenter2048);
		model2048.addObserver(presenter2048);
		Thread th = new Thread(GUI2048);
		
		th.start();
		
	
		//System.out.println(Thread.getAllStackTraces().keySet().size());
		
		
	}

}
