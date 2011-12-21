package tools.server;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import control.Controller;

import tools.Scripts;

import main.Client.Worker;

import display.GlandZoneSelector;

public class AutomatizerServer extends Thread{
	
	// Paramètre du serveur
	public static final int port = 4444;
	
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
		} catch (AWTException e) {
			e.printStackTrace();
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
	private ServerResponse processInput(Object obj) throws AWTException, HeadlessException, IOException {
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
			case Worker.STOP_USER_CONTROL:
				Scripts.REQUIRES_USER = false;
				resp.setReturnObject(null);
				resp.setRequest(Worker.NO_REQUEST);
				break;
			case Worker.MOUSE_PRESSED:
				double[] coord = (double[]) w.getSendObject();
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				System.out.println("press at "+dim.width+"--"+coord[0]+"--"+(int)Math.round(dim.width*coord[0])+"--"+(int)Math.round(dim.height * coord[1]));
				Scripts.CONTROLLER.getBot().mouseMove((int)Math.round(dim.width*coord[0]), (int)Math.round(dim.height * coord[1]));
				Scripts.CONTROLLER.getBot().delay(Controller.DELAY_PRESS_CLICK);
				Scripts.CONTROLLER.getBot().mousePress(InputEvent.BUTTON1_MASK);
				break;
			case Worker.MOUSE_RELEASED:
				double[] coordr = (double[]) w.getSendObject();
				Dimension dimr = Toolkit.getDefaultToolkit().getScreenSize();
				System.out.println("released at "+dimr.width+"--"+coordr[0]+"--"+(int)Math.round(dimr.width*coordr[0])+"--"+(int)Math.round(dimr.height * coordr[1]));
				Scripts.CONTROLLER.getBot().mouseMove((int)Math.round(dimr.width*coordr[0]), (int)Math.round(dimr.height * coordr[1]));
				Scripts.CONTROLLER.getBot().delay(Controller.DELAY_PRESS_CLICK);
				Scripts.CONTROLLER.getBot().mouseRelease(InputEvent.BUTTON1_MASK);
				break;
			case Worker.MOUSE_CLICKED:
				double[] coordc = (double[]) w.getSendObject();
				Dimension dimc = Toolkit.getDefaultToolkit().getScreenSize();
				System.out.println("clicked at "+dimc.width+"--"+coordc[0]+"--"+(int)Math.round(dimc.width*coordc[0])+"--"+(int)Math.round(dimc.height * coordc[1]));
				Scripts.CONTROLLER.getBot().mouseMove((int)Math.round(dimc.width*coordc[0]), (int)Math.round(dimc.height * coordc[1]));
				Scripts.CONTROLLER.getBot().delay(Controller.DELAY_PRESS_CLICK);
				Scripts.CONTROLLER.getBot().mousePress(InputEvent.BUTTON1_MASK);
				Scripts.CONTROLLER.getBot().delay(Controller.DELAY_PRESS_CLICK);
				Scripts.CONTROLLER.getBot().mouseRelease(InputEvent.BUTTON1_MASK);
				break;
			case Worker.GIVE_USER_CONTROL:
				Scripts.REQUIRES_USER = true;break;
			default: return null;
			}
			if(Scripts.REQUIRES_USER){
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( Scripts.CONTROLLER.getBot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())), "jpg", baos );
				byte[] imageInByte = baos.toByteArray();
				baos.flush();
				baos.close();
				resp.setReturnObject(imageInByte);
				resp.setRequest(Worker.GIVE_USER_CONTROL);
			}
		}
		return resp;
	}
}
