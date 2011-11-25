package display;

import java.awt.Dimension;

import javax.swing.JFrame;

import Control.Controller;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

public class ButtonSetup extends JFrame {

	private static final Dimension DEFAULT_SIZE=new Dimension(300,600);
	private static final String TITLE="Command setup";
	
	
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
		
		JPanel panel = new JPanel();
		panel.setLayout(gridBagLayout);
		int ligne=1;
		int colonne=0;
		for(String n:Controller.BUTTONS_NAME_TYPE.keySet()){
			if(colonne==2){
				ligne++;colonne=1;
			}
			JButton btn = new JButton(n);
			final String bname=n;
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);			
					TranslucentWindow tw = new TranslucentWindow("PLease don't touch this until setup isn't complete", bname, Controller.BUTTONS_NAME_TYPE.get(bname), getController());
	                tw.setSetupWindow(getThis());
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
		
		getContentPane().add(panel);
		
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
