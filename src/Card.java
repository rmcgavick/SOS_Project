import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	private String name;
	private char suit;
	private char number;
	private int val;
	private boolean isFaceUp;
	private BufferedImage cardImage;
	
	public Card(String cardName) {	
		isFaceUp = false;
		name = cardName;
		try {
			if(isFaceUp) {
				cardImage = ImageIO.read(this.getClass().getResource("" + cardName + ".png"));
			} else {
				cardImage = ImageIO.read(this.getClass().getResource("cardBack.png"));
			}
		} catch (IOException e) {
			System.out.println("FAIL!!!!!!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		}
		suit = cardName.charAt(0);
		number = cardName.charAt(1);
		
		switch(number) {
			case '2': val = 2;
				break;
			case '3': val = 3;
				break;
			case '4': val = 4;
				break;
			case '5': val = 5;
				break;
			case '6': val = 6;
				break;
			case '7': val = 7;
				break;
			case '8': val = 8;
				break;
			case '9': val = 9;
				break;
			case 'T': val = 10;
				break;
			case 'J': val = 10;
				break;
			case 'Q': val = 10;
				break;
			case 'K': val = 10;
				break;
			case 'A': val = 11;
				break;
		}
	}
	public String getName() {
		return name;
	}
	public BufferedImage getCardImage() {
		return cardImage;
	}
	public char getSuit() {
		return suit;
	}
	public char getNumber() {
		return number;
	}
	public int getVal() {
		return val;
	}
	public boolean getFace() {
		return isFaceUp;
	}
	public void setVal(int v) {
		val = v;
	}
	public void setFace(boolean b) {
		isFaceUp = b;
		if(b) {
			try {
				cardImage = ImageIO.read(this.getClass().getResource("" + name + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!b) {
			try {
				cardImage = ImageIO.read(this.getClass().getResource("cardBack.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
