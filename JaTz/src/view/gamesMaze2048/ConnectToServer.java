package view.gamesMaze2048;

import java.net.InetAddress;
import java.net.InetSocketAddress;

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

import common.Keys;
import controller.SLhelper;

public abstract class ConnectToServer implements Listener {
	Button connect;
	Combo ipBox;
	Label serverMsg;
	Display display;
	Shell connectShell;
	Button close;
	InetSocketAddress socketAddress;
	
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
		
		ipBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));	
		
		//Dynamic load of selections
		String selections[];
		try {
			selections = (String[]) SLhelper.load("conf/serverIPs.xml");
			ipBox.setItems(selections);
		} catch (Exception e2) {
			System.out.println("File not exist");
		}
		ipBox.setText("localhost");
		
//		serverMsg = new Label(connectShell, SWT.NONE);
//		serverMsg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		connect = new Button(connectShell, SWT.PUSH);
		setConnectButtonText();
		connect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		connect.addListener(SWT.Selection, connectListener());
		
		close = new Button(connectShell, SWT.PUSH);
		close.setText("Close");
		close.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		close.addListener(SWT.Selection, cancelListener());
		
		
		
		
		
		connectShell.setText("Connect to solver server");
		connectShell.open();
		
	}

	private Listener connectListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				if (connect.getText() == "Connect") {
					try {
						socketAddress = new InetSocketAddress(InetAddress.getByName(ipBox.getText()), 9000);
						setUserCommand(Keys.CONNECT);
					} catch (Exception ex) {
						System.out.println("Exception: " + ex);
					}
				} else {
					setUserCommand(Keys.DISCONNECT);
				}
			}
		});
	}
	
	private Listener cancelListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				connectShell.close();
			}
		});
	}
	
	public abstract void setUserCommand(int userCommand);
	public abstract void connected(Boolean flag);
	public abstract void setConnectButtonText(); 
}
