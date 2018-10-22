package Utils;



public class Message  extends Sendable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3812833901357989296L;
	
	private String messageType;
	private String content;
	private String IpDestination;
	private String IpSource;
	
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
	public String getIpDestination() {
		return IpDestination;
	}
	public void setIpDestination(String ipDestination) {
		IpDestination = ipDestination;
	}
	public String getIpSource() {
		return IpSource;
	}
	public void setIpSource(String ipSource) {
		IpSource = ipSource;
	}
	
}
