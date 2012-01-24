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
	public static final String GIVE_USER_CONTROL = "give_user_control";
	public static final String STOP_USER_CONTROL = "stop_user_control";
	public static final String NO_REQUEST = "no_request";
	public static final String MOUSE_PRESSED = "mouse_pressed";
	public static final String MOUSE_RELEASED = "mouse_released";
	public static final String MOUSE_CLICKED = "mouse_clicked";
	public static final String KEY_EVENT = "0-9 key typed";
	
	
	private byte[] buffer = new byte[65536];
	private Object sendObject;
	
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
	public Object getSendObject() {
		return sendObject;
	}

	public void setSendObject(Object sObject) {
		this.sendObject = sObject;
	}
}
