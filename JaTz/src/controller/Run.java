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
		
		
		String str = new String();
		
		GUI2048.run();
		
		
		for (int i = 0; i < 4; i++) {
			str = str.concat("\n");
			for (int j = 0; j < 4; j++) {
				str = str.concat(model2048.getState().getBoard()[i][j] + "  ");
			}
		}
		str = str.concat("\n");
		System.out.println(str);

		str = new String();
		model2048.doAction(1);

		for (int i = 0; i < 4; i++) {
			str = str.concat("\n");
			for (int j = 0; j < 4; j++) {
				str = str.concat(model2048.getState().getBoard()[i][j] + "  ");
			}
		}
		str = str.concat("\n");
		System.out.println(str);

		str = new String();
		model2048.doAction(3);
		model2048.doAction(2);
		for (int i = 0; i < 4; i++) {
			str = str.concat("\n");
			for (int j = 0; j < 4; j++) {
				str = str.concat(model2048.getState().getBoard()[i][j] + "  ");
			}
		}
		str = str.concat("\n");
		System.out.println(str);

	}

}
