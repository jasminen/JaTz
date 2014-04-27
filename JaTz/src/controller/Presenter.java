package controller;

import model.Model;
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
		commandToAction.put(Keys.RESTART, Keys.RESTART);
	}
	
	
	@Override
	public void update(Observable o, Object arg1) {
		if(o==gui)
		{
			model.doAction(commandToAction.get(gui.getUserCommand()));
		}
		
		if(o==model)
		{
			gui.displayState(model.getState());
		}
		
		
	}
	
	
	
}
