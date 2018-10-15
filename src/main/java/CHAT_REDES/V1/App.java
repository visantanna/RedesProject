package CHAT_REDES.V1;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
    	int port = 8923;
    	new Thread (new ConnectionsReceiver(port)).start();;
    	boolean wrongIpFormat = true;
    	String ip = "";
    	while(true){
	    	Pattern pattern = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("A que ip você quer se conectar?");
	    	ip = sc.nextLine();
	    	wrongIpFormat = !pattern.matcher(ip).matches();
	    	if(wrongIpFormat){
	    		System.out.println("O formato do IP está errado, tente novamente. O ip deve ser do formato Ex: 192.128.199.10");
	    	}else{
	    		System.out.println("Tentando conectar com o ip: "+ ip);
	        	try {
	    			Socket clientSocket = new Socket(ip , port);
	    			new Thread(new ChatWindow(clientSocket)).start();
	    			System.out.println("Conectado com sucesso !");
	    			System.out.println("Deseja se conectar com algum outro chat?");
	    		} catch (UnknownHostException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}

	    	}
    	}
    	    	
    }
}
