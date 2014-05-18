package backend.minimax.ai;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.minimax.ConsoleGame;
import backend.minimax.dataobjects.Direction;
import backend.minimax.game.Board;

/**
 * The AIsolver class that uses Artificial Intelligence to estimate the next
 * move.
 * 
 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
 */
public class AIsolver {

	/**
	 * Player vs Computer enum class
	 */
	public enum Player {
		/**
		 * Computer
		 */
		COMPUTER,

		/**
		 * User
		 */
		USER
	}

	/**
	 * Method that finds the best next move.
	 * 
	 * @param theBoard
	 * @param depth
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public static Direction findBestMove(Board theBoard, int depth)
			throws CloneNotSupportedException {
		// Map<String, Object> result = minimax(theBoard, depth, Player.USER);

		 Map<String, Object> result = alphabeta(theBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);

//		Map<String, Object> result = hMan(theBoard);
		return (Direction) result.get("Direction");
	}

	private static Map<String, Object> hMan(Board theBoard)throws CloneNotSupportedException {
		Boolean rightFlag = false;
		Map<String, Object> result = new HashMap<>();
		Direction direction = null;
		Board newBoard = (Board) theBoard.clone();
		ConsoleGame.printBoard(theBoard.getBoardArray(), 0, null);
		if (rightFlag) {
			direction = Direction.LEFT;
			newBoard.move(direction);
			rightFlag = false;
		}
		else if (rowFreeCell(theBoard, 0)) {
			direction = Direction.UP;
			newBoard.move(direction);
			if (newBoard.isEqual(theBoard.getBoardArray(), newBoard.getBoardArray())) {
				direction = Direction.LEFT;
				newBoard.move(direction);
				if (newBoard.isEqual(theBoard.getBoardArray(), newBoard.getBoardArray())) {
					direction = Direction.RIGHT;
					newBoard.move(direction);
					if (rowFreeCell(newBoard, 0)) {
						rightFlag = true;
					}
				}
			}
		} else {
			Point p;
			if (aLike(theBoard, 0) != null) {
				System.out.println("Top row LEFT");
				direction = Direction.LEFT;
				newBoard.move(direction);
				
			} else {
				p = aLike(theBoard, 0, 1);
				System.out.println("P: 2 row");
				if (p != null) {
					int direc = p.x - p.y;
					System.out.println("Direc: " + direc + " x/i: " + p.x + " y/j: " + p.y + " Free cell: " + rowFreeCell(theBoard, 1) );
					if (direc < 0 && rowFreeCell(theBoard, 1)) {
						System.out.println("LEFT");
						direction = Direction.LEFT;
						newBoard.move(direction);
					} else if (direc > 0 && rowFreeCell(theBoard, 1)) {
						System.out.println("RIGHT");
						direction = Direction.RIGHT;
						newBoard.move(direction);
						if (rowFreeCell(newBoard, 0)) {
							rightFlag = true;
						}
					} else {
						System.out.println("UP");
						direction = Direction.UP;
						newBoard.move(direction);
					}
				}
			}
			if(newBoard.isEqual(theBoard.getBoardArray(), newBoard.getBoardArray())) {
				direction = Direction.UP;
				newBoard.move(direction);
				if (newBoard.isEqual(theBoard.getBoardArray(), newBoard.getBoardArray())) {
					direction = Direction.LEFT;
					newBoard.move(direction);
					if (newBoard.isEqual(theBoard.getBoardArray(), newBoard.getBoardArray())) {
						direction = Direction.RIGHT;
						newBoard.move(direction);
						if (rowFreeCell(newBoard, 0)) {
							rightFlag = true;
						}
					}
				}
			}
		}

		result.put("Direction", direction);
		return result;
	}

	private static boolean rowFreeCell(Board theBoard, int rowNumber) {
		int[][] board = theBoard.getBoardArray();
		for (int i = 0; i < board[rowNumber].length; i++) {
			if (board[rowNumber][i] == 0) {
				return true;
			}
		}
		return false;
	}
	
	private static Point aLike(Board theBoard, int rowNumber1, int rowNumber2) {
		int disPoints, bestDisPoints = 10;
		Point points = null;
		int[][] board = theBoard.getBoardArray();
		for (int i = 0; i < board[rowNumber1].length; i++) {
			for (int j = 0; j < board[rowNumber2].length; j++) {
				if (board[rowNumber1][i] == board[rowNumber2][j]) {
					disPoints = Math.abs(i-j);
					if(disPoints < bestDisPoints) {
						bestDisPoints = disPoints;
						points = new Point(i, j);
					}
				}
			}
		}
		return points;
	}
	private static Point aLike(Board theBoard, int rowNumber) {
		Point points = null;
		int[][] board = theBoard.getBoardArray();
		System.out.println("waa");
		for (int i = 0; i < board[rowNumber].length-1; i++) {
				if (board[rowNumber][i] == board[rowNumber][i+1]) {
						points = new Point(i, i+1);
						return points;
				}
		}
		return points;
	}

	/**
	 * Finds the best move by using the Minimax algorithm.
	 * 
	 * @param theBoard
	 * @param depth
	 * @param player
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private static Map<String, Object> minimax(Board theBoard, int depth, Player player) throws CloneNotSupportedException {
		Map<String, Object> result = new HashMap<>();

		Direction bestDirection = null;
		int bestScore;

		if (depth == 0 || theBoard.isGameTerminated()) {
			bestScore = heuristicScore(theBoard.getScore(),
					theBoard.getNumberOfEmptyCells(),
					calculateClusteringScore(theBoard.getBoardArray()));
		} else {
			if (player == Player.USER) {
				bestScore = Integer.MIN_VALUE;

				for (Direction direction : Direction.values()) {
					Board newBoard = (Board) theBoard.clone();

					int points = newBoard.move(direction);

					if (points == 0
							&& newBoard.isEqual(theBoard.getBoardArray(),
									newBoard.getBoardArray())) {
						continue;
					}

					Map<String, Object> currentResult = minimax(newBoard,
							depth - 1, Player.COMPUTER);
					int currentScore = ((Number) currentResult.get("Score"))
							.intValue();
					if (currentScore > bestScore) { // maximize score
						bestScore = currentScore;
						bestDirection = direction;
					}
				}
			} else {
				bestScore = Integer.MAX_VALUE;

				List<Integer> moves = theBoard.getEmptyCellIds();
				if (moves.isEmpty()) {
					bestScore = 0;
				}
				int[] possibleValues = { 2, 4 };

				int i, j;
				int[][] boardArray;
				for (Integer cellId : moves) {
					i = cellId / Board.BOARD_SIZE;
					j = cellId % Board.BOARD_SIZE;

					for (int value : possibleValues) {
						Board newBoard = (Board) theBoard.clone();
						newBoard.setEmptyCell(i, j, value);

						Map<String, Object> currentResult = minimax(newBoard,
								depth - 1, Player.USER);
						int currentScore = ((Number) currentResult.get("Score"))
								.intValue();
						if (currentScore < bestScore) { // minimize best score
							bestScore = currentScore;
						}
					}
				}
			}
		}

		result.put("Score", bestScore);
		result.put("Direction", bestDirection);

		return result;
	}

	/**
	 * Finds the best move bay using the Alpha-Beta pruning algorithm.
	 * 
	 * @param theBoard
	 * @param depth
	 * @param alpha
	 * @param beta
	 * @param player
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private static Map<String, Object> alphabeta(Board theBoard, int depth,
			int alpha, int beta, Player player)
			throws CloneNotSupportedException {
		Map<String, Object> result = new HashMap<>();

		Direction bestDirection = null;
		int bestScore;

		if (theBoard.isGameTerminated()) {
			if (theBoard.hasWon()) {
				bestScore = Integer.MAX_VALUE; // highest possible score
			} else {
				bestScore = Math.min(theBoard.getScore(), 1); // lowest possible
																// score
			}
		} else if (depth == 0) {
			bestScore = heuristicScore(theBoard.getScore(),
					theBoard.getNumberOfEmptyCells(),
					calculateClusteringScore(theBoard.getBoardArray()));
		} else {
			if (player == Player.USER) {
				for (Direction direction : Direction.values()) {
					Board newBoard = (Board) theBoard.clone();

					int points = newBoard.move(direction);

					if (points == 0
							&& newBoard.isEqual(theBoard.getBoardArray(),
									newBoard.getBoardArray())) {
						continue;
					}

					Map<String, Object> currentResult = alphabeta(newBoard,
							depth - 1, alpha, beta, Player.COMPUTER);
					int currentScore = ((Number) currentResult.get("Score"))
							.intValue();

					if (currentScore > alpha) { // maximize score
						alpha = currentScore;
						bestDirection = direction;
					}

					if (beta <= alpha) {
						break; // beta cutoff
					}
				}

				bestScore = alpha;
			} else {
				List<Integer> moves = theBoard.getEmptyCellIds();
				int[] possibleValues = { 2, 4 };

				int i, j;
				abloop: for (Integer cellId : moves) {
					i = cellId / Board.BOARD_SIZE;
					j = cellId % Board.BOARD_SIZE;

					for (int value : possibleValues) {
						Board newBoard = (Board) theBoard.clone();
						newBoard.setEmptyCell(i, j, value);

						Map<String, Object> currentResult = alphabeta(newBoard,
								depth - 1, alpha, beta, Player.USER);
						int currentScore = ((Number) currentResult.get("Score"))
								.intValue();
						if (currentScore < beta) { // minimize best score
							beta = currentScore;
						}

						if (beta <= alpha) {
							break abloop; // alpha cutoff
						}
					}
				}

				bestScore = beta;

				if (moves.isEmpty()) {
					bestScore = 0;
				}
			}
		}

		result.put("Score", bestScore);
		result.put("Direction", bestDirection);

		return result;
	}

	/**
	 * Estimates a heuristic score by taking into account the real score, the
	 * number of empty cells and the clustering score of the board.
	 * 
	 * @param actualScore
	 * @param numberOfEmptyCells
	 * @param clusteringScore
	 * @return
	 */
	private static int heuristicScore(int actualScore, int numberOfEmptyCells,
			int clusteringScore) {
		int score = (int) (actualScore + Math.log(actualScore)
				* numberOfEmptyCells - clusteringScore);
		return Math.max(score, Math.min(actualScore, 1));
	}

	/**
	 * Calculates a heuristic variance-like score that measures how clustered
	 * the board is.
	 * 
	 * @param boardArray
	 * @return
	 */
	private static int calculateClusteringScore(int[][] boardArray) {
		int clusteringScore = 0;

		int[] neighbors = { -1, 0, 1 };

		for (int i = 0; i < boardArray.length; ++i) {
			for (int j = 0; j < boardArray.length; ++j) {
				if (boardArray[i][j] == 0) {
					continue; // ignore empty cells
				}

				// clusteringScore-=boardArray[i][j];

				// for every pixel find the distance from each neightbors
				int numOfNeighbors = 0;
				int sum = 0;
				for (int k : neighbors) {
					int x = i + k;
					if (x < 0 || x >= boardArray.length) {
						continue;
					}
					for (int l : neighbors) {
						int y = j + l;
						if (y < 0 || y >= boardArray.length) {
							continue;
						}

						if (boardArray[x][y] > 0) {
							++numOfNeighbors;
							sum += Math
									.abs(boardArray[i][j] - boardArray[x][y]);
						}

					}
				}

				clusteringScore += sum / numOfNeighbors;
			}
		}

		return clusteringScore;
	}

}
