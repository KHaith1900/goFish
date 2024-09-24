import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is the Card Class it creates the Card Object and is the heart
 * of all of the functions for the cards such as creating decks
 * and shuffling the cards
 * 
 * @author Keith Haith
 */

public class Card {
	public static final int SPADES = 0;
	public static final int HEARTS = 1;
	public static final int DIAMONDS = 2;
	public static final int CLUBS = 3;

	public static final int ACE = 1;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;

	private int suit;
	private int rank;

	public Card(int suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public int getSuit() {
		return suit;
	}

	public int getRank() {
		return rank;
	}

	public String toString() {
		String[] suits = { "Spades", "Hearts", "Diamonds", "Clubs" };
		String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

		return ranks[rank] + "of" + suits[suit];
	}

	public static Card[] createDeck() {
		String[] suits = { "Spades", "Hearts", "Diamonds", "Clubs" };
		String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
		Card[] deck = new Card[52];
		int num = 0;
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < ranks.length; j++) {
				deck[num] = new Card(i, j);
				num++;
			}
		}
		return deck;
	}

}