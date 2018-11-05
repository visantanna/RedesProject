package Utils;

public class Message  extends Sendable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3812833901357989296L;
	
	private String messageType;
	private String content;
	private ConnectionParams destination;
	private ConnectionParams source;
	
	public Message (String messageType){
		this.messageType = messageType;
	}
	
	public Message(MessageTypes type) {
		this.setTypeMessage(type.name());
	}

	public String getMessageType() {
		return messageType;
	}
	public void setTypeMessage(String messageType) {
		this.messageType = messageType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ConnectionParams getDestination() {
		return destination;
	}
	public void setDestination(ConnectionParams Destination) {
		destination = Destination;
	}
	public ConnectionParams getSource() {
		return source;
	}
	public void setSource(ConnectionParams Source) {
		source = Source;
	}
	
}
