package display.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class Case extends JPanel{
	
	
	private static final Dimension DEFAULT_SIZE=new Dimension(40,40);

	// Couleur de la case
	public static final Color DEFAULT_BG = null;
	public static final Color SELECTED_BG = Color.GREEN;
	public static final Color MOUSEOVER_BG = Color.WHITE;
	
	// Numéro du bloc
	private int numero = -1;
	private JLabel lbl_num;
	
	// Selection du bloc
	private boolean selected=false;
	
	public Case(int width,int height, int num){
		this(width,height);
		setNumero(num);
	}
	
	
	public Case(int width,int height){
		lbl_num=new JLabel();
		add(lbl_num);
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(width, height);
	}
	
	
	public Case(){
		lbl_num=new JLabel();
		add(lbl_num);
		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(DEFAULT_SIZE);
	}

	public void setSize(int w, int h){
		super.setSize(w,h);
		lbl_num.setLocation(w/2-lbl_num.getWidth()/2, h/2-lbl_num.getHeight()/2);
	}
	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
		lbl_num.setText(""+numero);
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
