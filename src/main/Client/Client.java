package main.Client;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import display.panels.GlandPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import main.Automatizer;


public class Client extends JFrame {
	// Loading frame used in main
	private static JFrame loadingFrame;
	
	
	private GlandPanel glandPanel;
	
	public Client(){
		super();
	}
	public void createAndShowGUI() {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setOpacity(0.9f);
		glandPanel.setMouseOverOnly(true);
		add(glandPanel);
		setVisible(true);
		Timer refresh = new Timer(5000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Asker asker = new Asker(null, getThis(),Worker.UPDATED_CASES, false);
			}
		});
		refresh.start();
	}
	public GlandPanel getGlandPanel() {
		return glandPanel;
	}
	public void setGlandPanel(GlandPanel glandPanel) {
		this.glandPanel = glandPanel;
		if(loadingFrame!=null){
			loadingFrame.dispose();
			loadingFrame = null;
		}
	}
	public Client getThis(){
		return this;
	}
	public static void main(String[] args){
		Client client=new Client();
		loadingFrame = new JFrame("Loading");
		loadingFrame.setSize(200, 200);
		loadingFrame.setUndecorated(true);
		loadingFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-200,Toolkit.getDefaultToolkit().getScreenSize().height/2-200);
		loadingFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		JProgressBar jp = new JProgressBar();
		jp.setIndeterminate(true);
		loadingFrame.getContentPane().add(jp, BorderLayout.SOUTH);
		ImageIcon ic = new ImageIcon(Automatizer.class.getClassLoader().getResource("images/logo.png"));//"server-logo.png");
		JButton logo = new JButton(ic);
		logo.setEnabled(false);
		loadingFrame.getContentPane().add(logo, BorderLayout.CENTER);
		//loadingFrame.add(jp);
		loadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loadingFrame.setVisible(true);
		Asker asker = new Asker(jp, client,Worker.GET_GLAND_PANEL, true);
	}
}
