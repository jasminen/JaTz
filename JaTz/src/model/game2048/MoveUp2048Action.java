
package model.game2048;


import common.State;

import model.Action;

/**
 * Move up action in 2048 game. Implements Action.
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public class MoveUp2048Action implements Action {

	
	/**
	 * Move up
	 * @param state
	 * @return new state after moving up
	 */
	@Override
	public State doAction(State state) {
		State newState = new State(state);
		int[][] newBoard=state.getCopyBoard();
		int newScore=state.getScore();
		
		for (int i = 0; i < newBoard[0].length ; i++) {
			Boolean sumFlag=true;
			
			for (int j = 1; j < newBoard.length; j++) {
				
				if(newBoard[j][i] ==0)
					continue;
				
				for (int h = j; h>0 ; h--) {
					
					if(newBoard[h-1][i] == 0 ) {
						newBoard[h-1][i]=newBoard[h][i];
						newBoard[h][i]=0;
						continue;
					}
						
					else if(newBoard[h-1][i] == newBoard[h][i] && sumFlag) {
						newBoard[h-1][i]+=newBoard[h][i];
						newBoard[h][i]=0;
						sumFlag=false;	
						newScore+=newBoard[h-1][i];
						break;
					}
					else {
						sumFlag=true;
						break;
					}			
				}
			}
		}
		
		newState.setBoard(newBoard);
		newState.setScore(newScore);
		return newState;
	}

}

