package control.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import display.TranslucentWindow;

public class KeyDetect implements KeyListener{

	private TranslucentWindow window;
	
	public KeyDetect(TranslucentWindow w){
		super();
		window=w;
	}
	
	public KeyDetect(){
		super();
	}
	
	
	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent k) {
		switch(k.getKeyCode()){
		case KeyEvent.VK_ESCAPE:
			window.dispose();break;
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}
	

}
