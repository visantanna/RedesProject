package CHAT_REDES.V1;

import java.util.concurrent.ArrayBlockingQueue;

import Utils.Message;

public class TCPReceiver implements Runnable{
	int tamMaxTela = 0;	
	ArrayBlockingQueue<Message> receivedMessages =null ; 
	ChatWindow chat = null;
	public TCPReceiver( ArrayBlockingQueue<Message> receivedMessages , ChatWindow chat){
		this.receivedMessages = receivedMessages;
		this.chat = chat;
	}	
    public void run() {
    	try {
    		while(true){
    			Message message = receivedMessages.take();
       			if(message.getContent() != null && message.getContent().length() > 0){
       				int newSize = 0; 
       		        while (newSize <= tamMaxTela){
       		        	System.out.println("Nome da thread: "+ Thread.currentThread().getName());
       		        	newSize = chat.messageRecieved(message.getContent());
       					if(newSize < tamMaxTela){
       						Thread.sleep(100);
       					}
       				}
       		        tamMaxTela = newSize;
       			}       
       		}
       	} catch (InterruptedException e) {
       		e.printStackTrace();
       		try {
       			this.finalize();
       		} catch (Throwable e1) {
       			e1.printStackTrace();
       		}
       }
	}
}

