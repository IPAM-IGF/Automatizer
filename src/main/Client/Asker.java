package main.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import display.panels.Case;
import display.panels.GlandPanel;

import tools.server.CustomObjectInputStream;
import tools.server.ServerResponse;


/**
 * Enverra les requetes au serveur
 * 
 * @author jérémy DEVERDUN
 *
 */
public class Asker extends Thread{
	
	// Infos sur le serveur
	private static final int port = 4444;
	private static final String ip = "127.0.0.1";


	// Infos à mettre à jours
	private JProgressBar statusArea;
	
	// Attributs
	private Socket kkSocket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private int BUFFER_SIZE = 65536;
	private Worker worker;
	
	private Client clientWindow;
	
	public Asker(JProgressBar l, Client c){
		super();
		setStatusArea(l);
		setClientWindow(c);
		l.setStringPainted(true);
	}
	public void run(){
		ServerResponse obj;
		if(statusArea != null) statusArea.setString("Attempting to connect");
		try {
            kkSocket = new Socket(ip, port);
            if(statusArea != null) statusArea.setString("Connected");
            in = new CustomObjectInputStream((kkSocket.getInputStream()));
			out = new ObjectOutputStream(kkSocket.getOutputStream());
			if(statusArea != null) statusArea.setString("Sending request");
			out.writeObject(worker.clone());
			if(statusArea != null) statusArea.setString("Retrieving informations");
			obj = (ServerResponse)in.readObject();
			processObject(obj);
			if(statusArea != null) statusArea.setString("Done");
			
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: "+ip+".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
				kkSocket.getOutputStream().flush();
				kkSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void processObject(ServerResponse obj) {
		switch(obj.getRequest()){
		case Worker.GET_GLAND_PANEL:
			clientWindow.setGlandPanel((GlandPanel) obj.getReturnObject());break;
		case Worker.UPDATED_CASES:
			clientWindow.getGlandPanel().setListCases((HashMap<String, Case>) obj.getReturnObject());break;
		}
	}
	
	public void doAsk(String request){
		worker = new Worker();
		worker.setRequest(request);
		this.start();
	}
	public JProgressBar getStatusArea() {
		return statusArea;
	}


	public void setStatusArea(JProgressBar statusArea) {
		this.statusArea = statusArea;
	}
	public int getBUFFER_SIZE() {
		return BUFFER_SIZE;
	}
	public void setBUFFER_SIZE(int bUFFER_SIZE) {
		BUFFER_SIZE = bUFFER_SIZE;
	}
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public Client getClientWindow() {
		return clientWindow;
	}
	public void setClientWindow(Client clientWindow) {
		this.clientWindow = clientWindow;
	}
}
