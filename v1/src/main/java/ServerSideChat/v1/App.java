package ServerSideChat.v1;

import java.io.IOException;
import java.net.ServerSocket;

import Utils.MessageRepository;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	int port = 50213;
    	ServerSocket listenner = null;
    	ClientList allClients = new ClientList();
    	MessageRepository messageManager = new MessageRepository();
    	new ClientListManager(allClients).start();
    	while(true){
    		try {
				listenner = new ServerSocket(port);
				System.out.println("Server escutando porta: " + port);
			} catch (IOException e1) {
				e1.printStackTrace();
				listenner.close();
			}
    		try {
    			new Thread(new ConnectionManager(listenner.accept(), allClients ,messageManager)).start();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
}
