package CHAT_REDES.V1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatWindow  implements Runnable   {
	JFrame tela;
	JTextArea textWriteableArea;
	JTextArea textReadableArea;
	JScrollPane WrittableScrollPanel;
	JScrollPane ReadableScrollPanel;
	JButton sendButton;
	Socket connection;
	TCPReceiver tcpReceiver;
	TCPSender   tcpSender;
	String ipAddress;
	Thread threadReceiver;
	Thread threadSender;
	ArrayBlockingQueue<String> Queue;
	
	
	public ChatWindow(Socket connection){
		tela = new JFrame();
		ipAddress =	connection.getInetAddress().getHostAddress();
		tela.setTitle("Conversa com IP " + ipAddress);
		tela.getContentPane().setLayout(null);
		tela.setSize(400, 300 );
		textWriteableArea = new JTextArea(5, 40);
		textReadableArea = new JTextArea(10 ,60);
		WrittableScrollPanel = new JScrollPane(textWriteableArea);
		ReadableScrollPanel = new JScrollPane(textReadableArea);
		ReadableScrollPanel.setBounds( 30 , 11 , 320 , 150);
		WrittableScrollPanel.setBounds( 30 , 180 , 260 , 70  );
		WrittableScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ReadableScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		WrittableScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ReadableScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textReadableArea.setEditable(false);
		sendButton = new JButton();
		sendButton.setText("Envio");
		sendButton.setBounds(310, 210 , 60 , 20);
		
		tela.getContentPane().add(WrittableScrollPanel);
		tela.getContentPane().add(ReadableScrollPanel);
		tela.getContentPane().add(sendButton);
		this.connection = connection;
		try {
			Queue = new ArrayBlockingQueue<String>(10);
			
			threadReceiver = new Thread(tcpReceiver = new TCPReceiver(connection , this));
			threadReceiver.start();
			
			threadSender = new Thread(tcpSender = new TCPSender(connection, (BlockingQueue<String>)Queue));
			threadSender.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
	}

	public void run() {
		init();
		
	}

	private void init() {
		sendButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				sendMessage(textWriteableArea.getText());
			}
			
		});
		textWriteableArea.addKeyListener(new KeyListener(){

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_ENTER){
					sendMessage(textWriteableArea.getText());
				}
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}
	public void messageRecieved(String message){
		this.textReadableArea.append(ipAddress + ": " + message + "\n");
	}
	public void sendMessage(String message){
		try {
			Queue.put(message);
			textWriteableArea.setText("");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
