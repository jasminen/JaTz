package backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import common.Message;
import backend.minimax.ai.AIsolver;

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
			output.writeObject(new Message(null, "You are connected to " + this.serverText, 0, null));
			output.flush();
			while (true) {
				Message messageIn = (Message) input.readObject();
				System.out.println("message from the client: " + messageIn.getMsg());
				if (messageIn.getMsg().equals("exit")) {
					System.out.println("Client says goodbye");
					break;
				} else if (messageIn.getMsg().equals("getHint") && messageIn.getGame().equals("2048")) {
					int direction = AIsolver.findBestMove(messageIn.getState());
					output.writeObject(new Message(null, "This is the best next move", direction, messageIn.getGame()));
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
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}