
package model.game2048;


import common.State;

import model.Action;

/**
 * Move right action in 2048 game. Implements Action.
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */
public class MoveRight2048Action implements Action {

	
	/**
	 * Move right
	 * @param state
	 * @return new state after moving right
	 */
	@Override
	public State doAction(State state) {
		State newState = new State(state);
		int[][] newBoard=state.getCopyBoard();
		int newScore=state.getScore();
		
		for (int i = 0; i < newBoard.length ; i++) {
			Boolean sumFlag=true;
			
			for (int j = newBoard[0].length-2; j >=0 ; j--) {
				
				if(newBoard[i][j] ==0)
					continue;
				
				for (int h = j; h<newBoard[0].length-1 ; h++) {
					
					if(newBoard[i][h+1] == 0 ) {
						newBoard[i][h+1]=newBoard[i][h];
						newBoard[i][h]=0;
						continue;
					}
						
					else if(newBoard[i][h+1] == newBoard[i][h] && sumFlag) {
						newBoard[i][h+1]+=newBoard[i][h];
						newBoard[i][h]=0;
						sumFlag=false;	
						newScore+=newBoard[i][h+1];
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

