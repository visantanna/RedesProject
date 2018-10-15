package CHAT_REDES.V1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class TCPSender implements Runnable{
	
	private BlockingQueue<String> MessageQueue = null;
	private Socket connection;
	
	public TCPSender(Socket connection , BlockingQueue<String> Queue){
		MessageQueue = Queue;
		this.connection = connection;
	}

	public void run() {
		while(true){
			try {
				String message = MessageQueue.take();
				DataOutputStream sendOutput = new DataOutputStream(
						connection.getOutputStream()
						);
				sendOutput.writeBytes(message);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
}
