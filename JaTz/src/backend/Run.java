package backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Run {

	public static void main(String[] args) throws IOException {
//		InetAddress localaddr = InetAddress.getLocalHost();
//		System.out.println ("Local hostname : " + localaddr.getHostName());
//		System.out.println ("Local hostname : " + localaddr.getHostAddress());

		ThreadPooledServer server = new ThreadPooledServer(9000);
		new Thread(server).start();

//		try {
//		    Thread.sleep(5 * 1000);
//		} catch (InterruptedException e) {
//		    e.printStackTrace();
//		}
//		System.out.println("Stopping Server");
//		server.stop();
	}

}
