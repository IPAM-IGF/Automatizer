package display;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import control.Controller;


public class TrayPopupMenu extends PopupMenu {

	private final TrayIcon trayIcon;
	
	private URL imageLoc;
	private MenuItem exitItem, setupKeyItem;
	private Menu setupItem;
	private Controller cont;
	
	public TrayPopupMenu(URL image, Controller cont, boolean isServer){
		super();
		imageLoc=image;
		this.cont=cont;
		// remplacer logo.png par image quand on exporte en jar
		Image imm = (new ImageIcon(image)).getImage();
		trayIcon =
                new TrayIcon(imm, "Tray Icon", this);//createImage(imageLoc, "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
       
        if(isServer){
	        // Create a pop-up menu components
	        Menu setupItem = new Menu("Setup");
	        MenuItem setupKeyItem = new MenuItem("Key binding");
	        setupKeyItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ButtonSetup bs=new ButtonSetup(getController());  
	                bs.setVisible(true);
				}
			});
	        MenuItem launchItem = new MenuItem("Launch");
	        launchItem.addActionListener(new ActionListener() {

	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		GlandZoneSelector gs=new GlandZoneSelector();  
	        		gs.setVisible(true);
	        	}
	        });
	        setupItem.add(setupKeyItem);
	        this.add(setupItem);
	        this.addSeparator();
	        this.add(launchItem);
	        MenuItem getIp = new MenuItem("Get IP");
	        getIp.addActionListener(new ActionListener() {

	        	@Override
	        	public void actionPerformed(ActionEvent e) {
        			URL whatismyip;
					try {
						whatismyip = new URL("http://automation.whatismyip.com/n09230945.asp");
					
	        			BufferedReader in = new BufferedReader(new InputStreamReader(
	        			                whatismyip.openStream()));
	
	        			String ip = in.readLine();
	        			JOptionPane.showMessageDialog(null,
	        					"IP : "+ip);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}

	        	}
	        });
      	this.add(getIp);
        }else{
        	 
        }
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
       
        this.add(exitItem);
       
        trayIcon.setPopupMenu(this);
        trayIcon.setImageAutoSize(true);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
	}
	
	public Controller getController() {
		return cont;
	}

	public void setController(Controller cont) {
		this.cont = cont;
	}

	//Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = ClassLoader.getSystemResource(path);
        System.out.println(imageURL.getPath());
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(path, description)).getImage();
        }
    }
}
