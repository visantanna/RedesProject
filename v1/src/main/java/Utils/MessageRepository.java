package Utils;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class MessageRepository extends HashMap<ConnectionParams,ArrayBlockingQueue<Message>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2864304150517467775L;

	public void addMessageRepository(ConnectionParams destination, Message message){
		ArrayBlockingQueue<Message> listOfMessages = this.get(destination); 
		System.out.println("já ta cadastrado? "+ this.containsKey(destination));
		if(listOfMessages == null){
			listOfMessages = new ArrayBlockingQueue<Message>(15);
			this.put(destination, listOfMessages);
		}
		System.out.println("mensagem: "+ message + " adicionada para a conexão: "+ destination );
		listOfMessages.add(message);
		System.out.println("mensagem incluida com sucesso:" + listOfMessages.contains(message));
	}
	public void addMessageRepository(ConnectionParams destination){
		ArrayBlockingQueue<Message> listOfMessages = this.get(destination);
		if(listOfMessages == null){
			listOfMessages = new ArrayBlockingQueue<Message>(15);
			this.put(destination, listOfMessages);
		}
	}
}
