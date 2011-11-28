package control;

import java.awt.Point;

public interface ControllerItem {
	public void rightClick();
	public void leftClick();
	public void middleClick();
	public Point getPosition();
	public void setPosition(Point p);
}
