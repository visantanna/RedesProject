package Utils;

import java.io.Serializable;

public class ConnectionParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1620349237713837924L;
	private int port;
	private String ip;
	
	
	public String toString(){
		return "Ip:  "+ ip +  " porta: "+ port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		return result;
	}

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
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}


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
