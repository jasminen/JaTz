package backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import backend.minimax.ConsoleGame;

public class ClientRunnable implements Runnable {

	protected Socket clientSocket = null;
	protected String serverText = null;

	public ClientRunnable(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
	}

	public void run() {
		try {
			ObjectOutputStream output = new ObjectOutputStream(this.clientSocket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(this.clientSocket.getInputStream());
			output.writeObject("You are connected to " + this.serverText);
			output.flush();
			while (true) {
				String messageIn = (String) input.readObject();
				System.out.println("message from the client: " + messageIn);
				if (messageIn.equals("exit")) {
					System.out.println("Client say goodbye");
					break;
				} else if (messageIn.equals("fullSolver")) {
//					String state = ConsoleGame.calculateNextMoves();
//					output.writeObject(state);
				}
			}
			output.close();
			input.close();
			System.out.println("Request processed");
		} catch (SocketException e) {
			System.out.println("User close the connection");
		}
		catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
//		catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}