package display;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import Control.Controller;
import Listeners.KeyDetect;
import Listeners.MouseDetect;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class TranslucentWindow extends JFrame {
	
	public static Dimension screenSize;
	public static int MAX_STEP=1;
	public static int CURR_STEP=0;
	
	// Boite de dialogue d'aide
	public static final String MSG="Where is located the ";
	private final Dimension dimAide=new Dimension(330,50);
	private JFrame dialogFrame;
	private JLabel txtAide;
	private String button;
	
	// Type de bouton 
	private String buttonType;
	
	// Données temporaires
	private Point tempXY;
	
	//Controlleur
	private Controller cont;
	
	// Setup window
	private ButtonSetup setupWindow;
	
    public TranslucentWindow(String title, String button, String type, Controller c) {
    	super(title);
    	cont=c;
    	buttonType=type;
    	this.button=button;
    	CURR_STEP=0;
    	setLayout(new GridBagLayout());
        setUndecorated(true);
        screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseListener(new MouseDetect(this));
        getContentPane().addKeyListener(new KeyDetect(this));
        getContentPane().setFocusable(true);
        dialogFrame=new JFrame("");
        txtAide=new JLabel(MSG+this.button);
        dialogFrame.add(txtAide);
        dialogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialogFrame.setSize(dimAide.width,dimAide.height);
        dialogFrame.setLocation(screenSize.width/2-dimAide.width/2,screenSize.height/2-dimAide.height/2);
        dialogFrame.setUndecorated(true);
        dialogFrame.setOpacity(0.80f);
        dialogFrame.setVisible(true);
    }
    
    public void dispose(){
    	if(dialogFrame!=null) dialogFrame.dispose();
    	super.dispose();
    }
    
    public void setupClickedButton(){
		cont.setButton(button, buttonType, getTempXY());
		dispose();
		cont=null;
		if(setupWindow!=null){
			setupWindow.setVisible(true);
			setupWindow=null;
		}
    }

	public Point getTempXY() {
		return tempXY;
	}

	public void setTempXY(Point tempXY) {
		this.tempXY = tempXY;
		setupClickedButton();
	}
	
    public Controller getCont() {
		return cont;
	}

	public void setCont(Controller cont) {
		this.cont = cont;
	}

	public void setSetupWindow(ButtonSetup s) {
		this.setupWindow=s;
	}
}
