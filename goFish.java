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
		while(deck.size() > 1)
		{
			turn();
		}
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
			for(int i = 0; i < numCards; i++)
			{
				for(int j = 0; j < pChoiceHandSize - 1; j++)
				{
					if(playerHand[pChoice][j].getRank() == rChoice)
					{
						removeCard(playerHand, pChoice, rChoice);
						pChoiceHandSize--;
					}
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

	public static boolean checkForCard(Card[][] playerHand, int[] playerHandSize, int player, int rank)
	{
		boolean check = false;
		int handSize = getHandSize(player);
		for(int i = 0; i < handSize; i++)
		{
			if(playerHand[player][i].getRank() == rank)
			{
				check = true;
			}
		}
		return check;
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
	 * 	clearPlay
	 * 	adds 20 lines to clear the choices of the previous player
	 */
	public static void clearPlay()
	{
		for(int i = 0; i < 20; i++)
		{
			System.out.println(" ");
		}
	}

	/**
	 * Turn
	 * Main game function
	 */
	public static void turn()
	{
		if(turnCounter > 0)
		{
			System.out.println("Its Player " + starting + " 's Turn");
			printHand(playerHand, starting, playerHandSize);
			optionA = option();
			if(optionA == 1)
			{
				pChoice = checkPlayer();
				lastCardChoice = rChoice = selectRank();
				if(rChoice == lastCardChoice)
				{
					while(rChoice == lastCardChoice)
					{
						System.out.println("You've Just Selected This Card. Please Select Another");
						rChoice = selectRank();
					}
				}
			}
			else
			{
				scoreboard();
				pChoice = checkPlayer();
				lastCardChoice = rChoice = selectRank();
			}

			if(checkForCard(playerHand, playerHandSize, pChoice, rChoice))
			{
				int rAmount = cardsOfRank(playerHand, playerHandSize, pChoice, rChoice);
				if(rChoice == 0)
				{
					System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " Aces");
				}
				else if(rChoice + 1 == 11)
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " Jacks");
                }
                else if(rChoice + 1 == 12)
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " Queens");
                }
                else if(rChoice + 1 == 13)
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " Kings");
                }
                else
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " " + (rChoice + 1) + "'s");
                }
				if(rAmount > 1)
				{
					getTheCards(playerHand, pChoice, starting, rChoice);
					boolean isSet = hasSet(starting);
					if(isSet)
					{
						System.out.println("Congrats You have a set");
						removeSet(starting);
					}
					turn();
				}
				else
				{
					getTheCards(playerHand, pChoice, starting, rChoice);
					boolean isSet = hasSet(starting);
					if(isSet)
					{
						System.out.println("Congrats You have a set");
						removeSet(starting);
					}
					nextTurn();
				}
			}
			else
			{
				System.out.println("GO FISH!!!");
				drawFromDeck(starting, playerHandSize);
				boolean isSet = hasSet(starting);
				if(isSet)
				{
					removeSet(starting);
				}
				nextTurn();
			}
		}
		else
		{
			System.out.println("Its Player " + starting + " 's Turn");
			printHand(playerHand, starting, playerHandSize);
			optionA = option();
			if(optionA == 1)
			{
				pChoice = checkPlayer();
				lastCardChoice = rChoice = selectRank();
			}
			else
			{
				scoreboard();
				pChoice = checkPlayer();
				lastCardChoice = rChoice = selectRank();
			}

			if(checkForCard(playerHand, playerHandSize, pChoice, rChoice))
			{
				int rAmount = cardsOfRank(playerHand, playerHandSize, pChoice, rChoice);
                if(rChoice == 0)
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " Aces");
                }
                else if(rChoice + 1 == 11)
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " Jacks");
                }
                else if(rChoice + 1 == 12)
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " Queens");
                }
                else if(rChoice + 1 == 13)
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " Kings");
                }
                else
                {
                    System.out.println("CATCH!!! Player " + pChoice + " Had " + rAmount + " " + (rChoice + 1) + "'s");
                }
				if(rAmount > 1)
				{
					getTheCards(playerHand, pChoice, starting, rChoice);
					boolean isSet = hasSet(starting);
					if(isSet)
					{
						System.out.println("Congrats you have a set");
						removeSet(starting);
					}
					turn();
				}
				else
				{
					getTheCards(playerHand, pChoice, starting, rChoice);
                    boolean isSet = hasSet(starting);
                    if(isSet)
                    {
                        System.out.println("Congrats You have a set");
                        removeSet(starting);
                    }
                    nextTurn();
				}
			}
			else
            {
                System.out.println("GO FISH!!!");
                drawFromDeck(starting, playerHandSize);
                boolean isSet = hasSet(starting);
                if(isSet)
                {
                    removeSet(starting);
                }
                nextTurn();
            }
		}
	}


	/**
	 *  nextTurn
	 *  Clears the actions of the previous player and sets the next Players Turn
	 */
	public static void nextTurn()
	{
		turnCounter = 0;
		System.out.println("Your Turn is Over Type '1' To Start Next Turn");
		int turn = kb.nextInt();
		if(turn != 1)
		{
			while(turn != 1)
			{
				System.out.println("Entering a different number doesn't mean your turn is still not over");
				System.out.println("Please Enter 1");
				turn = kb.nextInt();
			}
			clearPlay();
			if(starting == players)
			{
				starting = 1;
			}
			else
			{
				starting++;
			}
		}
		else
		{
			clearPlay();
			if(starting == players)
			{
				starting = 1;
			}
			else
			{
				starting++;
			}
		}
	}

	/**
	 * 	Option
	 * 	Displays the player options of checking to see if a player has a specific card
	 *  Or To Check the scoreboard
	 */
	public static int option()
	{
		System.out.println("Type 1: To Check A Player");
		System.out.println("Type 2: To See Scoreboard");
		int num = kb.nextInt();
		if(num > 2 || num < 1)
		{
			while(num > 2 || num < 1)
			{
				System.out.println("Please Enter a Valid Input");
				num = kb.nextInt();
			}	
		}
		return num;
	}

	/**
	 * 	CheckPlayer
	 * 	Allows the player to see if an opposing player has the card they are looking for
	 */
	public static int checkPlayer()
	{
		for(int i = 1; i <= players; i++)
		{
			if(i != starting)
			{
				System.out.println("Check Player " + i);
			}
		}
		int checkPlayer = kb.nextInt();
		if(checkPlayer > players || checkPlayer < 1 || checkPlayer == starting)
		{
			while(checkPlayer > players || checkPlayer < 1 || checkPlayer == starting)
			{
				System.out.println("Please Enter a valid Input");
				checkPlayer = kb.nextInt();
			}
		}
		return checkPlayer;
	}

	/**
	 * 	scoreboard()
	 * 	Displays the scores of a players
	 */
	public static void scoreboard()
	{
		for(int i = 0; i < players; i++)
		{
			System.out.println("Player " + (i + 1) + " Score " + scoreboard[i]);
		}
	}

	public static int selectRank()
	{
		System.out.println("Please Select a Rank to try to steal from opponent");
		System.out.println("Type 1 for Ace, 11 for Jack, 12 for Queen, and 13 for King");
		int dRank = kb.nextInt();
		if(dRank > 13 || dRank < 1)
		{
			System.out.println("Please Enter a valid rank");
			dRank = kb.nextInt();
		}
		return dRank-1;
	}

}
