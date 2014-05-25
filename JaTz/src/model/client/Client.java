
package model.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;


import common.Message;
/**
 * 
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */
public class Client implements Callable<Message> {


	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Message message;
	
	
	/**
	 * C'tor
	 * @param output
	 * @param input
	 * @param message - the message that should be sent to server
	 */
	public Client(ObjectOutputStream output, ObjectInputStream input, Message message ) {
		this.output = output;
		this.input = input;
		this.message = message;
	}
	
	
	@Override
	public Message call() throws Exception {
		return getHint();		
	}
	
	
	/**
	 * Ask for hint from solver server - send the message
	 * @return Message - the message received from server
	 */
public Message getHint() {
		
	try {
		output.writeObject(message);
		output.flush();
		return (Message) input.readObject();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace(); 
	}

	return null;
}


}
