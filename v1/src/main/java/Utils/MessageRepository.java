package Utils;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class MessageRepository extends HashMap<String,ArrayBlockingQueue<Message>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2864304150517467775L;

	public void addMessageRepository(String destinationIP, Message message){
		ArrayBlockingQueue<Message> listOfMessages = this.get(destinationIP); 
		if(listOfMessages == null){
			listOfMessages = new ArrayBlockingQueue<Message>(15);
			this.put(destinationIP, listOfMessages);
		}
		listOfMessages.add(message);
	}
	public void addMessageRepository(String destinationIP){
		ArrayBlockingQueue<Message> listOfMessages = this.get(destinationIP);
		if(listOfMessages == null){
			listOfMessages = new ArrayBlockingQueue<Message>(15);
			this.put(destinationIP, listOfMessages);
		}
	}
}
