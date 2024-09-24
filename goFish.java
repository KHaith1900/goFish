import java.util.*;

public class goFish {
	public static Scanner kb = new Scanner(System.in);
	public static int players = 2;
	public static Stack<Card> deck = new Stack<Card>();
	public static Card[][] playerHand;
	public static int[] playerHandSize;
	public static int[] scoreboard;
	public static int starting = 0;
	public static int playerTurn;
	public static int pChoice;
	public static int rChoice;
	public static int setRank;
	public static int optionA;
	public static int turnCounter = 0;
	public static int lastCardChoice;

	public static void main(String[] args) {
		initializeGame();
	}

	public static void initializeGame() {
		System.out.println("Welcome to goFish How many Players will be Playing?");
		System.out.println("Type a number between 2-4");
		int tri = kb.nextInt();
		if (tri > 4 || tri <= 0) {
			while (tri > 4 || tri <= 1) {
				System.out.println("That is not a valid input Please Enter a number between 2-4");
				tri = kb.nextInt();
			}
		}
		initializeDeck();
		players = tri;
		playerHandSize = new int[players + 1];
		playerHand = new Card[players + 1][52];
		distributeCards(playerHand, players);
		scoreboard = new int[players + 1];
		int min = 1;
		int max = players;
		int range = (max - min);
		starting = (int) (Math.random() * range) + min;
		System.out.println("Player " + starting + " Will Start First");
	}

	public static void initializeDeck() {
		Card[] go = Card.createDeck();
		Card[] go2 = Card.shuffleCards(go);
		for (int i = 0; i < 52; i++) {
			deck.push(go2[i]);
		}
	}

	public static void distributeCards(Card[][] playerHand, int players) {
		for (int i = 1; i < players + 1; i++) {
			playerHandSize[i] = 7;
			for (int j = 0; j < 7; j++) {
				playerHand[i][j] = deck.pop();
			}
		}
	}
}
