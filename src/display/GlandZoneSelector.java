package display;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;

import tools.Scripts;

import display.panels.GlandPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GlandZoneSelector extends JFrame{
	public static GlandPanel currentPanel = new GlandPanel();
	public GlandZoneSelector(){
		super();
		getContentPane().setLayout(new BorderLayout(0, 0));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width,screenSize.height-40);
		final GlandPanel gpanel = currentPanel;
		currentPanel = gpanel;
		getContentPane().add(gpanel, BorderLayout.CENTER);
		setUndecorated(true);
		setOpacity(0.9f);
		
		JPanel panelValid = new JPanel();
		getContentPane().add(panelValid, BorderLayout.SOUTH);
		GridBagLayout gbl_panelValid = new GridBagLayout();
		gbl_panelValid.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelValid.rowHeights = new int[]{100, 0};
		gbl_panelValid.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelValid.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelValid.setLayout(gbl_panelValid);
		
		JButton btnLaunch = new JButton("Launch");
		btnLaunch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//dispose();
				//setVisible(false);
				Thread simpleA = new Thread(){
					public void run(){
						Scripts.simpleAcquisition(gpanel);
					}
				};
				simpleA.start();
				
				// Definit un timer pour toute les minutes
				/*Timer check = new Timer(1000, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						
					}
				});
				check.start();*/
			}
		});
		GridBagConstraints gbc_btnLaunch = new GridBagConstraints();
		gbc_btnLaunch.insets = new Insets(0, 0, 0, 5);
		gbc_btnLaunch.gridx = 1;
		gbc_btnLaunch.gridy = 0;
		panelValid.add(btnLaunch, gbc_btnLaunch);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 0;
		panelValid.add(btnCancel, gbc_btnCancel);
	}
	
	public static void main(String[] args){
		GlandZoneSelector gs=new GlandZoneSelector();
		gs.setVisible(true);
	}
}
