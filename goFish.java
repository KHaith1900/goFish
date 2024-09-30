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
	
	public static void drawFromDeck(int player, int[] playerHandSize)
	{
		int handSize = playerHandSize[player];
		playerHand[player][handSize] = deck.pop();
		incHandSize(player);
	}

	public static void getTheCards(Card[][] playerHand, int pChoice, int starting, int rChoice)
	{
		int pChoiceHandSize = getHandSize(pChoice);
		int startingHandSize = getHandSize(starting);
		int numCards = cardsOfRank(playerHand, playerHandSize, pChoice, rChoice);
			for(int i = 0; i < pChoiceHandSize; i++)
			{
				if(playerHand[pChoice][i].getRank() == rChoice)
				{
					playerHand[starting][startingHandSize] = playerHand[pChoice][i];
					incHandSize(starting);
					startingHandSize++;
				}
			}
			for(int j = 0; j < pChoiceHandSize - 1; j++)
			{
				if(playerHand[pChoice][i].getRank() == rChoice)
				{
					removeCard(playerHand, pChoice, rChoice);
					pChoiceHandSize--;
				}
			}
	}

	public static void removeCard(Card[][] playerHand, int pChoice, int rChoice)
	{
		int handSize = getHandSize(pChoice);
		for(int i = 1; i < handSize - 1; i++)
		{
			if(playerHand[pChoice][i].getRank() == rChoice)
			{
				if(i == handSize - 1)
				{
					playerHand[pChoice][i] = null;
					decHandSize(pChoice);
					handSize--;
				}
				else
				{
					for(int j = i; j < handSize - 1; j++)
					{
						playerHand[pChoice][j] = playerHand[pChoice][j + 1];
					}
					playerHand[pChoice][handSize - 1] = null;
					decHandSize(pChoice);
					handSize--;
				}
			}
		}
	}

	public static void incHandSize(int player)
	{
		playerHandSize[player] += 1;
	}

	public static void decHandSize(int player)
	{
		playerHandSize[player] -= 1;
	}

	public static boolean hasSet(int starting)
	{
		boolean checked = false;
		int handSize = getHandSize(starting);
		int count = 0;
		for(int i = 0; i < handSize - 1; i++)
		{
			for(int rank = 0; rank < 13; rank++)
			{
				if(playerHand[starting][i].getRank() == rank)
				{
					count++;
					if(count == 4)
					{
						setRank = rank;
						checked = true;
					}
				}
			}
			count = 0;
		}
		return checked;
	}

	public static void removeSet(int starting)
	{
		for(int i = 0; i < 4; i++)
		{
			removeCard(playerHand, starting, setRank);
		}
		scoreboard[starting] += 1;
	}

	public static int getHandSize(int player)
	{
		return playerHandSize[player];
	}

	public static int cardsOfRank(Card[][] playerHand, int[] playerHandSize, int player, int rank)
	{
		int handSize = getHandSize(player);
		int count = 0;
		for(int i = 0; i < handSize; i++)
		{
			if(playerHand[player][i].getRank() == rank)
			{
				count++;
			}
		}
		return count;
	}

	public static void printHand(Card[][] playHand, int player, int[] playerHandSize)
	{
		int handSize = playerHandSize[player];
		for(int i = 0; i < handSize - 1; i++)
		{
			System.out.println(playHand[player][i].toString());
		}
	}

	/**
	 *  nextTurn
	 *  Clears the actions of the previous player and sets the next Players Turn
	 */
	public static void nextTurn()
	{

	}


}
