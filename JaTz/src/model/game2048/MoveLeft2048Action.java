
package model.game2048;

import model.Action;
import model.State;


public class MoveLeft2048Action implements Action {

	@Override
	public State doAction(State state) {
		int[][] newBoard=state.getBoard();
		int newScore=state.getScore();
		
		for (int i = 0; i < newBoard.length ; i++) {
			Boolean sumFlag=true;
			
			for (int j = 1; j < newBoard[0].length; j++) {
				
				if(newBoard[i][j] ==0)
					continue;
				
				for (int h = j; h>0 ; h--) {
					
					if(newBoard[i][h-1] == 0 ) {
						newBoard[i][h-1]=newBoard[i][h];
						newBoard[i][h]=0;
						continue;
					}
						
					else if(newBoard[i][h-1] == newBoard[i][h] && sumFlag) {
						newBoard[i][h-1]+=newBoard[i][h];
						newBoard[i][h]=0;
						sumFlag=false;	
						newScore+=newBoard[i][h-1];
						break;
					}
					else {
						sumFlag=true;
						break;
					}			
				}
			}
		}
		
		return new State(newBoard, newScore, state.getMode());
	}

}

