package Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import display.TranslucentWindow;


// Detect Mouse actions on the transparent window 
public class MouseDetect implements MouseListener {

	private TranslucentWindow window;
	
	public MouseDetect(TranslucentWindow w){
		super();
		window=w;
	}
	
	public MouseDetect(){
		super();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		window.setTempXY(e.getLocationOnScreen());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
