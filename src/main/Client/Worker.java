package main.Client;

import java.io.Serializable;
import java.util.ArrayList;

public class Worker  implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6441644225708240188L;
	
	// Commandes possibles
	public static final String UPDATED_CASES = "update_case";
	public static final String GET_GLAND_PANEL = "get_gland_panel";
	
	
	private byte[] buffer = new byte[65536];
	
	
	private String request="";
	
	public Worker(){
		
	}
	public Object clone(){
		  try{
			Worker cloned = (Worker)super.clone();
			cloned.request = (String) request;
		  	return cloned;
		  }
		  catch(CloneNotSupportedException e){
			  System.out.println(e);
		  	return null;
		  }
	}
	
	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}
	public byte[] getBuffer() {
		return buffer;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
}
