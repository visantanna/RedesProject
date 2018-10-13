package CHAT_REDES.V1;

import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	BlockingQueue<Message> MessageQueue = new ArrayBlockingQueue<Message>(10);
    	boolean wrongIpFormat = true;
    	String ip = "";
    	while(wrongIpFormat){
	    	Pattern pattern = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("A que ip você quer se conectar?");
	    	ip = sc.nextLine();
	    	wrongIpFormat = !pattern.matcher(ip).matches();
	    	if(wrongIpFormat){
	    		System.out.println("O formato do IP está errado, tente novamente. O ip deve ser do formato Ex: 192.128.199.10");
	    	}
    	}
    	InetAddress IpAddress;
		try {
			IpAddress = InetAddress.getByName(ip);
			DatagramReceiver receiver = new DatagramReceiver(MessageQueue); 
	    	DatagramSender sender = new DatagramSender(IpAddress, MessageQueue);
	    	
	    	new Thread(receiver).start();
	    	new Thread(sender).start();
	    	
	    	while(true){
	    		Message message = MessageQueue.take();
	    		System.out.println(message);
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
}
