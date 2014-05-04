package view.gamesMaze2048;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import controller.Keys;

public abstract class AbsArrowKeysListener implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		
		setInstructions(false);
		switch (e.keyCode) {
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
		default:
			setInstructions(true);
			break;	
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	
	public abstract void setUserCommand(int userCommand);
	public abstract void setInstructions(Boolean flag);
}
