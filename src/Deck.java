import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Deck extends JPanel{

	private int arrayOfCards[];
	private String cardName;
	private int deckSize;
	private boolean isEmpty;
	private JLabel deckLabel;
	private BufferedImage deckImage;
	
	public Deck(int numDecks) {
		deckSize = 52 * numDecks;
		arrayOfCards = new int[52];
		isEmpty = false;
		for(int i = 0; i < 52; i++) {
			arrayOfCards[i] = numDecks;
		}
		try {
			deckImage = ImageIO.read(this.getClass().getResource("Deck5.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		deckLabel = new JLabel(new ImageIcon(deckImage));
		add(deckLabel);
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public void setIsEmpty(boolean b) {
		isEmpty = b;
	}
	public int getDeckSize() {
		return deckSize;
	}
	
	public String printCardAtIndex(int index) {
		return ("" + CardsEnum.values()[index]);
	}
	
	// the drawCard method returns a random card still in the deck
	public Card drawCard() {
		int min = 0;
		int max = 51;
		int rnd = (min + (int)(Math.random() * ((max - min) + 1)));

		while(arrayOfCards[rnd] == 0) {
			rnd = (min + (int)(Math.random() * ((max - min) + 1)));
		}

		cardName = "" + CardsEnum.values()[rnd];
		Card card = new Card(cardName);
		arrayOfCards[rnd] = arrayOfCards[rnd] - 1;
		deckSize--;
		if(deckSize < 5) {
			deckLabel.setIcon(new ImageIcon("Deck"+ deckSize + ".png"));
			deckLabel.revalidate();
			add(deckLabel);
		}
		return card;
	}
	
	public boolean isCardAtIndex(int index) {
		if(arrayOfCards[index] == 0)
			return false;
		else
			return true;
	}
}