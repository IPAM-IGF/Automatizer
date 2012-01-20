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

	public static boolean REQUIRES_USER = false;
	
	
	// Prise d'image simple d'une tranche
	public static void simpleAcquisition(String saveDir, String saveName, int time_for_one_image, GlandPanel gpanel){
		Point previousP = new Point();
		Point actualP;
		int moveX,moveY;
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
				doWait();
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
				doWait();
				// On focus la fenêtre d'acquisition
				try {
					CONTROLLER.focus("acquisition");
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
				
				// On prend l'image
				CONTROLLER.get("Demarrer").leftClick();
				try {
					Thread.sleep(time_for_one_image);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Ajustement des contrastes par l'utilisateur
				REQUIRES_USER = true;
				doWait();
				//Sauvegarde
				CONTROLLER.get("Save as").leftClick();
				try {
					CONTROLLER.focus("saveas");
				} catch (Exception e) {
					e.printStackTrace();
				}
				((ButtonTextItem)CONTROLLER.get("Save as")).setText(saveDir+"/"+saveName+"_I"+c.getNumero());
				((ButtonItem)CONTROLLER.get("OK")).leftClick();
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
	private static void doWait() {
		while(REQUIRES_USER){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	private static void moveMotor(int x, int y){
		//((ButtonTextItem)CONTROLLER.get("Y step")).setText("0");
		((ButtonTextItem)CONTROLLER.get("X step")).setText(""+x);
		((ButtonTextItem)CONTROLLER.get("Y step")).setText(""+y);
		CONTROLLER.get("Move +").leftClick();
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
