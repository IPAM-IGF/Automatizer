package main;
import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import tools.server.AutomatizerServer;

import control.Controller;
import display.ButtonSetup;
import display.TrayPopupMenu;



public class Server {
	/**
	 *  Quelques composantes statiques  de configuration
	 */
	public static String CONF_FILE="keyBinding.conf";
	// a décommenter quand on exporte en jar, et changer dans traypopupmenu !
	public static final URL LOGO_URL = Automatizer.class.getClassLoader().getResource("images/logo.png");
	
	
	private static AutomatizerServer server;
	
	
	private static void getSimulationWindows(){
		JFrame motorf=new JFrame("Motor");
		JFrame acquif=new JFrame("Acquisition");
		motorf.setLayout(new FlowLayout());
		acquif.setLayout(new FlowLayout());
		motorf.setSize(500,200);
		acquif.setSize(400, 200);
		motorf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		acquif.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JLabel jl=new JLabel("0");
		final JTextField jt=new JTextField("");
		jt.setColumns(10);
		JButton tm=new JButton("-");
		tm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jl.setText(""+(Integer.parseInt(jl.getText())-Integer.parseInt(jt.getText())));
			}
		});
		motorf.getContentPane().add(tm,BorderLayout.CENTER);
		motorf.getContentPane().add(jt,BorderLayout.CENTER);
		JButton tp=new JButton("+");
		tp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jl.setText(""+(Integer.parseInt(jl.getText())+Integer.parseInt(jt.getText())));
			}
		});
		motorf.getContentPane().add(tp,BorderLayout.CENTER);
		
		motorf.getContentPane().add(jl,BorderLayout.SOUTH);
		
		final JLabel jl2=new JLabel("0");
		final JTextField jt2=new JTextField("");
		jt2.setColumns(10);
		JButton tm2=new JButton("-");
		tm2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jl2.setText(""+(Integer.parseInt(jl2.getText())-Integer.parseInt(jt2.getText())));
			}
		});
		motorf.getContentPane().add(tm2,BorderLayout.CENTER);
		motorf.getContentPane().add(jt2,BorderLayout.CENTER);
		JButton tp2=new JButton("+");
		tp2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jl2.setText(""+(Integer.parseInt(jl2.getText())+Integer.parseInt(jt2.getText())));
			}
		});
		motorf.getContentPane().add(tp2,BorderLayout.CENTER);
		
		motorf.getContentPane().add(jl2,BorderLayout.SOUTH);
		
		JButton meas=new JButton("MEASURE");
		final JProgressBar jp=new JProgressBar();
		jp.setVisible(false);
		jp.setMaximum(100);
		jp.setValue(0);
		meas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jp.setVisible(true);
				Thread thr=new Thread(){
					public void run(){
						for(int i=0;i<10;i++){
							jp.setValue(jp.getValue()+10);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						jp.setVisible(false);
						jp.setValue(0);
					}
				};
				thr.start();
			}
		});
		acquif.add(meas);
		acquif.add(jp);
		motorf.setVisible(true);
		acquif.setVisible(true);
	}
	
	
	
	/**
	 * Detect the path to the conf file and save it
	 */
	private static void setConfFile() {
		String jarlocation = Server.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		CONF_FILE = jarlocation.substring(0,jarlocation.lastIndexOf("/"))+"/"+CONF_FILE;
	}
	
	/**
	 * @param args
	 */
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
        
        // Detect the path to the jar file
        //setConfFile();
        // Simulation 
       // getSimulationWindows();
        
        
        final Controller controller=new Controller(1);
        TrayPopupMenu popup = new TrayPopupMenu(Server.LOGO_URL,controller, true, null);
        
        // Launch server and re launch it when it end
        server = new AutomatizerServer();
        server.start();
        Timer refresh = new Timer(30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!server.isAlive()){
					server = new AutomatizerServer();
					server.start();
				}
				
			}
		});
        refresh.start();
        
        
        boolean setupIsOK=false;
        
        if(new File(Server.CONF_FILE).exists()){
        	setupIsOK=controller.loadConf();
        }
        if(!setupIsOK){
        	SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ButtonSetup bs=new ButtonSetup(controller);  
                    bs.setVisible(true);
                }               
            });
        }
      /*  try 
			motor.focus("motor");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ((ButtonTextItem)motor.get("X step")).setText(""+Case.CASE_DIMENSION.width);*/
	}
	
}
