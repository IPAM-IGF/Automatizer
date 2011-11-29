package control;

import java.awt.Point;
import java.awt.Robot;

import static java.awt.event.KeyEvent.*;


public class ButtonTextItem extends ButtonItem {

	public ButtonTextItem(Robot r, Point p) {
		super(r, p);
	}

	public ButtonTextItem(Robot r, String n, Point p) {
		super(r, n, p);
	}

	public ButtonTextItem(Robot r) {
		super(r);
	}
	
	public void setText(String s){
		int[] keyCodes=stringToKeyCodes(s);
		leftClick();
		clearContent();
		for(int key:keyCodes){
			keyClick(key);
			System.out.println(key);
		}
	}
	
	private void clearContent() {
		leftClick();
		multiClick(new int[]{VK_CONTROL,VK_A});
		keyClick(VK_BACK_SPACE);
	}
	private void keyClick(int key){
		if(key >= VK_0 && key <= VK_9)
			bot.keyPress(VK_SHIFT);
		bot.keyPress(key);
		bot.delay(100);
		bot.keyRelease(key);
		if(key >= VK_0 && key <= VK_9)
			bot.keyRelease(VK_SHIFT);
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
		return this.name+"::"+"ButtonTextItem::"+this.position.x+"//"+this.position.y;
	}
	public static int[] stringToKeyCodes(String s){
		int[] keycodes=new int[s.length()];
		for(int i=0;i<s.length();i++){
			switch (s.charAt(i)) {
	        case 'a': keycodes[i]=VK_A; break;
	        case 'b': keycodes[i]=VK_B; break;
	        case 'c': keycodes[i]=VK_C; break;
	        case 'd': keycodes[i]=VK_D; break;
	        case 'e': keycodes[i]=VK_E; break;
	        case 'f': keycodes[i]=VK_F; break;
	        case 'g': keycodes[i]=VK_G; break;
	        case 'h': keycodes[i]=VK_H; break;
	        case 'i': keycodes[i]=VK_I; break;
	        case 'j': keycodes[i]=VK_J; break;
	        case 'k': keycodes[i]=VK_K; break;
	        case 'l': keycodes[i]=VK_L; break;
	        case 'm': keycodes[i]=VK_M; break;
	        case 'n': keycodes[i]=VK_N; break;
	        case 'o': keycodes[i]=VK_O; break;
	        case 'p': keycodes[i]=VK_P; break;
	        case 'q': keycodes[i]=VK_Q; break;
	        case 'r': keycodes[i]=VK_R; break;
	        case 's': keycodes[i]=VK_S; break;
	        case 't': keycodes[i]=VK_T; break;
	        case 'u': keycodes[i]=VK_U; break;
	        case 'v': keycodes[i]=VK_V; break;
	        case 'w': keycodes[i]=VK_W; break;
	        case 'x': keycodes[i]=VK_X; break;
	        case 'y': keycodes[i]=VK_Y; break;
	        case 'z': keycodes[i]=VK_Z; break;
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
	        case '\\': keycodes[i]=VK_BACK_SLASH; break;
	        case ';': keycodes[i]=VK_SEMICOLON; break;
	        case ':': keycodes[i]=VK_COLON; break;
	        case '\'': keycodes[i]=VK_QUOTE; break;
	        case '"': keycodes[i]=VK_QUOTEDBL; break;
	        case ',': keycodes[i]=VK_COMMA; break;
	        case '<': keycodes[i]=VK_LESS; break;
	        case '.': keycodes[i]=VK_PERIOD; break;
	        case '>': keycodes[i]=VK_GREATER; break;
	        case '/': keycodes[i]=VK_SLASH; break;
	        case ' ': keycodes[i]=VK_SPACE; break;
	        default:
	                throw new IllegalArgumentException("Cannot type character " + s.charAt(i));
	        }
		}
		return keycodes;
	}
	
}
