package tools.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import main.Client.Worker;

import display.GlandZoneSelector;

public class AutomatizerServer extends Thread{
	
	// Paramètre du serveur
	public static final int port = 4443;
	
	// Attributs
	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	ObjectOutputStream out = null;
	CustomObjectInputStream in = null;
	
	public AutomatizerServer(){
		serverSocket = null;
		clientSocket = null;
		out = null;
		in = null;
	}
	public void run(){
		// On écoute le port 
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+port+".");
            System.exit(1);
        }
        
        // On attend la connexion d'un client
        System.out.println("listenning");
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        
        // On récupère l'objet
        try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new CustomObjectInputStream(clientSocket.getInputStream());
			Object obj = in.readObject();
			// On traite la requête
			ServerResponse resp = processInput(obj);
			// On renvoi le résultat
			out.writeObject(resp);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			close();
		}finally{
			close();
		}
	}


	private void close() {
		try {
			serverSocket.close();
			clientSocket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private ServerResponse processInput(Object obj) {
		ServerResponse resp = null;
		if(obj instanceof Worker){
			Worker w = (Worker)obj;
			resp = new ServerResponse();
			resp.setRequest(w.getRequest());
			switch(w.getRequest()){
			case Worker.GET_GLAND_PANEL:
				resp.setReturnObject(GlandZoneSelector.currentPanel);break;
			case Worker.UPDATED_CASES:
				resp.setReturnObject(GlandZoneSelector.currentPanel.getListCases());
				break;
			default: return null;
			}
		}
		return resp;
	}
}
