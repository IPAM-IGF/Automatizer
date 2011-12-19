package control;

import java.awt.Point;
import java.util.ArrayList;

public interface ControllerItem {
	public void rightClick();
	public void leftClick();
	public void middleClick();
	public ArrayList<Point> getPosition();
	public void setPosition(ArrayList<Point> p);
}
