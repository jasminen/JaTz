package view.gamesMaze2048;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
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
import common.SLhelper;

/**
 * Abstract Connect To Server class implements Listener
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public abstract class AbsConnectToServer implements Listener {
	private Button connect;
	private Combo ipBox;
	private Shell connectShell;
	private Button disconnect;
	private Label error;
	protected InetSocketAddress socketAddress;
	
	/**
	 * Constructor needs shell to be draw on.
	 * @param connectShell 
	 */
	public AbsConnectToServer(Shell connectShell) {

		this.connectShell = connectShell;
	}

	/**
	 * Draw new shell that give the option to connect to a server. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(Event e) {
		connectShell.setLayout(new GridLayout(3, false));
		connectShell.setSize(300, 150);
		connectShell.setLocation(600, 500);
		
		connectShell.addListener(SWT.Close, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				e.doit = false;
				connectShell.setVisible(false);
			}
		});
		
		Label title = new Label(connectShell, SWT.NONE);
		title.setText("Connect to the relevant IP, default port is: 9000");
		title.setFont(new Font(Display.getDefault(), "Arial", 10, SWT.ITALIC));
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));

		ipBox = new Combo(connectShell, SWT.DROP_DOWN);
		ipBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));	
		
		//Dynamic load of selections
		ArrayList<String> selections;
		try {
			selections = (ArrayList<String>) SLhelper.load("conf/serverIPs.xml");
			String[] stringSelections = new String[selections.size()];
			selections.toArray(stringSelections);
			ipBox.setItems(stringSelections);
		} catch (Exception e2) {
			e2.printStackTrace();
			System.out.println("File not exist");
		}
		ipBox.setText("localhost");
		
		connect = new Button(connectShell, SWT.PUSH);
		connect.setText("Connect");
		connect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		connect.addListener(SWT.Selection, connectListener());
		
		disconnect = new Button(connectShell, SWT.PUSH);
		disconnect.setText("Disconnect");
		disconnect.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		disconnect.addListener(SWT.Selection, disconnectListener());
		
		error = new Label(connectShell, SWT.NONE);
		error.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		error.setForeground(new Color(null, 255, 0, 0));
		
		connectShell.setText("Connect to solver server");
		connectShell.open();
		
	}

	/**
	 * Connect to the server and notify the observer with the socket address (IP and Port)
	 * @return Connect Listener 
	 */
	private Listener connectListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				try {
					error.setText("");
					socketAddress = new InetSocketAddress(InetAddress.getByName(ipBox.getText()), 9000);
					setUserCommand(Keys.CONNECT);
				} catch (Exception ex) {
					error.setText("No such hostname!");
				}
			}
		});
	}
	
	/**
	 * Disconnect from the server.
	 * @return Disconnect Listener 
	 */
	private Listener disconnectListener() {
		return (new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				setUserCommand(Keys.DISCONNECT);
				connectShell.setVisible(false);
			}
		});
	}
	
	public abstract void setUserCommand(int userCommand);
}
