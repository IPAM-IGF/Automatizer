package control;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import display.TranslucentWindow;

public class ButtonItem implements ControllerItem{

	// Les clics de la souris
	protected static final int LEFT_C = InputEvent.BUTTON1_MASK;
	protected static final int MIDDLE_C = InputEvent.BUTTON2_MASK;
	protected static final int RIGHT_C = InputEvent.BUTTON3_MASK;
	
	
	protected ArrayList<Point> position;
	protected Robot bot;
	protected String name;
	
	public ButtonItem(){
		position=new ArrayList<Point>();
		name="Unknow";
	}
	
	public ButtonItem(Robot r){
		position=new ArrayList<Point>();
		bot=r;
		name="Unknow";
	}
	
	public ButtonItem(Robot r,ArrayList<Point> p){
		position=p;
		bot=r;
		name="Unknow";
	}
	
	public ButtonItem(Robot r,String n, ArrayList<Point> p){
		position=p;
		bot=r;
		name=n;
	}
	
	public ButtonItem(Robot bot2, String n, Point location) {
		this(bot2);
		name=n;
		addPosition(location);
	}

	@Override
	public void rightClick(){
		for(int i=0; i < position.size(); i ++){
			if(i!=0) bot.delay(Controller.DELAY_PRESS_CLICK);
			bot.mouseMove(position.get(i).x, position.get(i).y);
			bot.mousePress(RIGHT_C);
			bot.delay(Controller.DELAY_PRESS_CLICK);
			bot.mouseRelease(RIGHT_C);
		}
	}

	@Override
	public void leftClick(){
		for(int i=0; i < position.size(); i ++){
			if(i!=0) bot.delay(Controller.DELAY_PRESS_CLICK);
			bot.mouseMove(position.get(i).x, position.get(i).y);
			bot.mousePress(LEFT_C);
			bot.delay(Controller.DELAY_PRESS_CLICK);
			bot.mouseRelease(LEFT_C);
		}
	}

	@Override
	public void middleClick(){
		for(int i=0; i < position.size(); i ++){
			if(i!=0) bot.delay(Controller.DELAY_PRESS_CLICK);
			bot.mouseMove(position.get(i).x, position.get(i).y);
			bot.mousePress(MIDDLE_C);
			bot.delay(Controller.DELAY_PRESS_CLICK);
			bot.mouseRelease(MIDDLE_C);
		}
		
	}

	@Override
	public ArrayList<Point> getPosition() {
		return position;
	}

	@Override
	public void setPosition(ArrayList<Point> p) {
		position=p;
	}
	public String toString(){
		String r = this.name+"::"+"ButtonItem::";
		String separator = ";";
		for(int i = 0; i<position.size();i++){
			if(i == position.size()-1) separator = "";
			r += this.position.get(i).x+"//"+this.position.get(i).y+separator;
		}
		return r;
	}

	public void reset() {
		position.clear();
	}

	public void addPosition(Point location) {
		position.add(location);
	}
}
