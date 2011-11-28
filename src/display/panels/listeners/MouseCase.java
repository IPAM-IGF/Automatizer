package display.panels.listeners;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import display.panels.Case;

public class MouseCase implements MouseListener{

	// Si on maintient le clic pour en sélectionner plusieurs
	private static boolean multiSelect=false;
	private static int activeMousePressed=-1;
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(multiSelect && !((Case)e.getComponent()).isSelected()
				&& activeMousePressed==MouseEvent.BUTTON1){
			((Case)e.getComponent()).setSelected(true);
			e.getComponent().setBackground(Case.SELECTED_BG);
		}else{
			if(multiSelect && ((Case)e.getComponent()).isSelected()
					&& activeMousePressed==MouseEvent.BUTTON3){
				((Case)e.getComponent()).setSelected(false);
				e.getComponent().setBackground(Case.DEFAULT_BG);
			}else{
				e.getComponent().setBackground(Case.MOUSEOVER_BG);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(((Case)e.getComponent()).isSelected()) 
			e.getComponent().setBackground(Case.SELECTED_BG);
		else
			e.getComponent().setBackground(Case.DEFAULT_BG);			
	}

	@Override
	public void mousePressed(MouseEvent e) {
		multiSelect=true;
		activeMousePressed=e.getButton();
		switch(e.getButton()){
		case MouseEvent.BUTTON1:
			((Case)e.getComponent()).setSelected(true);
			e.getComponent().setBackground(Case.SELECTED_BG);break;
		case MouseEvent.BUTTON3:
			((Case)e.getComponent()).setSelected(false);
			e.getComponent().setBackground(Case.DEFAULT_BG);break;
		}
			
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		multiSelect=false;
		activeMousePressed=-1;
	}

}
