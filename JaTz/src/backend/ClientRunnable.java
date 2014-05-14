package backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public ClientRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
	        	ObjectOutputStream output=new ObjectOutputStream(this.clientSocket.getOutputStream());
	        	ObjectInputStream input=new ObjectInputStream(this.clientSocket.getInputStream());
	        	output.writeObject("You are connected to the server" + this.serverText);
	        	output.flush();
	        	String messageIn=(String)input.readObject();
	        	System.out.println("message from the client: "+messageIn);
	        	output.writeObject("bye");
	        	output.flush();

//            long time = System.currentTimeMillis();
//            output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
//                    this.serverText + " - " +
//                    time +
//                    "").getBytes());
            output.close();
            input.close();
            System.out.println("Request processed");
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}