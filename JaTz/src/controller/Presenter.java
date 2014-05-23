package controller;

import model.AbsModel;
import model.Model;
import view.View;

import java.net.InetSocketAddress;
import java.util.Observable;
import java.util.Observer;


import common.Keys;

/*
 * Presenter -  models 1&2 receiving the Maze and 2048 models
 */

public class Presenter implements Observer {

	private View gui;
	private Model model; 
	private Model model2;

	public Presenter(Model model, Model model2, View gui) {
		this.model2 = model2;
		this.model = model;
		this.gui = gui;
	}

	@Override
	public void update(Observable o, Object io) {
		if (o == gui) {
			if (io != null && !(io instanceof InetSocketAddress)) {
				saveLoadActions(io);
			} else {
				int command = gui.getUserCommand();

				switch (command) {
				case Keys.UP:
					model.moveUp();
					break;
				case Keys.DOWN:
					model.moveDown();
					break;
				case Keys.LEFT:
					model.moveLeft();
					break;
				case Keys.RIGHT:
					model.moveRight();
					break;
				case Keys.DIAGONAL_LEFT_DOWN:
					model.moveDiagonalLeftDown();
					break;
				case Keys.DIAGONAL_LEFT_UP:
					model.moveDiagonalLeftUp();
					break;
				case Keys.DIAGONAL_RIGHT_DOWN:
					model.moveDiagonalRightDown();
					break;
				case Keys.DIAGONAL_RIGHT_UP:
					model.moveDiagonalRightUp();
					break;
				case Keys.DIFFERENT_GAME:
					Model m = model;
					model = model2;
					model2 = m;
				case Keys.NEW_GAME:
					model.newGame();
					break;
				case Keys.RESTART:
					model.restart();
					break;
				case Keys.UNDO:
					model.undo();
					break;
				case Keys.CONNECT:
					model.connectToServer((InetSocketAddress) io);
					break;
				case Keys.DISCONNECT:
					model.disconnectFromServer();
					break;
				case Keys.GET_HINT:
					model.getHint();
					break;
				default:
					System.out.println("Presenter: Unknown command");
					break;
				}

			}
		}
		if (o == model) {
			gui.displayState(model.getState());
		}

	}

	private void saveLoadActions(Object io) {
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
				this.model = (Model)SLhelper.load(operations[0]);
				gui.displayState(this.model.getState());
				if(this.model instanceof AbsModel)
					((AbsModel) this.model).addObserver(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}
}
