package CHAT_REDES.V1;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import Utils.Message;


public class TCPReceiver implements Runnable{
	Socket socket;
	ChatWindow chat;
	ArrayBlockingQueue<Message> messageQueue;
	
	public TCPReceiver(ArrayBlockingQueue<Message> MessageQueue , ChatWindow chat ){
		this.chat = chat;
		messageQueue = MessageQueue; 
	}

	public void run() {
		while(true){
			try {
				Message message = messageQueue.take();
				chat.messageRecieved(message.getContent());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

