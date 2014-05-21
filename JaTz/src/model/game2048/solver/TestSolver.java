package model.game2048.solver;


import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import common.Keys;
import common.State;
import model.game2048.Game2048Model;
import model.game2048.MoveDown2048Action;
import model.game2048.MoveLeft2048Action;
import model.game2048.MoveRight2048Action;

public class TestSolver {
	
	private static Game2048Model model = new Game2048Model();
	
	
	
	public static void main(String[] args) {
		model.newGame();
		State state = model.getState();
		
		State down, left,right;
		
		while(!(state.getMode() == Keys.GAMEOVER) && !(state.getMode() == Keys.WIN )) {
			
			
			state = model.getState();
			
			
			
			down = new MoveDown2048Action().doAction(model.getState());
			
			left = new MoveLeft2048Action().doAction(model.getState());
			
			right = new MoveRight2048Action().doAction(model.getState());
			
			
			
			if(state.equals(down) && state.equals(left) && state.equals(right)) {
				model.moveUp();
				model.moveDown();
			} else {
				
				
				if(left.getScore() >= right.getScore() && left.getScore() >= down.getScore() && !state.equals(left)) {
					model.moveLeft();
					System.out.println("left");
				}
					
				else if(right.getScore() >= left.getScore() && right.getScore() >= down.getScore() && !state.equals(right)) {
					model.moveRight();
					System.out.println("right");
				}
				
				else {
					model.moveDown();
					System.out.println("down");
				}
			}
			
			
			state = model.getState();
			System.out.println(state);
			
		/*	
			while(!state.equals(newState)) {
				state = model.getState();
				model.moveLeft();
				model.moveDown();
				newState=model.getState();
			}
			
			model.moveRight();
	//		model.moveDown();
			state = model.getState();			
		*/	
		}
		
		
			System.out.println(model.getState());
		
		
		
		
		
	}

}
