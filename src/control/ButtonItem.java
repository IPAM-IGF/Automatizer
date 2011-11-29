package control;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import display.TranslucentWindow;

public class ButtonItem implements ControllerItem{

	// Les clics de la souris
	protected static final int LEFT_C = InputEvent.BUTTON1_MASK;
	protected static final int MIDDLE_C = InputEvent.BUTTON2_MASK;
	protected static final int RIGHT_C = InputEvent.BUTTON3_MASK;
	
	
	protected Point position;
	protected Robot bot;
	protected String name;
	
	public ButtonItem(){
		position=new Point();
		name="Unknow";
	}
	
	public ButtonItem(Robot r){
		position=new Point();
		bot=r;
		name="Unknow";
	}
	
	public ButtonItem(Robot r,Point p){
		position=p;
		bot=r;
		name="Unknow";
	}
	
	public ButtonItem(Robot r,String n, Point p){
		position=p;
		bot=r;
		name=n;
	}
	
	@Override
	public void rightClick(){
		bot.mouseMove(position.x, position.y);
		bot.mousePress(RIGHT_C);
		bot.mouseRelease(RIGHT_C);
	}

	@Override
	public void leftClick(){
	    bot.mouseMove(position.x, position.y);
		bot.mousePress(LEFT_C);
		bot.delay(100);
		bot.mouseRelease(LEFT_C);
	}

	@Override
	public void middleClick(){
		bot.mouseMove(position.x, position.y);
		bot.mousePress(MIDDLE_C);
		bot.mouseRelease(MIDDLE_C);
		
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point p) {
		position=p;
	}
	public String toString(){
		return this.name+"::"+"ButtonItem::"+this.position.x+"//"+this.position.y;
	}
}
