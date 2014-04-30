package controller;

import model.Model;
import model.game2048.Game2048Model;
import view.View;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;

public class Presenter implements Observer {

	private View gui;
	private Model model;
	private HashMap<Integer, Integer> commandToAction;

	public Presenter(Model model, View gui) {
		this.model = model;
		this.gui = gui;
		commandToAction = new HashMap<Integer, Integer>();
		commandToAction.put(SWT.ARROW_UP, Keys.UP);
		commandToAction.put(SWT.ARROW_DOWN, Keys.DOWN);
		commandToAction.put(SWT.ARROW_RIGHT, Keys.RIGHT);
		commandToAction.put(SWT.ARROW_LEFT, Keys.LEFT);
		commandToAction.put(Keys.UP, Keys.UP);
		commandToAction.put(Keys.DOWN, Keys.DOWN);
		commandToAction.put(Keys.RIGHT, Keys.RIGHT);
		commandToAction.put(Keys.LEFT, Keys.LEFT);
		commandToAction.put(Keys.UNDO, Keys.UNDO);
		commandToAction.put(Keys.NEW_GAME, Keys.NEW_GAME);
		commandToAction.put(Keys.RESTART, Keys.RESTART);
		commandToAction.put(SWT.ARROW_UP + SWT.ARROW_RIGHT, Keys.DIAGONAL_RIGHT_UP);
		commandToAction.put(SWT.ARROW_UP + SWT.ARROW_LEFT, Keys.DIAGONAL_LEFT_UP);
		commandToAction.put(SWT.ARROW_DOWN + SWT.ARROW_LEFT, Keys.DIAGONAL_LEFT_DOWN);
		commandToAction.put(SWT.ARROW_DOWN + SWT.ARROW_RIGHT, Keys.DIAGONAL_RIGHT_DOWN);
	}

	@Override
	public void update(Observable o, Object io) {
		if (o == gui) {
			if (io != null) {
				String[] operations = ((String) io).split("_");
				switch (operations[1]) {
				case "save":
					try {
						SLhelper.save(this.model, operations[0]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "load":
					try {
						this.model = SLhelper.load(operations[0]);
						gui.displayState(this.model.getState());
						if (this.model.getClass().getSimpleName().equals("Game2048Model")) {
							((Game2048Model) this.model).addObserver(this);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
			} else {
				model.doAction(commandToAction.get(gui.getUserCommand()));
			}
		}

		if (o == model) {
			gui.displayState(model.getState());
		}

	}

}
