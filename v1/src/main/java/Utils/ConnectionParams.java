package Utils;

import java.io.Serializable;

public class ConnectionParams implements Serializable{
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionParams other = (ConnectionParams) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		}else{
			if(ip != other.ip){
				return false;
			}
		}
		return true;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1620349237713837924L;
	private int port;
	private String ip;
	public ConnectionParams(String ip ,int port){
		this.setPort(port);
		this.setIp(ip);
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
