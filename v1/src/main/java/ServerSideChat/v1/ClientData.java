package ServerSideChat.v1;

import Utils.ConnectionParams;

public class ClientData {
	private String ip;
	private int port;
	private long remaningTimeToUpdate;
	
	
	public ClientData(String ip, int port ) {
		super();
		this.ip = ip;
		this.port = port;
		this.remaningTimeToUpdate = 10000;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public long getRemaningTimeToUpdate() {
		return remaningTimeToUpdate;
	}
	public void setRemaningTimeToUpdate(long remaningTimeToUpdate) {
		this.remaningTimeToUpdate = remaningTimeToUpdate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientData other = (ClientData) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	public ConnectionParams getConnectionParam(){
		return new ConnectionParams(ip , port);
	}
}
