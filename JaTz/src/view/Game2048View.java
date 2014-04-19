package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import model.State;

public class Game2048View extends Observable implements View, Runnable {

	Display display;
	Shell shell;
	int[][] boardData = new int[][] {{0,0,0,0},{2,0,4,0},{2,8,0,0},{0,2,0,0}};

	private void initComponents() {
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));
		shell.setSize(400, 300);
		shell.setText("2048 Game");

		Button undoMove = new Button(shell, SWT.PUSH);
		undoMove.setText("Undo Move");
		undoMove.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		Board board = new Board(shell, SWT.BORDER);
		board.setBoard(boardData);
		board.setGameColors(new Color(null, 199, 193, 173), new Color(null, 230, 227, 220));
		board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));
		Button restartGame = new Button(shell, SWT.PUSH);
		restartGame.setText("Restart Game");
		restartGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		Button loadGame = new Button(shell, SWT.PUSH);
		loadGame.setText("Load Game");
		loadGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		Button saveGame = new Button(shell, SWT.PUSH);
		saveGame.setText("Load Game");
		saveGame.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		
		
		shell.open();
	}

	@Override
	public void run() {
		initComponents();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	@Override
	public void displayState(State state) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getUserCommand() {
		// TODO Auto-generated method stub
		return 0;
	}

}
