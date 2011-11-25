package display.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class GlandPanel extends JPanel{

	// dimension de la grille dans laquelle est contenu la glande (X,Y)
	private static final Dimension grilleGlande=new Dimension(20,10);
	
	
	public GlandPanel() {
		super();
		
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
        for(int i=0;i<grilleGlande.width;i++){
        	for(int j=0;j<grilleGlande.height;j++){
        		g.drawRect((int)Math.round(i*cellWidth)+1, (int)Math.round(j*cellHeight), (int)Math.round(cellWidth), (int)Math.round(cellHeight));
        	}
        }

    }


}
