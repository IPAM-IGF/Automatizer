package display;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;

import tools.Scripts;

import display.panels.Case;
import display.panels.GlandPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GlandZoneSelector extends JFrame{
	public static GlandPanel currentPanel = new GlandPanel();
	
	public static File saveDirectory = new File(".");
	
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
				askForParametersAndBegin(gpanel);
				
				
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
	
	protected void askForParametersAndBegin(final GlandPanel gpanel) {
		final JFrame jf = new JFrame("Parameters");
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setSize(500,142);
		jf.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(5dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(73dlu;pref)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("pref:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("pref:grow"),},
			new RowSpec[] {
				RowSpec.decode("1px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JPanel panel = new JPanel();
		jf.getContentPane().add(panel, "9, 1, fill, top");
		panel.setLayout(new FormLayout(new ColumnSpec[] {},
			new RowSpec[] {}));
		
		JLabel lblSizeOfOne = new JLabel("Size of one block");
		jf.getContentPane().add(lblSizeOfOne, "3, 3, center, center");
		Dimension caseDim = new Dimension(2000,2000);
		if(Case.CASE_DIMENSION != null) caseDim = Case.CASE_DIMENSION;
		final JSpinner spinnerX = new JSpinner();
		spinnerX.setModel(new SpinnerNumberModel(caseDim.width, 10, 100000, 10));
		jf.getContentPane().add(spinnerX, "5, 3, fill, center");
		
		JLabel lblX = new JLabel("x");
		jf.getContentPane().add(lblX, "7, 3");
		
		final JSpinner spinnerY = new JSpinner();
		spinnerY.setModel(new SpinnerNumberModel(caseDim.height, 10, 100000, 10));
		jf.getContentPane().add(spinnerY, "9, 3, fill, center");
		
		JLabel lblSaveDirectory = new JLabel("Save directory");
		jf.getContentPane().add(lblSaveDirectory, "3, 5, center, center");
		
		final JTextField txtDirectory = new JTextField();
		txtDirectory.setText("directory");
		jf.getContentPane().add(txtDirectory, "5, 5, fill, center");
		txtDirectory.setColumns(10);
		
		JButton button = new JButton(". . .");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {	        
			    JFileChooser chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(saveDirectory);
			    chooser.setDialogTitle("Save directory");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    //
			    // disable the "All files" option.
			    //
			    chooser.setAcceptAllFileFilterUsed(false);
			    //    
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			      System.out.println("getCurrentDirectory(): " 
			         +  chooser.getCurrentDirectory());
			      System.out.println("getSelectedFile() : " 
			         +  chooser.getSelectedFile());
			      txtDirectory.setText(chooser.getSelectedFile().getAbsolutePath());
			      }
			    else {
			      System.out.println("No Selection ");
			      }				
			}
		});
		jf.getContentPane().add(button, "9, 5, center, center");
		
		JButton btnOk = new JButton("OK");
		jf.getContentPane().add(btnOk, "3, 7, center, center");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jf.dispose();
				saveDirectory = new File(txtDirectory.getText());
				Case.CASE_DIMENSION = new Dimension((Integer)spinnerX.getValue(),(Integer)spinnerY.getValue());
				System.out.println(saveDirectory.getAbsolutePath()+"--"+ saveDirectory.getName());
				Thread simpleA = new Thread(){
					public void run(){
						Scripts.simpleAcquisition(saveDirectory.getAbsolutePath(), saveDirectory.getName(),gpanel);
					}
				};
				simpleA.start();
			}
		});
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jf.dispose();
				
			}
		});
		jf.getContentPane().add(btnCancel, "5, 7, center, center");
		jf.setVisible(true);
	}

	public static void main(String[] args){
		GlandZoneSelector gs=new GlandZoneSelector();
		gs.setVisible(true);
	}
}
