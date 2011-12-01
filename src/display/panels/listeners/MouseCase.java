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
	private static boolean mouseOverOnly = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(isMouseOverOnly()) return;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(multiSelect && !((Case)e.getComponent()).isSelected()
				&& activeMousePressed==MouseEvent.BUTTON1){
			((Case)e.getComponent()).setSelected(true);
		}else{
			if(multiSelect && ((Case)e.getComponent()).isSelected()
					&& activeMousePressed==MouseEvent.BUTTON3){
				((Case)e.getComponent()).setSelected(false);
			}else{
				e.getComponent().setBackground(Case.MOUSEOVER_BG);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		((Case)e.getComponent()).setBackgroundOfState();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(isMouseOverOnly()) return;
		multiSelect=true;
		activeMousePressed=e.getButton();
		switch(e.getButton()){
		case MouseEvent.BUTTON1:
			((Case)e.getComponent()).setSelected(true);
			break;
		case MouseEvent.BUTTON3:
			((Case)e.getComponent()).setSelected(false);
			break;
		}
			
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		multiSelect=false;
		activeMousePressed=-1;
		if(isMouseOverOnly()) return;
	}

	public static boolean isMouseOverOnly() {
		return mouseOverOnly;
	}

	public static void setMouseOverOnly(boolean mouseOverOnly) {
		MouseCase.mouseOverOnly = mouseOverOnly;
	}

	

}
