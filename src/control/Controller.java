package control;

import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import tools.FileWorker;


import display.ButtonSetup;
import display.TrayPopupMenu;

public class Controller {
	public static final HashMap<String,String> BUTTONS_NAME_TYPE=new HashMap<String,String>();
	
	static{
		BUTTONS_NAME_TYPE.put("ZoomIn","ButtonItem");
		BUTTONS_NAME_TYPE.put("ZoomOut","ButtonItem");
		BUTTONS_NAME_TYPE.put("MozillaWindow","ButtonItem");
	}
	
	public static final String CONF_FILE="keyBinding.conf";
	private static final String LOGO_URL = "images/logo.png";
	
	
	private Robot bot;
	private HashMap<String,ButtonItem> buttons;
	private CountDownLatch setupSignal;

	
	// Numéro de la fenetre
	private int numeroFenetre = -1 ;
	
	
	public Controller(){
		buttons=new HashMap<String,ButtonItem>();
		try {
			bot=new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(0);
		}
		setSetupSignal(new CountDownLatch(1));
	}
	
	public Controller(int n){
		this();
		numeroFenetre=n;	
		System.out.println(numeroFenetre);
	}
	
	public ButtonItem get(String s){
		return buttons.get(s);
	}
	
	public void setButton(String name,String type, Point location){
		switch(type){
			case "ButtonTextItem":
				buttons.put(name, new ButtonTextItem(bot,name,location));
				break;
			case "ButtonItem":
				buttons.put(name, new ButtonItem(bot,name,location));
				break;
		}
	}
	
	
	public CountDownLatch getSetupSignal() {
		return setupSignal;
	}
	
	public void resetSetupSignal() {
		setupSignal=new CountDownLatch(1);
	}

	public void setSetupSignal(CountDownLatch setupSignal) {
		this.setupSignal = setupSignal;
	}

	/**
	 *  Sauvegarde l'association bouton <--> coordonnée dans un fichier
	 *  à la racine du programme
	 */
	public void save() {
		String fname=CONF_FILE;
		String content="";
		for(String name:buttons.keySet()){
			ButtonItem item=buttons.get(name);
			if(item instanceof ButtonTextItem)
				content+=((ButtonTextItem)item).toString()+"\n";
			else
				content+=item.toString()+"\n";
		}
		FileWorker.writeTo(new File(fname), content, false);
	}
	
	/**
	 * Charge le fichier de configuration pour l'association bouton - coordonnées
	 * @return renvoi un boolean = true si tous les boutons ont été paramétrés
	 * ou false si ce n'est pas le cas
	 */
	private boolean loadConf() {
		String content=FileWorker.read(new File(CONF_FILE));
		String[] lignes=content.split("\n");
		for(String ligne:lignes){
			String[] infos=ligne.split("::");
			setButton(infos[0], infos[1], new Point(Integer.parseInt(infos[2].split("//")[0]),Integer.parseInt(infos[2].split("//")[1])));
		}
		return (buttons.size()==BUTTONS_NAME_TYPE.size());
	}
	
	/**
	 * Permet de focus la fenêtre associé au controller
	 * en utilisant les commandes ALT+TAB autant de fois
	 * qu'il le faut (dépend du numéro de la fenêtre)
	 */
	private void focus() {
		looseFocus();
		for(int i=0;i<numeroFenetre;i++){
			bot.keyPress(KeyEvent.VK_ALT);
			bot.keyPress(KeyEvent.VK_TAB);	
			bot.keyRelease(KeyEvent.VK_ALT);
			bot.keyRelease(KeyEvent.VK_TAB);	
		}
	}
	
	/**
	 * On affiche une fenêtre invisible pour perdre le focus
	 * sur toutes les fenêtre du bureau
	 */
	public static void looseFocus(){
		JFrame jf=new JFrame();
		jf.setUndecorated(true);
		jf.setOpacity(0.1f);
		jf.setVisible(true);
		jf.requestFocus();
		jf.dispose();
	}
	
	public static void main(String[] args) {
		// Determine if the GraphicsDevice supports translucency.
        GraphicsEnvironment ge = 
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //If translucent windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
            System.err.println(
                "Translucency is not supported");
                System.exit(0);
        }
        
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final Controller motor=new Controller(1);
        TrayPopupMenu popup = new TrayPopupMenu(LOGO_URL,motor);
        
        boolean setupIsOK=false;
        
        if(new File(CONF_FILE).exists()){
        	setupIsOK=motor.loadConf();
        }
        if(!setupIsOK){
        	SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ButtonSetup bs=new ButtonSetup(motor);  
                    bs.setVisible(true);
                }               
            });
        	try {
				motor.getSetupSignal().await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        //((ButtonTextItem)cont.get("googleText")).setText("j'ai reussi");
        motor.focus();
       // ((ButtonItem)motor.get("MozillaWindow")).leftClick();
	}
}
