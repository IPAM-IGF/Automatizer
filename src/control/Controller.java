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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.Server;

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
		BUTTONS_ACQUISITION.put("Démarrer","ButtonItem");
		BUTTONS_ACQUISITION.put("Save as","ButtonItem");
		BUTTONS_ACQUISITION.put("Acquisition Window","ButtonItem");
	}
	public static final HashMap<String,String> BUTTONS_SAVEAS=new HashMap<String,String>();
	static{
		BUTTONS_SAVEAS.put("File name","ButtonTextItem");
		BUTTONS_SAVEAS.put("OK","ButtonItem");
		BUTTONS_SAVEAS.put("Save As Window","ButtonItem");
		
	}
	
	public static final HashMap<String,String> BUTTONS_MOTOR=new HashMap<String,String>();
	static{
		
		BUTTONS_MOTOR.put("Move +","ButtonItem");
		BUTTONS_MOTOR.put("Move -","ButtonItem");
		BUTTONS_MOTOR.put("X step","ButtonTextItem");
		BUTTONS_MOTOR.put("Y step","ButtonTextItem");
		BUTTONS_MOTOR.put("Motor Window","ButtonItem");
	}
	
	public static final HashMap<String,String> BUTTONS_NAME_TYPE=new HashMap<String,String>();
	
	static{
		BUTTONS_NAME_TYPE.putAll(BUTTONS_ACQUISITION);
		BUTTONS_NAME_TYPE.putAll(BUTTONS_MOTOR);
		BUTTONS_NAME_TYPE.putAll(BUTTONS_SAVEAS);
	}
	
	
	/**
	 * Le délai entre les mousepress et les mouserelease en ms
	 */
	
	public static final int DELAY_PRESS_CLICK = 100;
	
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
	public void setButton(String name, String type, ArrayList<Point> pts) {
		switch(type){
		case "ButtonTextItem":
			buttons.put(name, new ButtonTextItem(bot,name,pts));
			break;
		case "ButtonItem":
			buttons.put(name, new ButtonItem(bot,name,pts));
			break;
	}
		
	}
	public void setButton(String name,String type, Point location){
		switch(type){
			case "ButtonTextItem":
				if(buttons.get(name) == null){
					buttons.put(name, new ButtonTextItem(bot,name,location));
					break;
				}else{
					buttons.get(name).addPosition(location);
				}
					
				break;
			case "ButtonItem":
				if(buttons.get(name) == null){
					buttons.put(name, new ButtonItem(bot,name,location));
					break;
				}else{
					buttons.get(name).addPosition(location);
				}
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
		String fname=Server.CONF_FILE;
		String content="";
		for(String name:buttons.keySet()){
			ButtonItem item=buttons.get(name);
			item.setName(name);
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
		String content=FileWorker.read(new File(Server.CONF_FILE));
		String[] lignes=content.split("\n");
		for(String ligne:lignes){
			String[] infos=ligne.split("::");
			String[] listpoints = infos[2].split(";");
			ArrayList<Point> pts = new ArrayList<Point>();
			for(String pt : listpoints){
				pts.add(new Point(Integer.parseInt(pt.split("//")[0]),Integer.parseInt(pt.split("//")[1])));
			}
			setButton(infos[0], infos[1], pts);
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
			loc=buttons.get("Motor Window").getPosition().get(0);break;
		case "acquisition":
			loc=buttons.get("Acquisition Window").getPosition().get(0);break;
		case "saveas":
			buttons.get("Save As Window").leftClick();return;
		default:
			throw new Exception("Unknow window to focus ... ");	
		}
		bot.mouseMove(loc.x,loc.y+20);
		bot.mousePress(InputEvent.BUTTON1_MASK);
		bot.delay(DELAY_PRESS_CLICK);
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
		// On attend que la fenêtre soit bien en focus
		bot.delay(1000);
		/*looseFocus();
		for(int i=0;i<numeroFenetre;i++){
			bot.keyPress(KeyEvent.VK_ALT);
			bot.keyPress(KeyEvent.VK_TAB);	
			bot.keyRelease(KeyEvent.VK_ALT);
			bot.keyRelease(KeyEvent.VK_TAB);	
		}*/
	}
	
	public void resetButton(String button) {
		ButtonItem b = buttons.get(button);
		if(b == null) return;
		buttons.remove(button);
		b.reset();
		
	}
	public Robot getBot(){
		return bot;
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
        TrayPopupMenu popup = new TrayPopupMenu(Server.LOGO_URL,motor,true);

        boolean setupIsOK=false;
        
        if(new File(Server.CONF_FILE).exists()){
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
