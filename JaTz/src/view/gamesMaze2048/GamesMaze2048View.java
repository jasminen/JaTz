package view.gamesMaze2048;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import common.Keys;
import common.State;
import view.Board;
import view.MouseDragCommand;
import view.View;

/**
 * 
 * @author Tzelon Machluf and Jasmine Nouriel .
 *
 */

public class GamesMaze2048View extends Observable implements View, Runnable {

	private Display display;
	private Shell shell;
	private int userCommand = Keys.NEW_GAME;
	private Board board;
	private Label score;
	private String gameName;
	private Label instructions;
	private Label serverMsg;
	private Button getSolver;
	private Composite radioSelection;
	private Button steps;
	private Button fullSolver;
	private Text numberOfSteps;
	private MouseDragCommand mouseCommand;
	
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
		shell.addListener(SWT.Close, exitListener()); 

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
		display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
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
		});

	}
	
	
	@Override
	public void setConnectedToServer(final Boolean isConnectedToServer) {
		display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("View: set connected to server: "+isConnectedToServer);
				if (isConnectedToServer) {
					radioSelection.setVisible(true);
					getSolver.setEnabled(true);
					numberOfSteps.setVisible(true);
					serverMsg.setText("Connected to a server");
					steps.setSelection(true);
					fullSolver.setSelection(false);
				} else {
					radioSelection.setVisible(false);
					getSolver.setEnabled(false);
					numberOfSteps.setVisible(false);
					serverMsg.setText("Not connect to a solver server");
				}
			}
		});
		
		
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
		game2048Item.addListener(SWT.Selection, startNewGameListener("2048"));

		MenuItem gameMazeItem = new MenuItem(newGameSubMenu, SWT.NONE);
		gameMazeItem.setText("Maze");
		gameMazeItem.addListener(SWT.Selection, startNewGameListener("maze"));
		
		MenuItem connectToServerItem = new MenuItem(fileMenu, SWT.NONE);
		connectToServerItem.setText("Connect to Solver server");
		connectToServerItem.addListener(SWT.Selection, connectToServerListener());

		MenuItem restartItem = new MenuItem(fileMenu, SWT.NONE);
		restartItem.setText("Restart");
		restartItem.addListener(SWT.Selection, restartGameListener());

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

		ExitItem.addListener(SWT.Selection, exitListener());
		undoItem.addListener(SWT.Selection, undoMoveListener());
		loadItem.addListener(SWT.Selection, loadGameListener());
		saveItem.addListener(SWT.Selection, saveGameListener());

	}

	private void initButtons() {
		Button undoMove = new Button(shell, SWT.PUSH);
		undoMove.setText("Undo Move");
		undoMove.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1,	1));

		score = new Label(shell, SWT.NONE);
		score.setText("Score: 0        ");
		score.setFont(new Font(Display.getDefault(), "Arial", 16, SWT.BOLD));
		score.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		Button restartGame = new Button(shell, SWT.PUSH);
		restartGame.setText("Restart Game");
		restartGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		board = new Board(shell, SWT.BORDER, mouseCommand);
		board.setGameColors(new Color(null, 199, 193, 173), new Color(null,230, 227, 220));
		board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 7));

		Button loadGame = new Button(shell, SWT.PUSH);
		loadGame.setText("Load Game");
		loadGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		Button saveGame = new Button(shell, SWT.PUSH);
		saveGame.setText("Save Game");
		saveGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		
		getSolver = new Button(shell, SWT.PUSH);
		getSolver.setText("Please Solve");
		getSolver.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		getSolver.setEnabled(false);
		
		radioSelection = new Composite(shell, SWT.NULL);
		radioSelection.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		radioSelection.setLayout(new RowLayout());
		
	    fullSolver = new Button(radioSelection, SWT.RADIO);
	    fullSolver.setText("Full Solver");
	    steps = new Button(radioSelection, SWT.RADIO);
	    steps.setText("Steps");
	    radioSelection.setVisible(false);
	    
	    numberOfSteps = new Text(shell, SWT.BORDER);
	    numberOfSteps.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
	    numberOfSteps.addListener(SWT.Verify, onlyNumbersListener());
	    numberOfSteps.setVisible(false);
	    
	    
	    serverMsg = new Label(shell, SWT.NONE);
	    serverMsg.setText("You are not connect to the server");
	    serverMsg.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 2));
	    
	    steps.addListener(SWT.Selection, RadioSelected());
		getSolver.addListener(SWT.Selection, getSolverListener());
		loadGame.addListener(SWT.Selection, loadGameListener());
		saveGame.addListener(SWT.Selection, saveGameListener());
		restartGame.addListener(SWT.Selection, restartGameListener());
		undoMove.addListener(SWT.Selection, undoMoveListener());
	}

	
	//---------------
	// Listeners
	//---------------
	
	
	private Listener RadioSelected() {
		return (new Listener() {

			@Override
			public void handleEvent(Event e) {
				if (((Button) radioSelection.getChildren()[0]).getSelection()) { //Full Solver
					numberOfSteps.setVisible(false);
				} else { // Steps
					numberOfSteps.setVisible(true);
				}
				shell.forceFocus();
			}

		});
	}
	
	private Listener onlyNumbersListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				String string = e.text;
		        char[] chars = new char[string.length()];
		        string.getChars(0, chars.length, chars, 0);
		        System.out.println(e.text);
		        for (int i = 0; i < chars.length; i++) {
		          if (!('0' <= chars[i] && chars[i] <= '9')) {
		            e.doit = false;
		            return;
		          }
		        }
			}
		});
	}

	
	private Listener getSolverListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				if (((Button) radioSelection.getChildren()[0]).getSelection()) { //Full Solver
					userCommand = Keys.FULL_SOLVER;
					setChanged();
					notifyObservers();
				} else { // Steps
					userCommand = Keys.GET_HINT;
					setChanged();
					notifyObservers(Integer.parseInt(numberOfSteps.getText()));
				}
				shell.forceFocus();
			}
		});
	}
	
	private Listener loadGameListener() {
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
					notifyObservers(fileName);
				}
				shell.forceFocus();
			}
		});
	}

	private Listener saveGameListener() {
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
					notifyObservers(fileName);
				}
				shell.forceFocus();
			}
		});
	}

	private Listener restartGameListener() {
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
	
	private Listener connectToServerListener() {
		return (new ConnectToServer(display){

			@Override
			public void setUserCommand(int userCommand) {
				GamesMaze2048View.this.userCommand = userCommand;
				setChanged();
				notifyObservers(this.socketAddress);
			}
		});
	}

	private Listener undoMoveListener() {
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

	private Listener exitListener() {
		return (new Listener() {
			@Override
			public void handleEvent(Event e) {
				MessageBox exitMB = new MessageBox(shell, SWT.ICON_QUESTION | SWT.NO | SWT.YES);
				exitMB.setText("Exit");
				exitMB.setMessage("Wanna leave?");
				int buttonID = exitMB.open();
				e.doit = false;
				if(buttonID == SWT.YES) {
					e.doit = true;
					display.dispose();
				}
			}
		});
	}

	private Listener startNewGameListener(final String game) {
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
