package model.game2048;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.swt.graphics.Point;

import common.Keys;
import common.Message;
import common.SLhelper;
import common.State;
import model.AbsModel;
import model.client.Client;

/*
 * Game2048Model 
 */

public class Game2048Model extends AbsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private int boardSize;
	private int winNum;
	private Boolean alreadyWon = false;
	private Socket myServer;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Boolean connectedToServer = false;



	public Game2048Model() {
		this.boardSize = 4;
		this.winNum = 2048;
		
	}

	// C'tor - gets the win number of the game (a power of two).
	public Game2048Model(int winNum) {
		
		if (isPowerOfTwo(winNum))
			this.winNum = winNum;
		else {
			System.out.println("Not a power of two, setting to default 2048.");
			this.winNum = 2048;
		}
		this.boardSize = 4;
	}

	@Override
	public void moveUp() {
		endPhase(new MoveUp2048Action().doAction(getState()));

	}

	@Override
	public void moveDown() {
		endPhase(new MoveDown2048Action().doAction(getState()));

	}

	@Override
	public void moveLeft() {
		endPhase(new MoveLeft2048Action().doAction(getState()));

	}

	@Override
	public void moveRight() {
		endPhase(new MoveRight2048Action().doAction(getState()));

	}

	@Override
	public void restart() {
		this.states.clear();
		this.states.add(getStartState());
		this.alreadyWon = false;
		setChanged();
		notifyObservers();
	}
	
	

	@Override
	protected State getStartState() {
		int[][] board = new int[boardSize][boardSize];
		State state = new State(board, 0);
		DrawNewNumber(state);
		DrawNewNumber(state);

		return state;
	}
	
	@Override
	public void getHint(Integer iterations) {
		if(iterations == null) {
			iterations = 0;
		}
		
		while(!(alreadyWon || getState().getMode() == Keys.GAMEOVER) && iterations > 0) {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Future<Message> result = executor.submit(new Client(output, input, new Message(getState(), "getHint", 0, "2048")));
			executor.shutdown();
			try {
				performAction(result.get().getResult());
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			iterations--;

		}
	}
	
	
	@Override
	public void fullSolver() {
		while(!(alreadyWon || getState().getMode() == Keys.GAMEOVER)) {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Future<Message> result = executor.submit(new Client(output, input, new Message(getState(), "getHint", 0, "2048")));
			executor.shutdown();
			try {
				performAction(result.get().getResult());
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public void connectToServer(InetSocketAddress socketAddress) {
		if(!isConnectedToServer()) {
			try {
				myServer = new Socket(socketAddress.getAddress(), socketAddress.getPort());
				output = new ObjectOutputStream(myServer.getOutputStream());
				input = new ObjectInputStream(myServer.getInputStream());
				Message messageFromServer = (Message) input.readObject();
				System.out.println("message from server: " + messageFromServer.getMsg());
				setConnectedToServer(true);
				ArrayList<String> ips = (ArrayList<String>) SLhelper.load("conf/serverIPs.xml");
				if(!ips.contains(socketAddress.getAddress().getHostAddress())){
					ips.add(socketAddress.getAddress().getHostAddress());
				}
				SLhelper.save(ips, "conf/serverIPs.xml");
				setChanged();
				notifyObservers(connectedToServer);
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("server not found");
				setConnectedToServer(false);
				setChanged();
				notifyObservers(connectedToServer);
			} catch (Exception e) {
				System.out.println("Can't save the serverIP.xml");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void disconnectFromServer() {
		try {
			output.writeObject(new Message(null, "exit", 0, "2048"));
			output.flush();
			output.close();
			input.close();
			myServer.close();
			setConnectedToServer(false);
			setChanged();
			notifyObservers(connectedToServer);
		} catch (IOException | NullPointerException e) {
			System.out.println("Not Connected");
//			e.printStackTrace();
		}
	}
	
	@Override
	public void load(String path) {
		try {
			Game2048Model model = (Game2048Model) SLhelper.load(path);
			this.alreadyWon = model.alreadyWon;
			this.boardSize = model.boardSize;
			this.states = model.states;
			this.winNum = model.winNum;
			setChanged();
			notifyObservers();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Check if the mode needs to be change. if not, draw a new number. Add to
	// states array and notify.
	private void endPhase(State newState) {
		if (newState != null  && !newState.equals(getState())) {
			if (newState.getMode() == Keys.WIN) { // Let the game continue after winning
				newState.setMode(Keys.IN_PROGRESS);
			} if (newState.hasFreeCells()) {
				DrawNewNumber(newState);
			} if (!gotAvailableMoves(newState)) {
					newState.setMode(Keys.GAMEOVER);
			} if (alreadyWon == false && win(newState.getCopyBoard())) { // Check winning only if didn't win before.
				newState.setMode(Keys.WIN);
			}
			this.states.add(newState);
			setChanged();
			notifyObservers();
		}
	}

	private void DrawNewNumber(State state) {
		ArrayList<Point> freeCells = state.getEmptyCellIds();

		// Choose a random cell out of the free cells array and update the
		// relevant array in board
		int cell = new Random().nextInt(freeCells.size());
		int row = freeCells.get(cell).x;
		int column = freeCells.get(cell).y;
		if (new Random().nextInt(10) < 9)
			state.setCell(row, column, 2);
		else
			state.setCell(row, column, 4);
	}

	
	private Boolean win(int[][] board) {
		for (int[] r : board)
			for (int c : r)
				if (c == this.winNum) {
					this.alreadyWon = true;
					return true;
				}
		return false;

	}

	private Boolean gotAvailableMoves(State state) {
		State up = new MoveUp2048Action().doAction(state);
		State down = new MoveDown2048Action().doAction(state);
		State right = new MoveRight2048Action().doAction(state);
		State left = new MoveLeft2048Action().doAction(state);

		if (state.equals(up) && state.equals(down) && state.equals(right)
				&& state.equals(left)) {
			return false;
		}

		return true;

	}
	
	private void performAction(int action) {
		switch (action) {
		case Keys.UP:
			moveUp();
			break;
		case Keys.DOWN:
			moveDown();
			break;
		case Keys.LEFT:
			moveLeft();
			break;
		case Keys.RIGHT:
			moveRight();
			break;
		default:
			break;
		}
		
	}

	private static boolean isPowerOfTwo(int number) {
		if (number <= 0) {
			return false;
		}
		if ((number & -number) == number) {
			return true;
		}
		return false;
	}

	public int getWinNum() {
		return this.winNum;
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public Boolean getAlreadyWon() {
		return alreadyWon;
	}

	public void setAlreadyWon(Boolean alreadyWon) {
		this.alreadyWon = alreadyWon;
	}
	
	public Socket getMyServer() {
		return myServer;
	}

	public void setMyServer(Socket myServer) {
		this.myServer = myServer;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public void setInput(ObjectInputStream input) {
		this.input = input;
	}

	public Boolean isConnectedToServer() {
		return connectedToServer;
	}

	public void setConnectedToServer(Boolean connectedToServer) {
		this.connectedToServer = connectedToServer;
	}

	



}
