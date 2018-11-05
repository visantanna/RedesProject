package CHAT_REDES.V1;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import Utils.ConnectionParams;
import Utils.Message;
import Utils.MessageTypes;

public class ChatWindow  implements Runnable   {
	JFrame tela;
	JTextArea textWriteableArea;
	JTextArea textReadableArea;
	JScrollPane WrittableScrollPanel;
	JScrollPane ReadableScrollPanel;
	JButton sendButton;
	ConnectionParams address;
	Thread threadReceiver;
	SimpleAttributeSet specialKeyWord;
	SimpleAttributeSet normalKeyWord;
	JScrollBar verticalScrollReadeable;
	ArrayBlockingQueue<Message> receivedMessages;
	ArrayBlockingQueue<Message> messageQueue;
	String status = "open";
	int maxSizeOfText  = 0;
	boolean threadStarted =false;
	
	public ChatWindow(ConnectionParams address , ArrayBlockingQueue<Message> MessageQueue , ArrayBlockingQueue<Message> ReceivedMessages){
		this.address =	address;
		receivedMessages =ReceivedMessages;
		messageQueue = MessageQueue; 
		
		
		
	}

	public void run() {
		init();
		TCPReceiver receiver = new TCPReceiver(receivedMessages , this);
		threadReceiver = new Thread(receiver);
		if(threadStarted == false ){
			System.out.println("Nova tela de chat?");
			threadReceiver.start();
			threadStarted = true;
		}
	}

	private void init() {
		tela = new JFrame();
		tela.setTitle("Conversa com " + address.toString());
		tela.getContentPane().setLayout(null);
		tela.setSize(400, 300 );
		textWriteableArea = new JTextArea(5, 40);
		textReadableArea = new JTextArea();
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
		sendButton.setBounds(300, 210 , 70 , 20);
		verticalScrollReadeable = ReadableScrollPanel.getVerticalScrollBar();

		specialKeyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(specialKeyWord, new Color(35, 76, 142));
		StyleConstants.setBold(specialKeyWord, true);
		
		normalKeyWord = new SimpleAttributeSet();
		StyleConstants.setAlignment(normalKeyWord,StyleConstants.ALIGN_LEFT );
		
		tela.getContentPane().add(WrittableScrollPanel);
		tela.getContentPane().add(ReadableScrollPanel);
		tela.getContentPane().add(sendButton);
		tela.setVisible(true);
		sendButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(textWriteableArea.getText() != null)
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
				textWriteableArea.setText(textWriteableArea.getText().replace("\n" , ""));
			}
			
		});
		tela.setVisible(true);
		tela.addWindowListener(new WindowListener() {
			
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
				threadReceiver.interrupt();
				ChatWindow.this.status = "closed";
				
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
		
		
	}
	public synchronized int messageRecieved(String message){
		
		
		textReadableArea.append( address.getIp() + ": "+ message + "\n") ;
		verticalScrollReadeable.setValue(verticalScrollReadeable.getMaximum());
		return textReadableArea.getText().length();
	}
	public void sendMessage(String content){
		try {
			Message mensagem = new Message(MessageTypes.SendMessage);
			mensagem.setContent(content);
			mensagem.setDestination(address);
			messageQueue.put(mensagem);
			System.out.println("Mensagem colocada na Fila " + content);
			textWriteableArea.setText("");
			
			textReadableArea.append("localhost: " +content + "\n");
			verticalScrollReadeable.setValue(verticalScrollReadeable.getMaximum());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatWindow other = (ChatWindow) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		}else{
			if(address.getIp() != other.address.getIp()){
				return false;
			}else if(address.getPort() != other.address.getPort()){
				return false;
			}
		}
		return true;
	}

	
}
