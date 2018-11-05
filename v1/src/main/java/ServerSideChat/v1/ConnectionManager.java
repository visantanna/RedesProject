package ServerSideChat.v1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import Utils.CollectionConnections;
import Utils.ConnectionParams;
import Utils.Message;
import Utils.MessageList;
import Utils.MessageRepository;
import Utils.Sendable;

public class ConnectionManager implements Runnable {
	Socket connection;
	ClientList clientList;
	MessageRepository messageManager;
	public ConnectionManager(Socket connection ,ClientList clientList , MessageRepository messageManager){
		this.connection = connection;
		this.clientList = clientList;
		this.messageManager = messageManager;
	}
	public void run() {
		ClientData client = new ClientData(connection.getInetAddress().getHostName() 
				, connection.getPort());
		System.out.println("Recebendo conexão do ip "+ client.getIp() + " atualizando tempo restante até proxima verificação para : "+ client.getRemaningTimeToUpdate());
		clientList.keepAlive(client);
		messageManager.addMessageRepository(client.getConnectionParam());
		try {
			System.out.println("Lendo input da conexão...");
			ObjectOutputStream sendOutput = new ObjectOutputStream(connection.getOutputStream());
			System.out.println("sendOutput criado.");
			ObjectInputStream returnFromConnection  = new ObjectInputStream(connection.getInputStream());
			System.out.println("return from client criado.");
			while(true){
				handleMassages(returnFromConnection ,sendOutput , client );
			}
			
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void handleMassages( ObjectInputStream returnFromConnection ,ObjectOutputStream sendOutput , ClientData client) throws IOException {
		Sendable messageReceived = null;
		try {
			messageReceived = (Sendable) returnFromConnection.readObject();
			clientList.keepAlive(client);
		} catch (ClassNotFoundException e) {
			System.out.println("Mensagem recebida não esta no formato correto");
			e.printStackTrace();
		}
		
		switch(messageReceived.getMessageType()){
		
		case "GetIP":
			ArrayList<ConnectionParams> listConnected = (ArrayList<ConnectionParams>) clientList.getAllConnectedIps();
			CollectionConnections listOfConnections = new CollectionConnections();
			listOfConnections.setListofConnections(listConnected);
			sendOutput.writeObject(listOfConnections);
			break;
		case "SendMessage":
			Message formattedMessageSend = (Message) messageReceived;
			formattedMessageSend.setSource(client.getConnectionParam());
			messageManager.addMessageRepository(formattedMessageSend.getDestination() , formattedMessageSend);
			break;
		case "getMessages":
			Message formattedMessageGet = (Message) messageReceived;
			formattedMessageGet.setSource(client.getConnectionParam());
			messageManager.addMessageRepository(client.getConnectionParam());
			ArrayBlockingQueue<Message> listOfMessages = messageManager.get(formattedMessageGet.getSource());
			ArrayList<Message> listToBeSend = new ArrayList<Message>();
			if(listOfMessages != null){	
				listOfMessages.drainTo(listToBeSend);
			}
			MessageList returnedOutput = new MessageList();
			returnedOutput.setMessageList(listToBeSend);
			sendOutput.writeObject(returnedOutput);
			break;
		}
		
	}

}
