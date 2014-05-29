
public class OpponentHand extends Hand {
	
	public OpponentHand(Deck deck, String gameType) {
		super(deck, gameType, false);
	}

	public int calcHandValue() {
		int val = 0;
		
		for(int i = 0; i < super.getCurrentSize(); i++) {
			
		}
		
		return val;
	}

	//public draw
}
