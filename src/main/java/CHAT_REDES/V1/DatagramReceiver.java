package CHAT_REDES.V1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class DatagramReceiver implements Runnable{
	private DatagramSocket serverSocket = null;
	private int port = 8293;
	private BlockingQueue<Message> messageQueue;
	
	public DatagramReceiver(BlockingQueue<Message> Queue){
		try{
			serverSocket = new DatagramSocket(port);
			messageQueue = Queue;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run() {
		byte[] receiveData = new byte[1024];
		while(true){
			try {
				DatagramPacket datagramPacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(datagramPacket);
				Message message = new Message();
				String data = new String(datagramPacket.getData());
				//Getting order of the message
				message.setMessage(data);
				messageQueue.put(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}

