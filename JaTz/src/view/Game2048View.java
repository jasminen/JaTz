package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import model.State;

public class Game2048View extends Observable implements View, Runnable {

	Display display;
	Shell shell;
	int userCommand = 0;
	State state;
	Board board;
	Label score;
	int[][] boardData = new int[][] { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },{ 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

	private void initComponents() {
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));
		shell.setSize(400, 300);
		shell.setText("2048 Game");

		initMenuBar();
		initButtons();

		final Label instructions = new Label(shell, SWT.NONE);
		instructions.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		instructions.setVisible(false);
		instructions.setText("Use the arrow keys to move the tiles");
		instructions.setForeground(new Color(null, 255, 0, 0));
		shell.forceFocus();

		shell.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN || e.keyCode == SWT.ARROW_LEFT
						|| e.keyCode == SWT.ARROW_RIGHT || e.keyCode == SWT.ARROW_UP) {
					userCommand = e.keyCode;
					instructions.setVisible(false);
					setChanged();
					notifyObservers();
				} else {
					instructions.setVisible(true);
				}

			}
		});

		shell.open();
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
		board.updateBoard(state.getBoard());
		score.setText("Score: " + state.getScore());

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
		MenuItem restartItem = new MenuItem(fileMenu, SWT.NONE);
		restartItem.setText("Restart");

		// Create the first separator
		new MenuItem(fileMenu, SWT.SEPARATOR);

		MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
		saveItem.setText("Save");
		MenuItem LoadItem = new MenuItem(fileMenu, SWT.NONE);
		LoadItem.setText("Load");

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
	}

	private void initButtons() {
		Button undoMove = new Button(shell, SWT.PUSH);
		undoMove.setText("Undo Move");
		undoMove.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1,	1));
		
		score = new Label(shell, SWT.NONE);
		score.setText("Score: 0        ");
		score.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		Button restartGame = new Button(shell, SWT.PUSH);
		restartGame.setText("Restart Game");
		restartGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false,1, 1));

		board = new Board(shell, SWT.BORDER);
		board.setBoard(boardData);
		board.setGameColors(new Color(null, 199, 193, 173), new Color(null,230, 227, 220));
		board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));

		Button loadGame = new Button(shell, SWT.PUSH);
		loadGame.setText("Load Game");
		loadGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1,1));
		
		Button saveGame = new Button(shell, SWT.PUSH);
		saveGame.setText("Save Game");
		saveGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1,2));

		restartGame.addListener(SWT.Selection, restartGame());
		undoMove.addListener(SWT.Selection, undoMove());
	}

	
	
	private Listener restartGame() {
		return (new Listener() {

			@Override
			public void handleEvent(Event e) {
				userCommand = 0;
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
				userCommand = -1;
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});
	}
}
