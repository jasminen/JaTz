package model.Game2048;



import model.Action;
import model.State;

public class MoveDown2048Action implements Action {

	@Override
	public State doAction(State state) {
		int[][] newBoard=state.getBoard().clone();
		int newScore=state.getScore();
		
		for (int i = 0; i < newBoard[0].length ; i++) {
			Boolean sumFlag=true;
			
			for (int j = newBoard.length-2; j >=0 ; j--) {
				
				if(newBoard[j][i] ==0)
					continue;
				
				for (int h = j; h<newBoard.length-1 ; h++) {
					
					if(newBoard[h+1][i] == 0 ) {
						newBoard[h+1][i]=newBoard[h][i];
						newBoard[h][i]=0;
						continue;
					}
						
					else if(newBoard[h+1][i] == newBoard[h][i] && sumFlag) {
						newBoard[h+1][i]+=newBoard[h][i];
						newBoard[h][i]=0;
						sumFlag=false;	
						newScore+=newBoard[h+1][i];
						break;
					}
					else {
						sumFlag=true;
						break;
					}			
				}
			}
		}

		return new State(newBoard, newScore);
	}

}