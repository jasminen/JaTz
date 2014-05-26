package presenter;


import model.Model;
import view.View;

import java.net.InetSocketAddress;
import java.util.Observable;
import java.util.Observer;

import common.Keys;


/**
 * Presenter - this class manages the communication between View and Model\s
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public class Presenter implements Observer {

	private View gui;
	private Model model;
	private Model model2;
	
/**
 * C'tor
 * @param model
 * @param model2
 * @param gui
 */
	public Presenter(Model model, Model model2, View gui) {
		this.model2 = model2;
		this.model = model;
		this.gui = gui;
	}

	/**
	 * Gets a notification from one of the observable classes and updates the other one accordingly. 
	 * @param observable
	 * @param Object
	 */
	@Override
	public void update(Observable o, final Object io) {

		if (o == gui) {

			final int command = gui.getUserCommand();

			new Thread(new Runnable() {

				@Override
				public void run() {
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
						model.disconnectFromServer();
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
						if (io instanceof InetSocketAddress) {
							model.connectToServer((InetSocketAddress) io);
						}
						break;
					case Keys.DISCONNECT:
						model.disconnectFromServer();
						break;
					case Keys.GET_HINT:
						if (io instanceof Integer) {
							model.getHint((Integer) io);
						}
						break;
					case Keys.FULL_SOLVER:
						model.fullSolver();
						break;
					case Keys.SAVE:
						if (io instanceof String) {
							model.save((String) io);
						}
						break;
					case Keys.LOAD:
						if (io instanceof String) {
							model.load((String) io);
						}
						break;
					default:
						System.out.println("Presenter: Unknown command");
						break;
					}

				}
			}).start();

		}

		if (o == model) {
			if(io != null) {
				gui.setConnectedToServer((Boolean) io);
			}
			gui.displayState(model.getState());
		}

	}


}
