package controller;

import model.Model;
import view.View;
import java.util.Observable;
import java.util.Observer; 


public class Presenter implements Observer {

	private View gui;
	private Model model;
	
	public Presenter(Model model, View gui) {
		this.model = model;
		this.gui = gui;
	}
	
	
	@Override
	public void update(Observable o, Object arg1) {
		if(o==gui)
		{
			model.doAction(gui.getUserCommand());
		}
		
		if(o==model)
		{
			gui.displayState(model.getState());
		}
		
		
	}
	
	
	
}
