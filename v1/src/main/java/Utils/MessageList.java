package Utils;

import java.util.ArrayList;

public class MessageList extends Sendable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2741069138586231435L;
	
	private ArrayList<Message> messageList = new ArrayList<Message>(); 
	@Override
	public String getMessageType() {
		return MessageTypes.ListMessage.name();
	}
	public ArrayList<Message> getMessageList() {
		return messageList;
	}
	public void setMessageList(ArrayList<Message> messageList) {
		this.messageList = messageList;
	}

}
