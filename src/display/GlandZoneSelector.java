package display;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;

import display.panels.GlandPanel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;

public class GlandZoneSelector extends JFrame{
	public GlandZoneSelector(){
		super();
		getContentPane().setLayout(new BorderLayout(0, 0));
		setSize(200,200);
		GlandPanel gpanel = new GlandPanel();
		getContentPane().add(gpanel, BorderLayout.CENTER);
		gpanel.setLayout(new GridLayout(1, 0, 0, 0));

		
		JPanel panelValid = new JPanel();
		getContentPane().add(panelValid, BorderLayout.SOUTH);
		GridBagLayout gbl_panelValid = new GridBagLayout();
		gbl_panelValid.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelValid.rowHeights = new int[]{0, 0};
		gbl_panelValid.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelValid.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelValid.setLayout(gbl_panelValid);
		
		JButton btnLaunch = new JButton("Launch");
		GridBagConstraints gbc_btnLaunch = new GridBagConstraints();
		gbc_btnLaunch.insets = new Insets(0, 0, 0, 5);
		gbc_btnLaunch.gridx = 1;
		gbc_btnLaunch.gridy = 0;
		panelValid.add(btnLaunch, gbc_btnLaunch);
		
		JButton btnCancel = new JButton("Cancel");
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
