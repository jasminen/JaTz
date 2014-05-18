package view.gamesMaze2048;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import controller.Keys;
import view.Board;
import view.MouseDragCommand;
import view.View;
import model.State;

/**
 * 
 * @author Tzelon Machluf and Jasmine Nouriel .
 *
 */

public class GamesMaze2048View extends Observable implements View, Runnable {

	Display display;
	Shell shell;
	int userCommand = Keys.NEW_GAME;
	Board board;
	Label score;
	String fileName;
	String gameName;
	Label instructions;
	MouseDragCommand mouseCommand;
	
	/**
	 * 
	 * @param gameName The name of the game the player want to play
	 */
	public GamesMaze2048View(String gameName) {
		this.gameName = gameName;
		setMouseCommand();
	}

	private void initComponents() {
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));
		shell.setSize(950, 850);
		shell.setText("Maze and 2048 Games");

		initMenuBar();
		initButtons();

		instructions = new Label(shell, SWT.NONE);
		instructions.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false,	false, 1, 1));
		instructions.setVisible(false);
		instructions.setText("Use the arrow keys to move the tiles");
		instructions.setForeground(new Color(null, 255, 0, 0));
		shell.forceFocus();

		setShellKeyListener();

		// Prompt a messageBox when pressing on Exit.
		shell.addListener(SWT.Close, exit()); 

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
	public void displayState(final State state) {

		switch (state.getMode()) {
		case Keys.NEW_GAME:
			board.setBoard(state.getBoard());
			board.layout();
			score.setText("Score: " + state.getScore() + "      "+ state.getMsg());
			break;
		case Keys.IN_PROGRESS:
			board.updateBoard(state.getBoard());
			score.setText("Score: " + state.getScore() + "      "+ state.getMsg());
			break;
		case Keys.WIN:
			board.updateBoard(state.getBoard());
			score.setText("Score: " + state.getScore() + "      "+ state.getMsg());
			winWindow();
			break;
		case Keys.GAMEOVER:
			board.updateBoard(state.getBoard());
			score.setText("Score: " + state.getScore() + "      "+ state.getMsg());
			gameOverWindow();
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

		// Create the File item's drop down menu
		Menu fileMenu = new Menu(menuBar);
		Menu editMenu = new Menu(menuBar);

		// Create all the items in the bar menu
		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(fileMenu);
		MenuItem editItem = new MenuItem(menuBar, SWT.CASCADE);
		editItem.setText("Edit");
		editItem.setMenu(editMenu);

		// Create all the items in the File drop down menu
		MenuItem newGameItem = new MenuItem(fileMenu, SWT.CASCADE);
		newGameItem.setText("New Game");

		Menu newGameSubMenu = new Menu(shell, SWT.DROP_DOWN);
		newGameItem.setMenu(newGameSubMenu);

		MenuItem game2048Item = new MenuItem(newGameSubMenu, SWT.NONE);
		game2048Item.setText("2048");
		game2048Item.addListener(SWT.Selection, startNewGame("2048"));

		MenuItem gameMazeItem = new MenuItem(newGameSubMenu, SWT.NONE);
		gameMazeItem.setText("Maze");
		gameMazeItem.addListener(SWT.Selection, startNewGame("maze"));
		
		MenuItem connectToServerItem = new MenuItem(fileMenu, SWT.NONE);
		connectToServerItem.setText("Connect to Solver server");
		connectToServerItem.addListener(SWT.Selection, connectToServer());

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
		// End of file drop down

		// Create all the items in the Edit drop down menu
		MenuItem undoItem = new MenuItem(editMenu, SWT.NONE);
		undoItem.setText("Undo");
		// End of Edit drop down

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
		score.setFont(new Font(Display.getDefault(), "Arial", 16, SWT.BOLD));
		score.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		Button restartGame = new Button(shell, SWT.PUSH);
		restartGame.setText("Restart Game");
		restartGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false,
				1, 1));

		board = new Board(shell, SWT.BORDER, mouseCommand);
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

	
	//---------------
	// Listeners
	//---------------
	
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
				if (!(fileName == null)) {
					setChanged();
					notifyObservers(fileName + "_load");
				}
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
				if (!(fileName == null)) {
					setChanged();
					notifyObservers(fileName + "_save");
				}
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
	
	private Listener connectToServer() {
		return (new ConnectToServer(display){});
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
				MessageBox exitMB = new MessageBox(shell, SWT.ICON_QUESTION | SWT.NO | SWT.YES);
				exitMB.setText("Exit");
				exitMB.setMessage("Wanna leave?");
				int buttonID = exitMB.open();
				if(buttonID == SWT.YES) {
					e.doit = true;
					display.dispose();
				}
			}
		});
	}

	private Listener startNewGame(final String game) {
		return new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (!GamesMaze2048View.this.gameName.equals(game)) {
					shell.removeListener(1, shell.getListeners(1)[0]);
					userCommand = Keys.DIFFERENT_GAME;
					GamesMaze2048View.this.gameName = game;
					setShellKeyListener();
				} else {
					userCommand = Keys.NEW_GAME;
				}
				setChanged();
				notifyObservers();
			}
		};
	}
	
	// Setting the shell key listener according to the game.
	private void setShellKeyListener() {
		if (gameName.equals("maze")) {
			shell.addKeyListener(new AbsArrowDiagonalKeysListener() {
				@Override
				public void setUserCommand(int userCommand) {
					GamesMaze2048View.this.userCommand = userCommand;
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
					GamesMaze2048View.this.userCommand = userCommand;
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

	//---------END LISTENERS------------//
	
	private void winWindow() {
		Shell winShell = new Shell(display);
		winShell.setSize(280, 230);
		winShell.setBackgroundImage(new Image(Display.getDefault(),
				"images/youwin.jpg"));
		winShell.setText("Congratulations!!!!!");
		winShell.open();

	}

	private void gameOverWindow() {
		Shell gameOverShell = new Shell(display);
		gameOverShell.setSize(325, 268);
		gameOverShell.setBackgroundImage(new Image(Display.getDefault(),
				"images/gameover.png"));
		gameOverShell.setText("Game Over");
		gameOverShell.open();

	}

	private void setMouseCommand() {
		this.mouseCommand = new MouseDragCommand() {

			@Override
			public void setCommand(Point to, Point objectSize) {
				Boolean flag = true;

				if (to.x < 0 && Math.abs(to.x) < objectSize.x + 10 && to.y < 0
						&& Math.abs(to.y) < objectSize.y + 10
						&& gameName.equals("maze")) {
					userCommand = Keys.DIAGONAL_LEFT_UP;
				} else if (to.x < 0 && Math.abs(to.x) < objectSize.x + 10
						&& to.y > objectSize.y
						&& to.y < (2 * objectSize.y) + 10
						&& gameName.equals("maze")) {
					userCommand = Keys.DIAGONAL_LEFT_DOWN;
				} else if (to.x > objectSize.x
						&& to.x < (2 * objectSize.x) + 10 && to.y < 0
						&& Math.abs(to.y) < objectSize.y + 10
						&& gameName.equals("maze")) {
					userCommand = Keys.DIAGONAL_RIGHT_UP;
				} else if (to.x > objectSize.x
						&& to.x < (2 * objectSize.x) + 10
						&& to.y > objectSize.y
						&& to.y < (2 * objectSize.y) + 10
						&& gameName.equals("maze")) {
					userCommand = Keys.DIAGONAL_RIGHT_DOWN;
				} else if (to.x > 0 && to.x < objectSize.x && to.y < 0
						&& Math.abs(to.y) < objectSize.y + 10) {
					userCommand = Keys.UP;
				} else if (to.x > 0 && to.x < objectSize.x
						&& to.y > objectSize.y
						&& to.y < (2 * objectSize.y) + 10) {
					userCommand = Keys.DOWN;
				} else if (to.x < 0 && Math.abs(to.x) < objectSize.x + 10
						&& to.y > 0 && to.y < objectSize.y) {
					userCommand = Keys.LEFT;
				} else if (to.x > objectSize.x
						&& to.x < (2 * objectSize.x) + 10 && to.y > 0
						&& to.y < objectSize.y) {
					userCommand = Keys.RIGHT;
				} else {
					flag = false;
				}

				if (flag) {
					setChanged();
					notifyObservers();
				}

			}
		};
	}

}
