import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Hand extends JPanel{
	private int minHandSize;
	private int maxHandSize;
	private int currentSize;
	private int handVal;
	private boolean handHasAce;
	private Card[] hand;
	private JLabel[] cardImages;
	
	public Hand(Deck deck, String gameType, boolean isPlayerHand) {
		if(gameType.equals("BlackJack")) {
			minHandSize = 2;
			maxHandSize = 13;
			currentSize = 2;
			handVal = 0;
			handHasAce = false;
			hand = new Card[maxHandSize];
			cardImages = new JLabel[maxHandSize];
			
			for(int i = 0; i < currentSize; i++) {
				hand[i] = deck.drawCard();
				handVal += hand[i].getVal();
				if(hand[i].getVal() == 11) {
					handHasAce = true;
				}
				if(handHasAce && (handVal > 21)) 
					hand = handleAces(hand);
				cardImages[i] = new JLabel(new ImageIcon(hand[i].getCardImage()));
				add(cardImages[i]);
				if(isPlayerHand || i == 1) 
					flipCardAtIndex(i);
			}
		}
		if(gameType.equals("13")){
			
		}
	}
	public int getCurrentSize() {
		return currentSize;
	}
	public int getMinHandSize() {
		return minHandSize;
	}
	public int getMaxHandSize() {
		return maxHandSize;
	}
	public void setCurrentSize(int size) {
		currentSize = size;
	}
	public void setHandHasAce(boolean b) {
		handHasAce = b;
	}
	public int getHandValue() {
		return handVal;
	}
	public String printCardAtIndex(int index) {
		return hand[index].getName();
	}
	public int drawFromDeck(Deck deck, int numCards, boolean isPlayerHand) {
		int i;
		for(i = currentSize; i < currentSize + numCards; i++) {
			// instead of if/else, needs to be in a try/catch block to make 
			// sure deck isn't empty before drawing
			// this will be the same as the DeckIsEmptyException in Deck.java
			if(currentSize + numCards <= maxHandSize) {
				hand[i] = deck.drawCard();
				cardImages[i] = new JLabel(new ImageIcon(hand[i].getCardImage()));
				if(isPlayerHand || currentSize > 1)
					flipCardAtIndex(i);
				add(cardImages[i]);
				handVal += hand[i].getVal();
				if(hand[i].getVal() == 11) {
					handHasAce = true;
				}
				if(handHasAce && handVal > 21) {
					hand = handleAces(hand);
				}
			} else {
				System.out.println("error: trying to draw too many cards");
				return -1;
			}
		}
		currentSize += numCards;
		if(isPlayerHand) { // playerHand
			if(handVal < 21)
				return 0; // player stays under 21
			else if(handVal == 21)
				return 1; // player stays at 21
			else
				return 2; // player busts
		} else { // opponentHand
			if(handVal < 21)
				return 0;
			else if(handVal == 21)
				return 2; // dealer wins
			else 
				return 1; // player wins
		}
			
	}
	
	public Card[] handleAces(Card[] h) {
		for(int i = 0; i <= currentSize; i++) {
			if(h[i].getVal() == 11) {
				h[i].setVal(1);
				handVal -= 10;
				return h;
			}
		}
		setHandHasAce(false);
		return h;
	}
	
	public void discardAllFromHand(DiscardPile d) {
		for(int i = currentSize; i >= 0; i--) {
			d.discardFromHand(hand[i]);
			hand[i] = null;
			cardImages[i] = null;
		}
		handVal = 0;
		currentSize = 0;
		}
	
	public void discardFromHand(DiscardPile d, int numCards) {
		for(int i = currentSize; i > currentSize - numCards; i--) {
			d.discardFromHand(hand[i]);
			handVal = handVal-hand[i].getVal();
			hand[i] = null;
			cardImages[i] = null;
			currentSize--;
		}
	}
	
	public void emptyHand() {
		hand = new Card[maxHandSize];
		cardImages = new JLabel[maxHandSize];
		currentSize = 0;
		handVal = 0;
	}
	
	public void flipCardAtIndex(int index) {
		if(hand[index].getFace() == true) {
			hand[index].setFace(false);
			cardImages[index].setIcon(new ImageIcon(hand[index].getCardImage()));
			cardImages[index].revalidate();
			add(cardImages[index]);
		} else {
			hand[index].setFace(true);
			cardImages[index].setIcon(new ImageIcon(hand[index].getCardImage()));
			cardImages[index].revalidate();
			add(cardImages[index]);
		}
	}
}
