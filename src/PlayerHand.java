
public class PlayerHand extends Hand {

	public PlayerHand(Deck deck, String gameType) {
		super(deck, gameType, true);
		// player hand starts with all cards face up
		//for(int i = 0; i < super.getCurrentSize(); i++) {
		//	super.flipCardAtIndex(i);
		//}
	}

}
