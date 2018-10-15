package CHAT_REDES.V1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class TCPReceiver implements Runnable{
	Socket socket;
	ChatWindow chat;
	
	public TCPReceiver(Socket socket , ChatWindow chat ){
		this.socket=socket;
		this.chat = chat;
	}

	public void run() {
		while(true){
			try {
				BufferedReader returnFromConnection  = new BufferedReader( 
						new InputStreamReader(
							socket.getInputStream()
						)
					   );
				String message = returnFromConnection.readLine();
				System.out.println("Recebido com sucesso!");
				chat.messageRecieved(message);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}

