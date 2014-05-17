package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

//EXAMPLE USE ONLY
public class Client {

	public void connect() {
		try{
			 InetAddress address=InetAddress.getByName("192.168.12.102");
			 Socket myServer = new Socket(address, 9000);
			 ObjectOutputStream output=new ObjectOutputStream(myServer.getOutputStream());
			 ObjectInputStream input=new ObjectInputStream(myServer.getInputStream());

			 String messageFromServer=(String)input.readObject();
			 System.out.println("message from server: "+messageFromServer);
			 output.writeObject("networking is so simple in java");
			 output.flush();

//			 output.close();
//			 input.close();
//			 myServer.close();

			}catch (Exception e) {}


	}
}
