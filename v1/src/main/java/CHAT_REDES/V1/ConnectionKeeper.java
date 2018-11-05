package CHAT_REDES.V1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import Utils.CollectionConnections;
import Utils.ConnectionParams;
import Utils.Message;
import Utils.MessageList;
import Utils.MessageRepository;

public class ConnectionKeeper implements Runnable{
	
	ServerSocket connectionMaker;
	int serverPort = 50213;
	String serverAddress = "52.33.208.132";
	CopyOnWriteArrayList<ConnectionParams> listOfConnectedIps;
	ArrayBlockingQueue<Message> messageQueue = null;
	private SelectChatWindow  chatOpenner;
	private MessageRepository messageRepository;
	
	public ConnectionKeeper(CopyOnWriteArrayList<ConnectionParams> listOfConnectedIps , SelectChatWindow chatOpenner,
			ArrayBlockingQueue<Message> messageQueue , MessageRepository messageRepository){
		this.listOfConnectedIps = listOfConnectedIps;
		this.messageQueue =  messageQueue;
		this.messageRepository = messageRepository;
		this.chatOpenner = chatOpenner;
		checkConfig();
	}

	private void checkConfig() {
		File configDir = new File("config.txt");
		if(configDir.exists()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(configDir));
				String newIP = reader.readLine();
				String newPort = reader.readLine();
				serverAddress = newIP.split("=")[1];
				serverPort    = new Integer(newPort.split("=")[1]);
				System.out.println("serverAddress: " +  serverAddress );
				System.out.println("port: " + serverPort);
			} catch (IOException e) {
				System.out.println("O formato do arquivo é inválido");
				e.printStackTrace();
				serverPort = 50213;
				serverAddress = "52.33.208.132";
			} 
		}
		
	}

	public void run() {
		while(true){
			Socket connectionToServer = null;
			try {
				connectionToServer = new Socket(serverAddress , serverPort);
			} catch (UnknownHostException e) {
				System.out.println("Host não respondendo");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				ObjectOutputStream sendOutput = new ObjectOutputStream(
						connectionToServer.getOutputStream()
						);
				ObjectInputStream returnFromServer = new ObjectInputStream(connectionToServer.getInputStream());
				while(true){
					Message message = messageQueue.take();
					handleMessages(returnFromServer , sendOutput , message);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					connectionToServer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			
		}
		
	}

	private void handleMessages(ObjectInputStream returnFromServer, ObjectOutputStream sendOutput , Message message) throws IOException, ClassNotFoundException {
		switch(message.getMessageType()){
			case "GetIP":
				sendOutput.writeObject(message);
				CollectionConnections connections = (CollectionConnections) returnFromServer.readObject();
				ArrayList<ConnectionParams> IpList = connections.getListofConnections();
				listOfConnectedIps.removeIf(element -> !IpList.contains(element));
				listOfConnectedIps.addAllAbsent(IpList);
				break;
			case "SendMessage":
				sendOutput.writeObject(message);
				break;
			case "getMessages":
				sendOutput.writeObject(message);
				MessageList list = (MessageList) returnFromServer.readObject();
				list.getMessageList().forEach(messageOfList -> messageRepository.addMessageRepository(messageOfList.getSource(), messageOfList));
				list.getMessageList().forEach(messageOfList -> chatOpenner.createNewChat(messageOfList.getSource()));
				break;
		}
	}
	
}
