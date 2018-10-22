package Utils;

import java.io.Serializable;

public abstract class Sendable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2529499522000221657L;

	public abstract String getMessageType();
}
