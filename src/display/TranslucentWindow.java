package display;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;

import javax.swing.*;

import control.Controller;
import control.listeners.KeyDetect;
import control.listeners.MouseDetect;

import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class TranslucentWindow extends JFrame {
	
	public static Dimension screenSize;
	public static int MAX_STEP=1;
	public static int CURR_STEP=0;
	
	// Boite de dialogue d'aide
	public static final String MSG="Where is located the ";
	private final Dimension dimAide=new Dimension(330,50);
	private JWindow dialogFrame;
	private JLabel txtAide;
	private String button;
	
	// Type de bouton 
	private String buttonType;
	
	// Données temporaires
	private Point tempXY;
	
	//Controlleur
	private Controller cont;
	
	// Bouton à paramétrer
	private JButton setupButton;
	
    public TranslucentWindow(String title, String button, String type, Controller c) {
    	super(title);
    	cont=c;
    	buttonType=type;
    	this.button=button;
    	cont.resetButton(this.button);
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
        dialogFrame=new JWindow(this);
        txtAide=new JLabel(MSG+this.button);
        dialogFrame.add(txtAide);
        //dialogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialogFrame.setSize(dimAide.width,dimAide.height);
        dialogFrame.setLocation(screenSize.width/2-dimAide.width/2,screenSize.height/2-dimAide.height/2);
      //  dialogFrame.setUndecorated(true);
        dialogFrame.setOpacity(0.80f);
        dialogFrame.setVisible(true);
    }
    
    public void dispose(){
    	if(dialogFrame!=null) dialogFrame.dispose();
    	super.dispose();
    }
    
    /**
     * 
     * @param serial true si c'est une séquence de clic
     */
    public void setupClickedButton(boolean serial){
		cont.setButton(button, buttonType, getTempXY());
		// on continu tant que l'utilisateur n'a pas fait un clic sans control
		if(serial) return;
		dispose();
		cont=null;
		if(setupButton!=null){
			setupButton.setBackground(ButtonSetup.DEFINED_BG);
			setupButton.getTopLevelAncestor().setVisible(true);
			setupButton=null;
		}
    }

	public Point getTempXY() {
		return tempXY;
	}

	public void setTempXY(Point tempXY) {
		this.tempXY = tempXY;
		setupClickedButton(false);
	}
	
    public Controller getCont() {
		return cont;
	}

	public void setCont(Controller cont) {
		this.cont = cont;
	}

	public void setSetupWindow(JButton s) {
		this.setupButton=s;
	}

	public void setTempSerialXY(Point tempXY) {
		this.tempXY = tempXY;
		this.setVisible(false);
		cont.getBot().mouseMove(this.tempXY.x, this.tempXY.y);
		cont.getBot().mousePress(InputEvent.BUTTON1_MASK);
		//cont.getBot().delay(Controller.DELAY_PRESS_CLICK);
		cont.getBot().mouseRelease(InputEvent.BUTTON1_MASK);
		this.requestFocus();
		this.setVisible(true);
		setupClickedButton(true);
		this.requestFocus();
	}
}
