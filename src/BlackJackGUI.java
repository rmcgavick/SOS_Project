import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.Timer;

public class BlackJackGUI {
	private static int numDecks = 1;
	private static int playerScore;
	private static int opponentScore;
	private static int playerWinOrLose; // 0 = game still in progress, 1 = player wins hand, 2 = player busts
	private static boolean game;
	private static int blackjack;
	private Timer dealerTimer;
	private ActionListener pause;
	
	private JFrame Table;
	private static Deck DeckPanel = new Deck(numDecks);
	private static PlayerHand PlayerHandPanel = new PlayerHand(DeckPanel, "BlackJack");
	private static OpponentHand OpponentHandPanel = new OpponentHand(DeckPanel, "BlackJack");
	private static DiscardPile DiscardPanel = new DiscardPile(numDecks);
	
	private static JButton btnHit = new JButton("Hit");
	private static JButton btnStay = new JButton("Stay");
	private static JButton btnNewGame = new JButton("New Game");
	private static JButton btnNewHand = new JButton("New Hand");
	private static JLabel lblPlayerScore = new JLabel("");
	private static JLabel lblOpponentScore = new JLabel("");
	private static JLabel lblOpponentHandVal = new JLabel("");
	private static JLabel lblPlayerHandVal = new JLabel("");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				BlackJackGUI window;
				try {
					window = new BlackJackGUI();
					window.Table.setVisible(true);
/*					while(game) {	// This is the main game loop
						System.out.println("game loop entered");
						if(playerWinOrLose == 1) {
							playerScore++;
						}
						else if(playerWinOrLose == 2) {
							opponentScore++;
						}
						window.Table.revalidate();
						window.Table.repaint();
						
					}   */
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BlackJackGUI() {
		initialize();
	}
	
	public int checkForBlackjack(Hand playerHand, Hand opponentHand, boolean onDeal) {
		// this needs to go in the initializer, in the newGame and newHand methods, and the hit methods
		// 0 = no blackjack, 1 = blackjack tie, 2 = player blackjack, 3 = dealer blackjack
		if(playerHand.getHandValue() != 21 && opponentHand.getHandValue() != 21)
			return 0;
		else if(onDeal) {
			if(playerHand.getHandValue() == 21) {
				if(opponentHand.getHandValue() == 21)
					return 1;
				else
					return 2;
			}
		} else {
			if(playerHand.getHandValue() == 21) {
				if(opponentHand.getHandValue() == 21)
					return 1;
				else
					return 2;
			}
		}
		return 3;
	}
	
	public void newHand() {
		
		if(DeckPanel.getDeckSize() >= 26) {
			PlayerHandPanel.discardAllFromHand(DiscardPanel);
			OpponentHandPanel.discardAllFromHand(DiscardPanel);
		} else {
			DeckPanel.removeAll();
			DeckPanel.revalidate();
			DeckPanel = new Deck(numDecks);
			DeckPanel.repaint();
			DiscardPanel.removeAll();
			DiscardPanel.revalidate();
			DiscardPanel = new DiscardPile(numDecks);
			DiscardPanel.repaint();
		}
		
		PlayerHandPanel.removeAll();
		PlayerHandPanel.revalidate();
		PlayerHandPanel.emptyHand();
		playerWinOrLose = PlayerHandPanel.drawFromDeck(DeckPanel, 2, true);
		lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue());
		lblPlayerScore.setText("Player games won: " + playerScore);
		PlayerHandPanel.repaint();
		
		OpponentHandPanel.removeAll();
		OpponentHandPanel.revalidate();
		OpponentHandPanel.emptyHand();
		playerWinOrLose = OpponentHandPanel.drawFromDeck(DeckPanel, 1, false);
		OpponentHandPanel.drawFromDeck(DeckPanel, 1, true);
		lblOpponentHandVal.setText("" + OpponentHandPanel.getHandValue());
		lblOpponentScore.setText("Dealer games won: " + opponentScore);
		OpponentHandPanel.repaint();
		
		btnHit.setEnabled(true);
		btnStay.setEnabled(true);
		
		blackjack = checkForBlackjack(PlayerHandPanel, OpponentHandPanel, false);
		switch(blackjack) {
			case 1: 
				tie();
				lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Push");
				break;
			case 2: 
				playerWin();
				lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Blackjack!");
				break;
			case 3: 
				dealerWin();
				lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Dealer Blackjack!");
				break;
			default:
				break;	
		}
		return; 
	}
	
	public void newGame() {
		//game = true;
		playerScore = 0;
		opponentScore = 0;
		playerWinOrLose = 0;
		DeckPanel.removeAll();
		DeckPanel.revalidate();
		DeckPanel = new Deck(numDecks);
		DeckPanel.repaint();
		
		PlayerHandPanel.removeAll();
		PlayerHandPanel.revalidate();
		PlayerHandPanel.emptyHand();
		playerWinOrLose = PlayerHandPanel.drawFromDeck(DeckPanel, 2, true);
		lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue());
		lblPlayerScore.setText("Player games won: " + playerScore);
		PlayerHandPanel.repaint();
		
		OpponentHandPanel.removeAll();
		OpponentHandPanel.revalidate();
		OpponentHandPanel.emptyHand();
		playerWinOrLose = OpponentHandPanel.drawFromDeck(DeckPanel, 1, false);
		OpponentHandPanel.drawFromDeck(DeckPanel, 1, true);
		lblOpponentHandVal.setText("" + OpponentHandPanel.getHandValue());
		lblOpponentScore.setText("Dealer games won: " + opponentScore);
		OpponentHandPanel.repaint();
		
		btnHit.setEnabled(true);
		btnStay.setEnabled(true);
		
		blackjack = checkForBlackjack(PlayerHandPanel, OpponentHandPanel, false);
		switch(blackjack) {
			case 1: 
				tie();
				lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Push");
				break;
			case 2: 
				playerWin();
				lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Blackjack!");
				break;
			case 3: 
				dealerWin();
				lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Dealer Blackjack!");
				break;
			default:
				break;	
		}
	}
	
	public int initiateOpponentTurn(Hand opponentHand, Hand playerHand) {
		int playerHandVal = playerHand.getHandValue();
		int opponentHandVal = opponentHand.getHandValue();
		int opponentWinOrLose = 0;
		
		while(opponentHandVal <= 17) {
			try {
				OpponentHandPanel.paintImmediately(0, 0, 1024, 109);
				System.out.println("pause in 'dealerTurn()' method");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			opponentWinOrLose = opponentHand.drawFromDeck(DeckPanel, 1, false);
			opponentHandVal = opponentHand.getHandValue();
		}
		if(playerHandVal > opponentHandVal)
			opponentWinOrLose = 1;
		return opponentWinOrLose;
	}
	
	public void playerWin() {
		btnHit.setEnabled(false);
		btnStay.setEnabled(false);
		playerScore++;
		lblPlayerScore.setText("Player games won: " + playerScore);
		lblOpponentHandVal.setText("" + OpponentHandPanel.getHandValue());
		if(PlayerHandPanel.getHandValue() == 21)
			lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Blackjack!");
		else
			lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Player Wins.");
		return;
	}
	
	public void playerStay() {
		/*try {
			Thread.sleep(1000);
			System.out.println("pause in 'PlayerStay()' method");
			OpponentHandPanel.paintImmediately(0, 0, 1024, 109);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		OpponentHandPanel.flipCardAtIndex(0);
		btnHit.setEnabled(false);
		btnStay.setEnabled(false);
		if((playerWinOrLose = initiateOpponentTurn(OpponentHandPanel, PlayerHandPanel)) == 1) {
			playerWin();
		}
		else if(PlayerHandPanel.getHandValue() < OpponentHandPanel.getHandValue()) {
			dealerWin();
		}
		else {
			tie();
		}
	}
	
	public void dealerWin() {
		btnHit.setEnabled(false);
		btnStay.setEnabled(false);
		opponentScore++;
		lblOpponentScore.setText("Dealer games won: " + opponentScore);
		lblOpponentHandVal.setText("" + OpponentHandPanel.getHandValue());
		if(OpponentHandPanel.getHandValue() == 21)
			lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Dealer Blackjack");
		else
			lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Dealer Wins.");
		return;
	}
	
	public void tie() {
		btnHit.setEnabled(false);
		btnStay.setEnabled(false);
		lblOpponentHandVal.setText("" + OpponentHandPanel.getHandValue());
		lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Push");
		return;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		game = true;
		dealerTimer = new Timer(1000, pause);
		pause = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pause");
			}
		};
		Table = new JFrame();
		Table.setTitle("Blackjack");
		Table.getContentPane().setBackground(new Color(0, 128, 0));
		Table.getContentPane().setLayout(null);
		playerScore = 0;
		opponentScore = 0;
		playerWinOrLose = 0;
		

		btnHit.setBounds(622, 345, 117, 29);			
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((playerWinOrLose = PlayerHandPanel.drawFromDeck(DeckPanel, 1, true)) == 2) {
					dealerWin();
					lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Bust!");	
				} else {
					blackjack = checkForBlackjack(PlayerHandPanel, OpponentHandPanel, false);
					switch(blackjack) {
						case 1: 
							// " -- Push"
							tie();
							break;
						case 2: 
							//lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Blackjack!");
							playerStay();
							break;
						case 3: 
							//lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Dealer Blackjack!");
							dealerWin();
							break;
						default:
							lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue());
							break;	
					}
				PlayerHandPanel.revalidate();
				}
			}
		});
		Table.getContentPane().add(btnHit);
		
		btnStay.setBounds(622, 380, 117, 29);
		btnStay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerStay();
			}
		});
		Table.getContentPane().add(btnStay);

		btnNewGame.setBounds(272, 169, 117, 29);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		Table.getContentPane().add(btnNewGame);
		
		btnNewHand.setBounds(272, 200, 117, 29);
		btnNewHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newHand();
			}
		});
		Table.getContentPane().add(btnNewHand);
		
		DeckPanel.setBackground(new Color(0, 128, 0));
		DeckPanel.setBounds(401, 345, 209, 128);
		DeckPanel.setPreferredSize(new Dimension(150, 150));	
		Table.getContentPane().add(DeckPanel);

		DiscardPanel.setBackground(new Color(0, 128, 0));
		DiscardPanel.setBounds(401, 169, 209, 128);
		DiscardPanel.setPreferredSize(new Dimension(150, 150));
		Table.getContentPane().add(DiscardPanel);

		OpponentHandPanel.setBackground(new Color(0, 128, 0));
		OpponentHandPanel.setBounds(0, 0, 1024, 109);
		OpponentHandPanel.setPreferredSize(new Dimension(150, 150));
		OpponentHandPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Table.getContentPane().add(OpponentHandPanel);
		
		PlayerHandPanel.setBackground(new Color(0, 128, 0));
		PlayerHandPanel.setBounds(0, 541, 1018, 109);
		PlayerHandPanel.setForeground(new Color(0, 0, 0));
		PlayerHandPanel.setPreferredSize(new Dimension(150, 150));
		Table.getContentPane().add(PlayerHandPanel);
		
		lblOpponentScore.setText("Dealer games won: " + opponentScore);
		lblOpponentScore.setBounds(133, 350, 189, 29);
		Table.getContentPane().add(lblOpponentScore);
		
		lblPlayerScore.setText("Player games won: " + playerScore);
		lblPlayerScore.setBounds(133, 385, 189, 24);
		Table.getContentPane().add(lblPlayerScore);

		lblOpponentHandVal.setText("" + OpponentHandPanel.getHandValue());
		lblOpponentHandVal.setBounds(439, 121, 147, 29);
		Table.getContentPane().add(lblOpponentHandVal);
		
		lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue());
		lblPlayerHandVal.setBounds(433, 500, 153, 29);
		Table.getContentPane().add(lblPlayerHandVal);
		
		Table.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{PlayerHandPanel}));
		Table.setBounds(0, 0, 1024, 678);
		Table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		blackjack = checkForBlackjack(PlayerHandPanel, OpponentHandPanel, false);
		switch(blackjack) {
			case 1: 
				tie();
				//lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Push");
				break;
			case 2: 
				playerWin();
				//lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Blackjack!");
				break;
			case 3: 
				dealerWin();
				//lblPlayerHandVal.setText("" + PlayerHandPanel.getHandValue() + " -- Dealer Blackjack!");
				break;
			default:
				break;	
		}
	}
}
