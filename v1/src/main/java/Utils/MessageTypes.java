package Utils;

public enum MessageTypes{
	SendMessage("SendMessage"),
	GetIP("GetIP"),
	getMessages("getMessages"),
	ListMessage("ListMessage");
	
	private final String name;
	
	private MessageTypes(String n){
		name = n;
	}
	public String toString(){
		return name;
	}
}