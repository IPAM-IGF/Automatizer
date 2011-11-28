package display;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;

import control.Controller;


public class TrayPopupMenu extends PopupMenu {

	private final TrayIcon trayIcon;
	
	private String imageLoc="";
	private MenuItem exitItem, setupKeyItem;
	private Menu setupItem;
	private Controller cont;
	
	public TrayPopupMenu(String image, Controller cont){
		super();
		imageLoc=image;
		this.cont=cont;
		trayIcon =
                new TrayIcon(createImage(imageLoc, "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
       
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
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
       
        //Add components to pop-up menu
        setupItem.add(setupKeyItem);
        this.add(setupItem);
        this.addSeparator();
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
