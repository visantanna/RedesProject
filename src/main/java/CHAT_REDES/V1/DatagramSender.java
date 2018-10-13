package CHAT_REDES.V1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class DatagramSender implements Runnable{
	
	private BlockingQueue<Message> MessageQueue = null;
	private InetAddress ip;
	private int port = 8293;
	
	public DatagramSender(InetAddress ip , BlockingQueue<Message> Queue){
		this.ip = ip;
		MessageQueue = Queue;
	}

	public void run() {
		byte[] sendData = new byte[1024];
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length , ip , port );
		try {
			DatagramSocket senderSocket = new DatagramSocket(port);
			Scanner sc = new Scanner(System.in);
			while(true){
				String message = sc.nextLine();
				byte[] byteMessage = message.getBytes();
				int maxSize= 1024;
				int numberOfPackages = (int)Math.ceil(byteMessage.length/maxSize);
				int lastByteSend = 0;
				byte[] messagePackage = null;
				if( byteMessage.length >= maxSize  ){
					for(int PackageNumber =  0;  PackageNumber < numberOfPackages+1; PackageNumber ++ ){
						
						if(PackageNumber == numberOfPackages ){
							messagePackage = Arrays.copyOfRange(byteMessage, lastByteSend+1, byteMessage.length);
						}else{
							messagePackage = Arrays.copyOfRange(byteMessage, lastByteSend, PackageNumber+1*1024);
							lastByteSend = PackageNumber+1*1024;
						}
						sendPacket.setData(messagePackage);
						senderSocket.send(sendPacket);
					}
				}
				Message message_send = new Message();
				message_send.setMessage(message);
				MessageQueue.put(message_send);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
