package control;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;


public class ButtonTextItem extends ButtonItem {

	public ButtonTextItem(Robot r, ArrayList<Point> p) {
		super(r, p);
	}

	public ButtonTextItem(Robot r, String n, ArrayList<Point> p) {
		super(r, n, p);
	}

	public ButtonTextItem(Robot r) {
		super(r);
	}
	
	public ButtonTextItem(Robot bot, String name, Point l) {
		super(bot);
		getPosition().add(l);
	}

	public void setText(String s){
		System.out.println(s);
		int[] keyCodes=stringToKeyCodes(s);
		leftClick();
		clearContent();
		for(int key:keyCodes){
			keyClick(key);
			System.out.println(key);
		}
	}
	
	public void clearContent() {
		leftClick();
		multiClick(new int[]{VK_CONTROL,VK_A});
		keyClick(VK_BACK_SPACE);
	}
	protected void keyClick(int key){
		// key original
		int okey = key;
		// > 20000 si c'est une majuscule
		if(key >= VK_0 && key <= VK_9 || VK_PERIOD==key || key > 20000)
			bot.keyPress(VK_SHIFT);
		if(key > 20000) key = key - 20000;
		// Si c'est un slash
		if(key == 10001){
			bot.keyPress(VK_SHIFT);
			key = VK_COLON;
		} 
		// Si c'est un anti slash
		if(okey == 10002){
			bot.keyPress(VK_CONTROL);
			bot.keyPress(VK_ALT);
			key = VK_8;
		}
		// Si c'est un underscore
		if(okey == VK_UNDERSCORE)
			key = VK_8;
		if(okey == VK_MINUS)
			key = VK_6;
		// Si c'est un point
		if(VK_PERIOD==key)
			key = VK_SEMICOLON;
		bot.keyPress(key);
		bot.delay(Controller.DELAY_PRESS_CLICK);
		bot.keyRelease(key);
		if(key >= VK_0 && key <= VK_9 || key == 10001 || VK_PERIOD == okey  || okey > 20000)
			bot.keyRelease(VK_SHIFT);
		if(okey == 10002){
			bot.keyRelease(VK_CONTROL);
			bot.keyRelease(VK_ALT);
		}
	}
	private void multiClick(int[] keycodes){
		for(int key:keycodes){
			bot.keyPress(key);
		}
		for(int key:keycodes){
			bot.keyRelease(key);
		}
	}
	
	public String toString(){
		String r = this.name+"::"+"ButtonTextItem::";
		String separator = ";";
		for(int i = 0; i<position.size();i++){
			if(i == position.size()-1) separator = "";
			r += this.position.get(i).x+"//"+this.position.get(i).y+separator;
		}
		return r;
	}
	public static int[] stringToKeyCodes(String s){
		int[] keycodes=new int[s.length()];
		for(int i=0;i<s.length();i++){
			switch (s.charAt(i)) {
	        case 'a':keycodes[i]=VK_A; break;
	        case 'b':keycodes[i]=VK_B; break;
	        case 'c':keycodes[i]=VK_C; break;
	        case 'd':keycodes[i]=VK_D; break;
	        case 'e':keycodes[i]=VK_E; break;
	        case 'f':keycodes[i]=VK_F; break;
	        case 'g':keycodes[i]=VK_G; break;
	        case 'h':keycodes[i]=VK_H; break;
	        case 'i':keycodes[i]=VK_I; break;
	        case 'j':keycodes[i]=VK_J; break;
	        case 'k':keycodes[i]=VK_K; break;
	        case 'l':keycodes[i]=VK_L; break;
	        case 'm':keycodes[i]=VK_M; break;
	        case 'n':keycodes[i]=VK_N; break;
	        case 'o':keycodes[i]=VK_O; break;
	        case 'p':keycodes[i]=VK_P; break;
	        case 'q':keycodes[i]=VK_Q; break;
	        case 'r':keycodes[i]=VK_R; break;
	        case 's':keycodes[i]=VK_S; break;
	        case 't':keycodes[i]=VK_T; break;
	        case 'u':keycodes[i]=VK_U; break;
	        case 'v':keycodes[i]=VK_V; break;
	        case 'w':keycodes[i]=VK_W; break;
	        case 'x':keycodes[i]=VK_X; break;
	        case 'y':keycodes[i]=VK_Y; break;
	        case 'z':keycodes[i]=VK_Z; break;
	        // Pour savoir si c'est une majuscule
	        case 'A': keycodes[i]=VK_A+20000; break;
	        case 'B': keycodes[i]=VK_B+20000; break;
	        case 'C': keycodes[i]=VK_C+20000; break;
	        case 'D': keycodes[i]=VK_D+20000; break;
	        case 'E': keycodes[i]=VK_E+20000; break;
	        case 'F': keycodes[i]=VK_F+20000; break;
	        case 'G': keycodes[i]=VK_G+20000; break;
	        case 'H': keycodes[i]=VK_H+20000; break;
	        case 'I': keycodes[i]=VK_I+20000; break;
	        case 'J': keycodes[i]=VK_J+20000; break;
	        case 'K': keycodes[i]=VK_K+20000; break;
	        case 'L': keycodes[i]=VK_L+20000; break;
	        case 'M': keycodes[i]=VK_M+20000; break;
	        case 'N': keycodes[i]=VK_N+20000; break;
	        case 'O': keycodes[i]=VK_O+20000; break;
	        case 'P': keycodes[i]=VK_P+20000; break;
	        case 'Q': keycodes[i]=VK_Q+20000; break;
	        case 'R': keycodes[i]=VK_R+20000; break;
	        case 'S': keycodes[i]=VK_S+20000; break;
	        case 'T': keycodes[i]=VK_T+20000; break;
	        case 'U': keycodes[i]=VK_U+20000; break;
	        case 'V': keycodes[i]=VK_V+20000; break;
	        case 'W': keycodes[i]=VK_W+20000; break;
	        case 'X': keycodes[i]=VK_X+20000; break;
	        case 'Y': keycodes[i]=VK_Y+20000; break;
	        case 'Z': keycodes[i]=VK_Z+20000; break;
	        case '`': keycodes[i]=VK_BACK_QUOTE; break;
	        case '0': keycodes[i]=VK_0; break;
	        case '1': keycodes[i]=VK_1; break;
	        case '2': keycodes[i]=VK_2; break;
	        case '3': keycodes[i]=VK_3; break;
	        case '4': keycodes[i]=VK_4; break;
	        case '5': keycodes[i]=VK_5; break;
	        case '6': keycodes[i]=VK_6; break;
	        case '7': keycodes[i]=VK_7; break;
	        case '8': keycodes[i]=VK_8; break;
	        case '9': keycodes[i]=VK_9; break;
	        case '-': keycodes[i]=VK_MINUS; break;
	        case '=': keycodes[i]=VK_EQUALS; break;
	        case '!': keycodes[i]=VK_EXCLAMATION_MARK; break;
	        case '@': keycodes[i]=VK_AT; break;
	        case '#': keycodes[i]=VK_NUMBER_SIGN; break;
	        case '$': keycodes[i]=VK_DOLLAR; break;
	        case '^': keycodes[i]=VK_CIRCUMFLEX; break;
	        case '&': keycodes[i]=VK_AMPERSAND; break;
	        case '*': keycodes[i]=VK_ASTERISK; break;
	        case '(': keycodes[i]=VK_LEFT_PARENTHESIS; break;
	        case ')': keycodes[i]=VK_RIGHT_PARENTHESIS; break;
	        case '_': keycodes[i]=VK_UNDERSCORE; break;
	        case '+': keycodes[i]=VK_PLUS; break;
	        case '\t': keycodes[i]=VK_TAB; break;
	        case '\n': keycodes[i]=VK_ENTER; break;
	        case '[': keycodes[i]=VK_OPEN_BRACKET; break;
	        case ']': keycodes[i]=VK_CLOSE_BRACKET; break;
	        case '\\': keycodes[i]=10002; break;
	        case ';': keycodes[i]=VK_SEMICOLON; break;
	        case ':': keycodes[i]=VK_COLON; break;
	        case '\'': keycodes[i]=VK_QUOTE;break;//VK_QUOTE
	        case '"': keycodes[i]=VK_QUOTEDBL; break;
	        case ',': keycodes[i]=VK_COMMA; break;
	        case '<': keycodes[i]=VK_LESS; break;
	        case '.': keycodes[i]=VK_PERIOD; break;
	        case '>': keycodes[i]=VK_GREATER; break;
	        case '/': keycodes[i]=10001; break;
	        case ' ': keycodes[i]=VK_SPACE; break;
	        default:
	                throw new IllegalArgumentException("Cannot type character " + s.charAt(i));
	        }
		}
		return keycodes;
	}
	public static void main(String args[]) throws AWTException{
		Robot bot = new Robot();
		System.out.println(stringToKeyCodes("_")[0]);
		ButtonTextItem b = new ButtonTextItem(bot);
		b.keyClick(stringToKeyCodes(".")[0]);
	}
	
}
