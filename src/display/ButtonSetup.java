package display;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import control.Controller;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.util.Set;
import java.awt.FlowLayout;

public class ButtonSetup extends JFrame {

	private static final Dimension DEFAULT_SIZE=new Dimension(300,600);
	private static final String TITLE="Command setup";
	
	// Couleur des boutonns selon si on les a défini ou non
	public static final Color DEFINED_BG = Color.GREEN;
	public static final Color UNDEFINED_BG = Color.RED;
	
	
	private Controller cont;
	
	
	public ButtonSetup(Controller cont){
		this.cont=cont;
		setSize(DEFAULT_SIZE);
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
		      public void windowClosing(WindowEvent e)
		      {
		          exitProcedure();
		      }
		});
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		
		for(int i=0;i<3;i++){
			JPanel panel = new JPanel();
			panel.setLayout(gridBagLayout);
			int ligne=1;
			int colonne=0;
			Set<String> cles = null;
			if(i==0){
				cles=Controller.BUTTONS_MOTOR.keySet();
				TitledBorder title = BorderFactory.createTitledBorder("Motor");
				panel.setBorder(title);
			}else{
				if(i==1){
					cles=Controller.BUTTONS_ACQUISITION.keySet();
					TitledBorder title = BorderFactory.createTitledBorder("Acquisition");
					panel.setBorder(title);
				}else{
					if(i==2){
						cles=Controller.BUTTONS_SAVEAS.keySet();
						TitledBorder title = BorderFactory.createTitledBorder("Save As");
						panel.setBorder(title);
					}
				}
			}
			for(String n:cles){
				if(colonne==2){
					ligne++;colonne=0;
				}
				final JButton btn = new JButton(n);
				if(getController().get(n)==null) btn.setBackground(UNDEFINED_BG);
				else btn.setBackground(DEFINED_BG);
				final String bname=n;
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);			
						TranslucentWindow tw = new TranslucentWindow("PLease don't touch this until setup isn't complete", bname, Controller.BUTTONS_NAME_TYPE.get(bname), getController());
		                tw.setSetupWindow(btn);
						tw.setOpacity(0.10f);
		                tw.setVisible(true);  		
					}
				});
				GridBagConstraints gbc_btnFde = new GridBagConstraints();
				gbc_btnFde.insets = new Insets(5, 0, 0, 5);
				gbc_btnFde.gridx = colonne++;
				gbc_btnFde.gridy = ligne;
				panel.add(btn, gbc_btnFde);
			}
			
			mainPanel.add(panel);
		}
		
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		
		JPanel panelSouth = new JPanel();
		getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exitProcedure();
			}
		});
		panelSouth.add(btnClose);
		
		

		
	}
	public Controller getController() {
		return cont;
	}
	public void setController(Controller cont) {
		this.cont = cont;
	}
	
	public ButtonSetup getThis(){
		return this;
	}
	private void exitProcedure() {
		getController().save();
		getController().getSetupSignal().countDown();
		dispose();
	}
	
	public static void main(String[] args){
		ButtonSetup bs=new ButtonSetup(new Controller());
		bs.setVisible(true);
	}
}
