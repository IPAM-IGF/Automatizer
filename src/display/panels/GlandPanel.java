package display.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;

import display.panels.listeners.MouseCase;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;

public class GlandPanel extends JPanel{

	// dimension de la grille dans laquelle est contenu la glande (X,Y)
	private static final Dimension grilleGlande=new Dimension(20,10);
	
	// Collection des cases
	private HashMap<String, Case> listCases;
	private HashMap<Point,Integer> coordSpiral;
	
	
	public GlandPanel() {
		super();
		listCases=new HashMap<String, Case>();
		Dimension panSize = this.getSize();
        float cellWidth = panSize.width/grilleGlande.width;
        float cellHeight = panSize.height/grilleGlande.height;
        
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		int maxG=Math.max(grilleGlande.width, grilleGlande.height);
		Spiral sp=new Spiral(maxG,maxG, grilleGlande.width/2, grilleGlande.height/2);
		coordSpiral=sp.spiral();
		
		int count=0;
		for(int i=0;i<grilleGlande.width;i++){
        	for(int j=0;j<grilleGlande.height;j++){
        		Case c=new Case(Math.round(cellWidth), Math.round(cellHeight),coordSpiral.get(new Point(i,j)));
        		c.addMouseListener(new MouseCase());
        		GridBagConstraints gbc_c = new GridBagConstraints();
        		gbc_c.gridx = i;
        		gbc_c.gridy = j;
        		add(c, gbc_c);
        		listCases.put(""+(count++), c);
        	}
        }
		
		
	}

	public GlandPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public GlandPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public GlandPanel(LayoutManager layout) {
		super(layout);

	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension panSize = this.getSize();
        float cellWidth = panSize.width/grilleGlande.width;
        float cellHeight = panSize.height/grilleGlande.height;
        int count=0;
        for(int i=0;i<grilleGlande.width;i++){
        	for(int j=0;j<grilleGlande.height;j++){
        		listCases.get(""+(count)).setSize(Math.round(cellWidth), Math.round(cellHeight));
        		listCases.get(""+(count++)).setLocation((int)Math.round(i*cellWidth)+5, (int)Math.round(j*cellHeight));
        	}
        }

    }
	
	


}
