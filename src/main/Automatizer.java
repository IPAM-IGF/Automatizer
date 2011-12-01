package main;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;

import tools.server.ServerResponse;
import main.Client.*;

/**
 * Main class
 * @author Jérémy DEVERDUN
 *
 */
public class Automatizer {
	public static void main(String[] args){
		final JFrame mainWindow = new JFrame("Automatizer");
		mainWindow.setSize(200, 100);
		mainWindow.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-200,Toolkit.getDefaultToolkit().getScreenSize().height/2-100);
		mainWindow.setUndecorated(true);
		mainWindow.setOpacity(0.1f);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(null);
		// Voir Server.java pour rendre visible les logos  dans un jar
		ImageIcon ic = new ImageIcon(Automatizer.class.getClassLoader().getResource("images/server-logo.png"));//"server-logo.png");
		Image img = ic.getImage() ;  
		Image newimg = img.getScaledInstance( 85, 76,  java.awt.Image.SCALE_SMOOTH ) ;  
		ic = new ImageIcon( newimg );

			
		JButton btnServer = new JButton(ic);
		btnServer.setBounds(12, 12, 85, 76);
		btnServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.dispose();
				Server.main(null);
			}
		});
		mainWindow.getContentPane().add(btnServer);
		btnServer.setBackground(Color.white);
		ic = new ImageIcon(Automatizer.class.getClassLoader().getResource("images/client-logo.png"));
		img = ic.getImage() ;  
		newimg = img.getScaledInstance( 85, 76,  java.awt.Image.SCALE_SMOOTH ) ;  
		ic = new ImageIcon( newimg );
		JButton btnClient = new JButton(ic);
		btnClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.dispose();
				Client.main(null);
			}
		});
		btnClient.setBounds(103, 12, 85, 76);
		btnClient.setBackground(Color.white);
		mainWindow.getContentPane().add(btnClient);
		mainWindow.setVisible(true);
		for(float i=0;i<=1;i+=0.01){
			mainWindow.setOpacity(i);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
