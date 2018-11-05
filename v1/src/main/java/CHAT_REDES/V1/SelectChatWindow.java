package CHAT_REDES.V1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Utils.ConnectionParams;
import Utils.Message;
import Utils.MessageRepository;

public class SelectChatWindow extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<ConnectionParams> list;
    private DefaultListModel<ConnectionParams> listModel;
    private JButton connectButton;
    private JScrollPane scroll;
    private JFrame frame;
    private ArrayBlockingQueue<Message>  messageToServer;
    private MessageRepository messageRepository;
    private ArrayList<ChatWindow> chatList=  new ArrayList<ChatWindow>();
    private String status = "";
    
	CopyOnWriteArrayList<ConnectionParams> listOfConnectedIps = null;
	
	public SelectChatWindow(CopyOnWriteArrayList<ConnectionParams> listOfConnectedIps, ArrayBlockingQueue<Message>  messageToServer,MessageRepository messageRepository){
		this.listOfConnectedIps = listOfConnectedIps;
		this.messageToServer = messageToServer;
		this.messageRepository = messageRepository;
	}
	public void init(){
		
		frame = new JFrame("Lista de Ips conectados");
		frame.setLayout(null);
		frame.setSize(340 , 200);
		listModel = new DefaultListModel<ConnectionParams>();
		connectButton = new JButton();
		connectButton.setText("Conectar");
		listOfConnectedIps.forEach(connection -> listModel.addElement(connection));
		list = new JList<ConnectionParams>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scroll.setBounds(10 ,10, 305 , 115 );
		connectButton.setBounds(120, 130 , 100, 25);
		
		frame.add(scroll);
		frame.add(connectButton);
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				SelectChatWindow.this.status = "closed";
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener (){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

		            if (list.getSelectedIndex() == -1) {
		            //No selection, disable fire button.
		            	connectButton.setEnabled(false);

		            } else {
		            //Selection, enable the fire button.
		            	connectButton.setEnabled(true);
		            }
				}
			}
		});
		
		connectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
				
			}
		});
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					updateList(listOfConnectedIps);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		frame.setVisible(true);;
		
	}
	protected void connect() {
		ConnectionParams ip = list.getSelectedValue();
		createNewChat(ip);
		
		
	}
	public void updateList(List<ConnectionParams> newListConnection){
		ArrayList<ConnectionParams> oldList = new ArrayList<ConnectionParams>();
		if(listModel.toArray().length > 0){
			Object[] listIPs = listModel.toArray(); 
			for(int i = 0 ; i <listIPs.length ; i ++ ){
				if(listIPs[i] instanceof ConnectionParams ){
					oldList.add((ConnectionParams)listIPs[i]);
				}
			}
			
		}
		ArrayList<ConnectionParams> newList = new ArrayList<ConnectionParams>();
		for(ConnectionParams connection : newListConnection){
			if(!oldList.contains(connection)){
				listModel.addElement(connection);
			}
			newList.add(connection);
		}
		for(ConnectionParams connection : oldList){
			if(!newList.contains(connection)){
				listModel.removeElement(connection);
			}
		}
	}
	@Override
	public void run() {
		init();
	}
	public void createNewChat(ConnectionParams address){
		messageRepository.addMessageRepository(address);
		ArrayBlockingQueue<Message> messageFromServer = messageRepository.get(address);
		chatList.removeIf(chat -> chat.status == "closed");
		ChatWindow newChatWindow = new ChatWindow(address, messageToServer ,  messageFromServer);
		if(!chatList.contains(newChatWindow)){
			chatList.add(newChatWindow);
			new Thread(newChatWindow).start();
		}
		
	}
	
	
	public String getStatus() {
		return status;
	}
	
	
}
