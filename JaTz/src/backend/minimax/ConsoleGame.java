package backend.minimax;

import java.util.Scanner;

import model.State;
import backend.minimax.ai.AIsolver;
import backend.minimax.dataobjects.ActionStatus;
import backend.minimax.dataobjects.Direction;
import backend.minimax.game.Board;

/**
 * The main class of the Game.
 * 
 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
 */
public class ConsoleGame {

    /**
     * Main function of the game.
     * 
     * @param args
     * @throws CloneNotSupportedException 
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        
    	State state = new State();
    	calculateNextMoves(state);
           
    }
    
    
    /**
     * Estimates the accuracy of the AI solver by running multiple games.
     * 
     * @throws CloneNotSupportedException 
     */
    public static String calculateNextMoves(State currentState) throws CloneNotSupportedException {
        int wins=0;
        System.out.println("Running");
        
        	Board theGame = new Board(currentState.getCopyBoard(), currentState.getScore());
            int hintDepth = 7;
           
            Direction hint = AIsolver.findBestMove(theGame, hintDepth);
            ActionStatus result=ActionStatus.CONTINUE;
            printBoard(theGame.getBoardArray(), theGame.getScore(), hint);
       /*     while(result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE) {
                result=theGame.action(hint);
                if(result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE ) {
                    hint = AIsolver.findBestMove(theGame, hintDepth);
                }
            }

            if(result == ActionStatus.WIN) {
                ++wins;
                System.out.println("Game "+(i+1)+" - won");
                return "win";
            }
            else {
                System.out.println("Game "+(i+1)+" - lost");
            }
       */
        
		return "bla";
    }
    
    
    
    /**
     * Prints the Board
     * 
     * @param boardArray
     * @param score 
     * @param hint 
     */
    public static void printBoard(int[][] boardArray, int score, Direction hint) {
        System.out.println("-------------------------");
        System.out.println("Score:\t" + String.valueOf(score));
        System.out.println();
        System.out.println("Hint:\t" + hint);
        System.out.println();
        
        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray[i].length;++j) {
                System.out.print(boardArray[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }
}
