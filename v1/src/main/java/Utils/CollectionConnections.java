package Utils;

import java.util.ArrayList;

public class CollectionConnections extends Sendable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5207674274871127112L;
	
	private ArrayList<ConnectionParams> listofConnections = new ArrayList<ConnectionParams>();
	@Override
	public String getMessageType() {
		return "connections";
	}
	public ArrayList<ConnectionParams> getListofConnections() {
		return listofConnections;
	}
	public void setListofConnections(ArrayList<ConnectionParams> list){
		listofConnections = list;
	}
}
