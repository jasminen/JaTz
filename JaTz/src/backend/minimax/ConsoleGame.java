package backend.minimax;

import java.util.Scanner;

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
        
        System.out.println("The 2048 Game in JAVA!");
        System.out.println("======================");
        System.out.println();
        System.out.println("The Game is developed by Vasilis Vryniotis, visit Datumbox.com for more information. This implementation is based on the ideas and projects of Gabriele Cirulli and Matt Overlan.");
        while(true) {
            printMenu();
            int choice;
            try {
                Scanner sc = new Scanner (System.in);     
                choice = sc.nextInt();
                switch (choice) {
                    case 2:  calculateAccuracy();
                             break;
                    case 3:  help();
                             break;
                    case 4:  return;
                    default: throw new Exception();
                }
            }
            catch(Exception e) {
                System.out.println("Wrong choice");
            }
        }
    }
    
    /**
     * Prints Help menu
     */
    public static void help() {
        System.out.println("Seriously?!?!?");
    }
    
    /**
     * Prints main menu
     */
    public static void printMenu() {
        System.out.println();
        System.out.println("Choices:");
        System.out.println("2. Estimate the Accuracy of AI Solver");
        System.out.println("3. Help");
        System.out.println("4. Quit");
        System.out.println();
        System.out.println("Enter a number from 1-4:");
    }
    
    /**
     * Estimates the accuracy of the AI solver by running multiple games.
     * 
     * @throws CloneNotSupportedException 
     */
    public static String calculateAccuracy() throws CloneNotSupportedException {
        int wins=0;
        int total=10;
        System.out.println("Running "+total+" games to estimate the accuracy:");
        
        for(int i=0;i<total;++i) {
            int hintDepth = 7;
            Board theGame = new Board();
            Direction hint = AIsolver.findBestMove(theGame, hintDepth);
            ActionStatus result=ActionStatus.CONTINUE;
            while(result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE) {
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
        }
        
        System.out.println(wins+" wins out of "+total+" games.");
		return "lost";
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
