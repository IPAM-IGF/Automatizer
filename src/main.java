import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.ObjectInputStream.GetField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tools.Scripts;

import control.ButtonTextItem;
import control.Controller;
import display.ButtonSetup;
import display.TrayPopupMenu;
import display.panels.Case;


public class main {

	
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
		
		
		acquif.add(new JButton("MEASURE"));
		motorf.setVisible(true);
		acquif.setVisible(true);
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
        
        
        // Simulation 
        getSimulationWindows();
        
        
        final Controller motor=new Controller(1);
        TrayPopupMenu popup = new TrayPopupMenu(Controller.LOGO_URL,motor);

        boolean setupIsOK=false;
        
        if(new File(Controller.CONF_FILE).exists()){
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
      /*  try 
			motor.focus("motor");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ((ButtonTextItem)motor.get("X step")).setText(""+Case.CASE_DIMENSION.width);*/
	}
}
