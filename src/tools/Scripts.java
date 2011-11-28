package tools;

import java.awt.Point;
import java.util.HashMap;

import control.ButtonItem;
import control.ButtonTextItem;
import control.Controller;
import display.panels.Case;
import display.panels.GlandPanel;

public class Scripts {
	public static Controller CONTROLLER;
	
	
	// Prise d'image simple d'une tranche
	public static void simpleAcquisition(GlandPanel gpanel){
		HashMap<String, Case> listCases = gpanel.getListCases();
		HashMap<Point,Integer> coord = gpanel.getCoordSpiral();
		
		for(String name:listCases.keySet()){
			// Au début le micro est positionné sur la glande en position 1
			// On prend donc l'image
			try {
				CONTROLLER.focus("acquisition");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			
			
			
			// On repositionne le moteur
			try {
				CONTROLLER.focus("motor");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			((ButtonTextItem)CONTROLLER.get("X step")).setText(""+Case.CASE_DIMENSION.width);
			((ButtonTextItem)CONTROLLER.get("Y step")).setText(""+Case.CASE_DIMENSION.height);
			CONTROLLER.get("move").leftClick();
			
			
			
		}
		
	}
}
