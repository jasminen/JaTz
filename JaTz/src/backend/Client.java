package backend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		try{
			 InetAddress address=InetAddress.getByName("192.168.13.200");
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
