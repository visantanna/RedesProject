package CHAT_REDES.V1;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionsReceiver implements Runnable {
	ServerSocket connectionOpenner;
	int port;
	public ConnectionsReceiver(int port){
		this.port = port; 
		try {
			connectionOpenner = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void run() {
		while(true){
			try {
				new Thread(new ChatWindow(connectionOpenner.accept())).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
