package view.game2048;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import controller.Keys;
import view.AbsArrowDiagonalKeysListener;
import view.AbsArrowKeysListener;
import view.Board;
import view.View;
import model.State;

public class Game2048View extends Observable implements View, Runnable {

	Display display;
	Shell shell;
	int userCommand = Keys.NEW_GAME;
	Board board;
	Label score;
	String fileName;
	String gameName = "2048";
	Label instructions;

	private void initComponents() {
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));
		shell.setSize(400, 300);
		shell.setText("2048 Game");

		initMenuBar();
		initButtons();

		instructions = new Label(shell, SWT.NONE);
		instructions.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false,
				false, 1, 1));
		instructions.setVisible(false);
		instructions.setText("Use the arrow keys to move the tiles");
		instructions.setForeground(new Color(null, 255, 0, 0));
		shell.forceFocus();
		
		setShellKeyListener();
		
		shell.open(); // End initComponent
	}

	
	
	
	@Override
	public void run() {
		initComponents();
		setChanged();
		notifyObservers();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	@Override
	public void displayState(State state) {
		switch (state.getMode()) {
		case Keys.NEW_GAME:
			board.setBoard(state.getBoard());
			board.layout();
			break;
		case Keys.IN_PROGRESS:
			board.updateBoard(state.getBoard());
			score.setText("Score: " + state.getScore());
			break;
		case Keys.WIN:
			score.setText("Score: " + state.getScore() + "  YOU WIN!!!"); // needs
																			// improvement
			break;
		case Keys.GAMEOVER:
			score.setText("Score: " + state.getScore() + "  GAME OVER!!!"); // needs
																			// improvement
			break;
		default:
			break;
		}
	}

	@Override
	public int getUserCommand() {
		return userCommand;
	}

	private void initMenuBar() {
		Menu menuBar = new Menu(shell, SWT.BAR);

		// Create the File item's dropdown menu
		Menu fileMenu = new Menu(menuBar);
		Menu editMenu = new Menu(menuBar);

		// Create all the items in the bar menu
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(fileMenu);
		MenuItem editItem = new MenuItem(menuBar, SWT.CASCADE);
		editItem.setText("Edit");
		editItem.setMenu(editMenu);

		// Create all the items in the File dropdown menu
		MenuItem newGameItem = new MenuItem(fileMenu, SWT.CASCADE);
		newGameItem.setText("New Game");

		Menu newGameSubMenu = new Menu(shell, SWT.DROP_DOWN);
		newGameItem.setMenu(newGameSubMenu);

		MenuItem game2048Item = new MenuItem(newGameSubMenu, SWT.NONE);
		game2048Item.setText("2048");
		game2048Item.addListener(SWT.Selection, setGameName("2048"));

		MenuItem gameMazeItem = new MenuItem(newGameSubMenu, SWT.NONE);
		gameMazeItem.setText("Maze");
		gameMazeItem.addListener(SWT.Selection, setGameName("maze"));

		MenuItem restartItem = new MenuItem(fileMenu, SWT.NONE);
		restartItem.setText("Restart");
		restartItem.addListener(SWT.Selection, restartGame());

		// Create the first separator
		new MenuItem(fileMenu, SWT.SEPARATOR);
		MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
		saveItem.setText("Save");
		MenuItem loadItem = new MenuItem(fileMenu, SWT.NONE);
		loadItem.setText("Load");

		// Create the second separator
		new MenuItem(fileMenu, SWT.SEPARATOR);
		MenuItem ExitItem = new MenuItem(fileMenu, SWT.NONE);
		ExitItem.setText("Exit");
		// End of file dropdown

		// Create all the items in the Edit dropdown menu
		MenuItem undoItem = new MenuItem(editMenu, SWT.NONE);
		undoItem.setText("Undo");
		// End of Edit dropdown

		shell.setMenuBar(menuBar);

		ExitItem.addListener(SWT.Selection, exit());
		undoItem.addListener(SWT.Selection, undoMove());
		loadItem.addListener(SWT.Selection, loadGame());
		saveItem.addListener(SWT.Selection, saveGame());

	}


	private void initButtons() {
		Button undoMove = new Button(shell, SWT.PUSH);
		undoMove.setText("Undo Move");
		undoMove.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1,
				1));

		score = new Label(shell, SWT.NONE);
		score.setText("Score: 0        ");
		score.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		Button restartGame = new Button(shell, SWT.PUSH);
		restartGame.setText("Restart Game");
		restartGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false,
				1, 1));

		board = new Board(shell, SWT.BORDER);
		board.setGameColors(new Color(null, 199, 193, 173), new Color(null,
				230, 227, 220));
		board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		Button loadGame = new Button(shell, SWT.PUSH);
		loadGame.setText("Load Game");
		loadGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1,
				1));

		Button saveGame = new Button(shell, SWT.PUSH);
		saveGame.setText("Save Game");
		saveGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1,
				2));

		loadGame.addListener(SWT.Selection, loadGame());
		saveGame.addListener(SWT.Selection, saveGame());
		restartGame.addListener(SWT.Selection, restartGame());
		undoMove.addListener(SWT.Selection, undoMove());
	}

	private Listener loadGame() {
		return (new Listener() {
			@Override
			public void handleEvent(Event e) {
				userCommand = Keys.LOAD;
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Load");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.xml", "*.txt", "*.*" };
				fd.setFilterExtensions(filterExt);
				String fileName = fd.open();
				System.out.println("load");
				setChanged();
				notifyObservers(fileName + "_load");
				shell.forceFocus();
			}
		});
	}

	private Listener saveGame() {
		return (new Listener() {
			@Override
			public void handleEvent(Event e) {
				userCommand = Keys.SAVE;
				FileDialog fd = new FileDialog(shell, SWT.SAVE);
				fd.setText("Save");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.xml", "*.txt", "*.*" };
				fd.setFilterExtensions(filterExt);
				String fileName = fd.open();
				System.out.println("save");
				setChanged();
				notifyObservers(fileName + "_save");
				shell.forceFocus();
			}
		});
	}

	private Listener restartGame() {
		return (new Listener() {
			@Override
			public void handleEvent(Event e) {
				userCommand = Keys.RESTART;
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});
	}

	private Listener undoMove() {
		return (new Listener() {
			@Override
			public void handleEvent(Event e) {
				userCommand = Keys.UNDO;
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});
	}

	private Listener exit() {
		return (new Listener() {
			@Override
			public void handleEvent(Event e) {
				display.dispose();
			}
		});
	}
	
	private Listener setGameName(final String game) {

		return new Listener() {

			@Override
			public void handleEvent(Event e) {
				shell.removeListener(1, shell.getListeners(1)[0]);
				Game2048View.this.gameName = game;
				setShellKeyListener();
				userCommand = Keys.NEW_GAME;
				setChanged();
				notifyObservers();
			}
		};

	}
	
	
	
	private void setShellKeyListener() {
		if (gameName.equals("maze")) {
			shell.addKeyListener(new AbsArrowDiagonalKeysListener() {

				@Override
				public void setUserCommand(int userCommand) {
					Game2048View.this.userCommand = userCommand;
					display.asyncExec(new Runnable() {

						@Override
						public void run() {
							setChanged();
							notifyObservers();
						}
					});
				}

				@Override
				public void setInstructions(Boolean flag) {
					instructions.setVisible(flag);

				}
			});

		}

		else {
			shell.addKeyListener(new AbsArrowKeysListener() {

				@Override
				public void setUserCommand(int userCommand) {
					Game2048View.this.userCommand = userCommand;
					setChanged();
					notifyObservers();

				}

				@Override
				public void setInstructions(Boolean flag) {
					instructions.setVisible(flag);
				}
			});
		}

		
	}

}
