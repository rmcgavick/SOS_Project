import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DiscardPile extends JPanel{

	private Card[] discardPile;
	private int topOfPile;
	private int currentSize;
	private boolean isEmpty;
	private JLabel discardLabel;
	private BufferedImage discardImage;
	
	public DiscardPile(int size) {
		discardPile = new Card[size * 52];
		topOfPile = 0;
		currentSize = 0;
		isEmpty = true;
		try {
			discardImage = ImageIO.read(this.getClass().getResource("cardBack.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		discardLabel = new JLabel(new ImageIcon(discardImage));
		add(discardLabel);
	}
	
	public int getCurrentSize() {
		return currentSize;
	}
	
	public int getTopOfPile() {
		return topOfPile;
	}
	
	public void discardFromHand(Card card) {
		discardPile[topOfPile++] = card;
		currentSize++;
		if(isEmpty)
			updateDiscardImage();
	}
	
	public Card drawFromDiscardPile() {
		currentSize--;
		return discardPile[topOfPile--];
	}
	
	public void updateDiscardImage() {
		isEmpty = false;
		if(currentSize == 0) {
			return;
		} else if(currentSize < 5) { 
			try {
				discardImage = ImageIO.read(this.getClass().getResource("Deck5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		discardLabel.setIcon(new ImageIcon(discardImage));
		discardLabel.revalidate();
		add(discardLabel);
	}
	
}
