package controller;

import model.Model;
import view.View;

import java.util.Observable;
import java.util.Observer; 


public class Presenter implements Observer {

	private View ui;
	private Model model;
	
	
	@Override
	public void update(Observable o, Object arg1) {
		
		if(o==ui)
		{
			///
		}
		
		if(o==model)
		{
			////
		}
		
		
	}
	
}
