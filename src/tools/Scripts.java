package tools;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import control.ButtonItem;
import control.ButtonTextItem;
import control.Controller;
import display.panels.Case;
import display.panels.GlandPanel;

public class Scripts {
	public static Controller CONTROLLER;
	
	
	// Direction du parcours
	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int LEFT = 3;
	private static final int UP = 4;
	
	// Coordonnées relatif au point 1 du moteur (X,Y)
	private static int[] localisation = {0,0};
	// Point de référence (1)
	private static Point refPoint ;
	
	// Prise d'image simple d'une tranche
	public static void simpleAcquisition(GlandPanel gpanel){
		Point previousP = new Point();
		Point actualP;
		int moveX,moveY;
		int sens = RIGHT;
		HashMap<String, Case> listCases = gpanel.getListCases();
		HashMap<Integer, Point> coord = invertHashMap(gpanel.getCoordSpiral());
		List<Case> collection = new ArrayList<Case>(gpanel.getListCases().values());
		Collections.sort((List)collection);
		refPoint = coord.get(1);
		
		// fait une pause de 2 s avant de commencer
		// pour éviter les conflits avec l'utilisateur
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		
		for(Case c:collection){
			if(c.getState().equals(Case.SELECTED_STATE)){
				c.setState(Case.RUNNING_STATE);
				actualP = coord.get(c.getNumero());
				if(c.getNumero()!=1){
					// On repositionne le moteur
					try {
						CONTROLLER.focus("motor");
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(0);
					}
					moveX = ((actualP.x-refPoint.x)*Case.CASE_DIMENSION.width)-localisation[0];
					moveY = ((actualP.y-refPoint.y)*Case.CASE_DIMENSION.height)-localisation[1];
					localisation[0] = ((actualP.x-refPoint.x)*Case.CASE_DIMENSION.width);
					localisation[1] = ((actualP.y-refPoint.y)*Case.CASE_DIMENSION.height);
					moveMotor(moveX,moveY);
					
				}
				// On focus la fenêtre d'acquisition
				try {
					CONTROLLER.focus("acquisition");
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
				
				// On prend l'image
				CONTROLLER.get("Measure").leftClick();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				
				
				
				c.setState(Case.DONE_STATE);
				previousP = actualP;
			}
			
		}
		// On se replace dans la position d'origine
		moveMotor(-localisation[0],-localisation[1]);
		try {
			CONTROLLER.focus("acquisition");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	private static void moveMotor(int x, int y){
		((ButtonTextItem)CONTROLLER.get("X step")).setText(""+Math.abs(x));
		((ButtonTextItem)CONTROLLER.get("Y step")).setText(""+Math.abs(y));
		if(x>0)
			CONTROLLER.get("X ZoomIn").leftClick();
		else if(x<0)
			CONTROLLER.get("X ZoomOut").leftClick();
		if(y>0)
			CONTROLLER.get("Y ZoomIn").leftClick();
		else if(y<0)
			CONTROLLER.get("Y ZoomOut").leftClick();
	}
	
	public static Point getKeyForValue(int i, HashMap<Point, Integer> h){
		for(Point p:h.keySet())
			if(h.get(p) == i) return p;
		
		return null;
	}
	
	public static HashMap invertHashMap(HashMap h){
		HashMap loc = new HashMap<>();
		for(Object p:h.keySet())
			loc.put(h.get(p), p);
		return loc;
	}
	
}
