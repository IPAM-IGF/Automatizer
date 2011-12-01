package tools.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class ServerResponse implements Cloneable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1263491097158374017L;
	private String request;
	private Object returnObject;

	public ServerResponse(){
		setReturnObject(null);
		request = "";
	}

	public void setRequest(String req) {
		this.request = req;
	}
	public String getRequest() {
		return request;
	}
	
	public Object clone(){
		  try{
			ServerResponse cloned = (ServerResponse)super.clone();
		  	cloned.request = (String)request;
		  	cloned.returnObject = (Object)returnObject;
		  	return cloned;
		  }
		  catch(CloneNotSupportedException e){
			  System.out.println(e);
		  	return null;
		  }
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
}
