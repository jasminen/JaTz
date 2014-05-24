
package model.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;


import common.Message;

public class Client implements Callable<Message> {


	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Message message;
	
	
	public Client(ObjectOutputStream output, ObjectInputStream input, Message message ) {
		this.output = output;
		this.input = input;
		this.message = message;
	}
	
	
	
	@Override
	public Message call() throws Exception {
		return getHint();		
	}
	
	
	
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
