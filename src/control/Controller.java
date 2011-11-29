package control;

import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import tools.FileWorker;
import tools.Scripts;


import display.ButtonSetup;
import display.TrayPopupMenu;

public class Controller {
	
	/**
	 *  Les boutons disponibles	
	 */
	public static final HashMap<String,String> BUTTONS_ACQUISITION=new HashMap<String,String>();
	static{
		BUTTONS_ACQUISITION.put("Measure","ButtonItem");
		BUTTONS_ACQUISITION.put("Acquisition Window","ButtonItem");
	}
	
	public static final HashMap<String,String> BUTTONS_MOTOR=new HashMap<String,String>();
	static{
		
		BUTTONS_MOTOR.put("X ZoomIn","ButtonItem");
		BUTTONS_MOTOR.put("X ZoomOut","ButtonItem");
		BUTTONS_MOTOR.put("Y ZoomIn","ButtonItem");
		BUTTONS_MOTOR.put("Y ZoomOut","ButtonItem");
		BUTTONS_MOTOR.put("X step","ButtonTextItem");
		BUTTONS_MOTOR.put("Y step","ButtonTextItem");
		BUTTONS_MOTOR.put("Motor Window","ButtonItem");
	}
	
	public static final HashMap<String,String> BUTTONS_NAME_TYPE=new HashMap<String,String>();
	
	static{
		BUTTONS_NAME_TYPE.putAll(BUTTONS_ACQUISITION);
		BUTTONS_NAME_TYPE.putAll(BUTTONS_MOTOR);
	}
	
	/**
	 *  Quelques composantes statiques  de configuration
	 */
	public static final String CONF_FILE="keyBinding.conf";
	public static final String LOGO_URL = "images/logo.png";
	
	/**
	 * Attributs
	 */
	private Robot bot;
	private HashMap<String,ButtonItem> buttons;
	private CountDownLatch setupSignal;

	
	// Numéro de la fenetre
	private int numeroFenetre = -1 ;
	
	
	public Controller(){
		Scripts.CONTROLLER=this;
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
	public boolean loadConf() {
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
	 * @throws Exception 
	 */
	public void focus(String window) throws Exception {
		Point loc = null;
		switch(window){
		case "motor":
			loc=buttons.get("Motor Window").getPosition();break;
		case "acquisition":
			loc=buttons.get("Acquisition Window").getPosition();break;
		default:
			throw new Exception("Unknow window to focus ... ");	
		}
		bot.mouseMove(loc.x,loc.y+20);
		bot.mousePress(InputEvent.BUTTON1_MASK);
		bot.delay(100);
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
		/*looseFocus();
		for(int i=0;i<numeroFenetre;i++){
			bot.keyPress(KeyEvent.VK_ALT);
			bot.keyPress(KeyEvent.VK_TAB);	
			bot.keyRelease(KeyEvent.VK_ALT);
			bot.keyRelease(KeyEvent.VK_TAB);	
		}*/
	}
	
	/**
	 * On affiche une fenêtre invisible pour perdre le focus
	 * sur toutes les fenêtres du bureau
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
        
        try {
			motor.focus("motor");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
       // ((ButtonTextItem)motor.get("google")).setText("j'ai reussi");
       // ((ButtonItem)motor.get("MozillaWindow")).leftClick();
	}
}
