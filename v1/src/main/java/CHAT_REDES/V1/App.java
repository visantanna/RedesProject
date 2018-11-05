package CHAT_REDES.V1;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;


import Utils.ConnectionParams;
import Utils.Message;
import Utils.MessageRepository;
import Utils.MessageTypes;


public class App 
{
    public static void main( String[] args )
    {
    	MessageRepository messageRepository = new MessageRepository();
    	ArrayBlockingQueue<Message> messagesToServer = new ArrayBlockingQueue<>(10);
    	CopyOnWriteArrayList<ConnectionParams> listOfConnectedIps = new CopyOnWriteArrayList<ConnectionParams>();
    	SelectChatWindow selectChat =new SelectChatWindow(listOfConnectedIps , messagesToServer, messageRepository);
    	new Thread(selectChat).start();
    	new Thread (new ConnectionKeeper(listOfConnectedIps ,selectChat , messagesToServer , messageRepository )).start();
    	
    	long updateTime = 1000;
    	
    	while(true){
    		try {
    			if(selectChat.getStatus() == "closed"){
    				System.exit(0);
    			}
	    		Message mensagemAtualizaIp = new Message(MessageTypes.GetIP);
				messagesToServer.put(mensagemAtualizaIp);
    			Message getMessages = new Message(MessageTypes.getMessages);
    			messagesToServer.put(getMessages);
				Thread.sleep(updateTime);		
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
    	    	
    }
}
