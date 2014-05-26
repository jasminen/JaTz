package view.gamesMaze2048;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import common.Keys;

/**
 * Abstract Arrow Diagonal class implements KeyListener 
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */
public abstract class AbsArrowDiagonalKeysListener implements KeyListener {

	int command;
	Boolean keyFlag = false;
	int lastKeyEventCode = 0;
	Timer tm = new Timer();
	
	/**
	 * Support keys UP, DOWN, LEFT, RIGHT, RIGHT_DOWN, LEFT_DOWN, RIGHT_UP, LEFT_UP 
	 */
	@Override
	public void keyPressed(final KeyEvent e) {
		// The timer is waiting for the second key press for 50 millisec if there isn't any it will proceed.
		if (e.keyCode == SWT.ARROW_DOWN || e.keyCode == SWT.ARROW_LEFT || e.keyCode == SWT.ARROW_RIGHT || e.keyCode == SWT.ARROW_UP) {
			setInstructions(false);
			if (keyFlag == false) {
				keyFlag = true;
				lastKeyEventCode = e.keyCode;
				tm = new Timer();
				
				tm.schedule(new TimerTask() { //Scheduled after 50 millisec

					@Override
					public void run() {
						if (keyFlag == false && lastKeyEventCode != e.keyCode) {
							command =((e.keyCode==SWT.ARROW_LEFT || lastKeyEventCode==SWT.ARROW_LEFT)?e.keyCode+100+lastKeyEventCode:e.keyCode-20+lastKeyEventCode);
						} else {
							keyFlag = false;
							command = e.keyCode;
						}
						
						switch (command) {
						case SWT.ARROW_DOWN:
							setUserCommand(Keys.DOWN);
							break;
						case SWT.ARROW_UP:
							setUserCommand(Keys.UP);
							break;
						case SWT.ARROW_LEFT:
							setUserCommand(Keys.LEFT);
							break;
						case SWT.ARROW_RIGHT:
							setUserCommand(Keys.RIGHT);
							break;
						case SWT.ARROW_LEFT + SWT.ARROW_DOWN + 100:
							setUserCommand(Keys.DIAGONAL_LEFT_DOWN);
							break;
						case SWT.ARROW_RIGHT + SWT.ARROW_DOWN - 20:
							setUserCommand(Keys.DIAGONAL_RIGHT_DOWN);
							break;
						case SWT.ARROW_LEFT + SWT.ARROW_UP + 100:
							setUserCommand(Keys.DIAGONAL_LEFT_UP);
							break;
						case SWT.ARROW_RIGHT + SWT.ARROW_UP - 20:
							setUserCommand(Keys.DIAGONAL_RIGHT_UP);
							break;
						default:
							break;	
						}
						
					}
				}, 50); //End tm.schedule
				
			} else {
				keyFlag = false;
				lastKeyEventCode = e.keyCode;
			}
			
		} else {
			setInstructions(true);
		}
	}

	

	@Override
	public void keyReleased(KeyEvent e) {
		tm.cancel();
	}

	
	
	public abstract void setUserCommand(int userCommand);
	public abstract void setInstructions(Boolean flag);
}
