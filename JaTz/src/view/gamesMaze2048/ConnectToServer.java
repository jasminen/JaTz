package view.gamesMaze2048;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import controller.SLhelper;

public abstract class ConnectToServer implements Listener {
	Button connect;
	Combo ipBox;
	Label serverMsg;
	Display display;
	Shell connectShell;
	Button GetSolver;
	Button Cancel;
	Socket myServer;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	public ConnectToServer(Display display) {
		this.display = display;
	}

	@Override
	public void handleEvent(Event e) {
		connectShell = new Shell(display);
		connectShell.setLayout(new GridLayout(3, false));
		connectShell.setSize(300, 150);
		connectShell.setLocation(600, 500);
		Label title = new Label(connectShell, SWT.NONE);
		title.setText("Connect to the relevant IP, default prot is: 9000");
		title.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.ITALIC));
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));

		ipBox = new Combo(connectShell, SWT.DROP_DOWN);
		ipBox.setText("localhost");
		ipBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		//Dynmic load of selections
		String selections[];
		try {
			selections = (String[]) SLhelper.load(System.getProperty("user.dir")+File.separator+"serverIPs.xml");
			ipBox.setItems(selections);
		} catch (Exception e2) {
			System.out.println("File not exist");
		}

		
		serverMsg = new Label(connectShell, SWT.NONE);
		serverMsg.setText("Server Message: ");
		serverMsg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		connect = new Button(connectShell, SWT.PUSH);
		connect.setText("Connect");
		connect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		connect.addListener(SWT.Selection, connectListener());
		
		Cancel = new Button(connectShell, SWT.PUSH);
		Cancel.setText("Cancel");
		Cancel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		Cancel.addListener(SWT.Selection, cancelListener());
		
		GetSolver = new Button(connectShell, SWT.PUSH);
		GetSolver.setText("Please Solve");
		GetSolver.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		GetSolver.setEnabled(false);
		GetSolver.addListener(SWT.Selection, getSolverListener());
		
		
		
		connectShell.setText("Connect to solver server");
		connectShell.open();
		
	}

	private Listener connectListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				if (connect.getText() == "Connect") {
					try {
//						InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(ipBox.getText()), 9000);
//						myServer = new Socket(address, 9000);
//						System.out.println(address);
						output = new ObjectOutputStream(myServer.getOutputStream());
						input = new ObjectInputStream(myServer.getInputStream());
						String messageFromServer = (String) input.readObject();
						System.out.println("message from server: " + messageFromServer);
						serverMsg.setText("Server Message: " + messageFromServer);
						GetSolver.setEnabled(true);
						
						connect.setText("Disonnect");
						ipBox.add(ipBox.getText());
					} catch (Exception ex) {
						System.out.println("Exception: " + ex);
					}
				} else {
					try {
						output.writeObject("exit");
						output.flush();
						output.close();
						input.close();
						myServer.close();
						GetSolver.setEnabled(false);
						connect.setText("Connect");
					} catch (Exception ex) {
						System.out.println("not connected");
					}
				}

			}
		});
	}
	
	private Listener cancelListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				try{
					 output.writeObject("exit");
					 output.flush();
					 output.close();
					 input.close();
					 myServer.close();
				}catch (Exception ex) {
					System.out.println("not connected");
				}
				try {
					String selections[] = ipBox.getItems(); //NEED TO WORK ON EXIT TOO (X) =====
					SLhelper.save(selections, System.getProperty("user.dir")+File.separator+"serverIPs.xml");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				connectShell.close();
			}
		});
	}
	
	private Listener getSolverListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				try{
					 output.writeObject("fullSolver");
					 output.flush();
					 String messageFromServer = (String)input.readObject();
					 System.out.println("message from server: "+messageFromServer);
					 serverMsg.setText("Server Message: " + messageFromServer);
				}catch (Exception ex) {
					System.out.println("not connected");
				}
			}
		});
	}
	

}
