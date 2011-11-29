package display.panels;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class Case extends JPanel implements Comparable<Case>{
	
	/**
	 * Les paramètres lié au moteur (dimension du bloc
	 */
	public static final Dimension CASE_DIMENSION = new Dimension(5000,4000);
	
	
	private static final Dimension DEFAULT_SIZE=new Dimension(40,40);
	public static final String DONE_STATE = "done";
	public static final String RUNNING_STATE = "running";
	public static final String SELECTED_STATE = "selected";
	public static final String DEFAULT_STATE = "none";
	
	// Couleur de la case
	public static final Color DEFAULT_BG = null;
	public static final Color SELECTED_BG = Color.GREEN;
	public static final Color MOUSEOVER_BG = Color.WHITE;
	public static final Color RUNNING_BG = Color.CYAN;
	public static final Color DONE_BG = Color.CYAN;
	
	// Numéro du bloc
	private int numero = -1;
	private JLabel lbl_num;
	
	// Selection du bloc
	private boolean selected=false;
	
	// Etat du bloc (en attente, en cours etc)
	private String state = "none";
	
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
		if(this.selected){
			setState(SELECTED_STATE);
		}else{
			setState(DEFAULT_STATE);
		}
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		switch(state){
		case Case.DEFAULT_STATE:
			setBackground(Case.DEFAULT_BG);break;
		case Case.SELECTED_STATE:
			setBackground(Case.SELECTED_BG);break;
		case Case.RUNNING_STATE:
			setBackground(Case.RUNNING_BG);break;
		case Case.DONE_STATE:
			setBackground(Case.DONE_BG);break;
			default:
				System.out.println("Unknow state");
				System.exit(0);
		}
		this.state = state;
	}
	
	public int compareTo(Case c) {
	    return this.numero>c.getNumero() ? 1 : -1;
	  }
}
