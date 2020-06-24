package logic;

import logic.deepLogic.CheckAces;
import logic.deepLogic.CheckKings;
import logic.deepLogic.CheckTabToFou;
import logic.deepLogic.TableauMovement;

import java.util.ArrayList;

//DET BLIVER NOGLE VILDE LOOPS DET HER!!!!!

public class GameEngine {
	private LogicState logicState = new LogicState();
	private ArrayList<ArrayList<Card>> tableauRows = new ArrayList<>();
	private Card topDeckCard, foundationsDeckDiamonds, foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades;
	private Card[] returnCards = new Card[2];
	private Card[] prevCards = new Card[2];
	private CheckAces checkAces = new CheckAces();
	private CheckTabToFou checkTabToFou = new CheckTabToFou();
	private CheckKings checkKings = new CheckKings();
	private TableauMovement tableauMovement = new TableauMovement();
	private int backToBackTopDeck = 0;

	public void initiateGame() {
		initiateCardsArray();
		logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
		logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
		// Finished game might not need this call:
		//updateGameState();
	}

	private void initiateCardsArray() {
		tableauRows = new ArrayList<>();
	}

	public boolean revertGameState(int revertedTurns) {
		if (revertedTurns <= logicState.getHistoricHiddenCards().size() && revertedTurns != 0) {
			logicState.setHiddenCards(logicState.getHistoricHiddenCards().get(logicState.getHistoricHiddenCards().size() - revertedTurns - 1));
			logicState.setTotalCardsInTopDeck(logicState.getHistoricCardsInTopDeck().get(logicState.getHistoricCardsInTopDeck().size() - revertedTurns));
			for (int i = 0; i < revertedTurns; i++) {
				logicState.getHistoricHiddenCards().remove(logicState.getHistoricCardsInTopDeck().size() - 1);
				logicState.getHistoricCardsInTopDeck().remove(logicState.getHistoricCardsInTopDeck().size() - 1);
			}
			return true;
		}
		else
			return false;
	}

	public Card[] updateGameState(ArrayList<ArrayList<Card>> input) {
		//The rank of cards in Solitaire games is: K(13), Q(12), J(11), 10, 9, 8, 7, 6, 5, 4, 3, 2, A(1).
		//The color of the cards can be the following: Diamonds, Hearts, Clubs and Spades.
		initiateCardsArray();
		topDeckCard = input.get(1).get(0);

		tableauRows.add(input.get(6));
		tableauRows.add(input.get(7));
		tableauRows.add(input.get(8));
		tableauRows.add(input.get(9));
		tableauRows.add(input.get(10));
		tableauRows.add(input.get(11));
		tableauRows.add(input.get(12));

		foundationsDeckDiamonds = input.get(2).get(0);
		foundationsDeckHearts = input.get(3).get(0);
		foundationsDeckClubs = input.get(4).get(0);
		foundationsDeckSpades = input.get(5).get(0);

		logicState.updateState(tableauRows, topDeckCard, foundationsDeckDiamonds,
				foundationsDeckHearts, foundationsDeckClubs, foundationsDeckSpades);
		return calculateNextMove();
	}

	private Card[] calculateNextMove() {
		checkAces.setLogicState(logicState);
		checkTabToFou.setLogicState(logicState);
		checkKings.setLogicState(logicState);
		tableauMovement.setLogicState(logicState);

		returnCards = null;

		// checkTopDeckForAce
		returnCards = checkAces.checkTopDeckForAce();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("checkTopDeckForAce FÆRDIG");
			backToBackTopDeck = 0;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return returnCards;
		}

		// checkTopDeckForAce
		returnCards = checkAces.checkTableauRowsForAce();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("checkTableauRowsForAce FÆRDIG");
			backToBackTopDeck = 0;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return returnCards;
		}

		// topdeckToTableau
		returnCards = tableauMovement.topdeckToTableau();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("topdeckToTableau FÆRDIG");
			backToBackTopDeck = 0;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return returnCards;
		}

		// checkForKing
		returnCards = checkKings.checkForKing();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("checkForKing FÆRDIG");
			backToBackTopDeck = 0;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return returnCards;
		}

		// tableauToTableauHiddenCard
		returnCards = tableauMovement.tableauToTableauHiddenCard();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("tableauToTableauHidden FÆRDIG");
			backToBackTopDeck = 0;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return returnCards;
		}

		// topDeckToFoundation
		returnCards = checkTabToFou.topDeckToFoundation();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("topDeckToFoundation FÆRDIG");
			backToBackTopDeck = 0;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return returnCards;
		}

		// tabRowToTabRow
		returnCards = tableauMovement.tabRowToTabRow();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("tabRowToTabRow FÆRDIG");
			backToBackTopDeck = 0;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return returnCards;
		}

		// tableauToTableau
		returnCards = tableauMovement.tableauToTableau();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("tableauToTableau FÆRDIG");
			if((returnCards[0] == prevCards[0]) && (returnCards[1].getValue() == prevCards[1].getValue())){
				returnCards = null;
				revertGameState(1);
			} else {
				backToBackTopDeck = 0;
				logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
				logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
				prevCards = returnCards.clone();
				return returnCards;
			}
		}

		// checkTableauToFoundation
		returnCards = checkTabToFou.checkTableauToFoundation();
		if (returnCards != null) {
			//System.out.println(retrunCards[0].toString() + retrunCards[1].toString());
			//System.out.println("checkTableauToFoundation FÆRDIG");
			backToBackTopDeck = 0;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return returnCards;
		}

		// getTotalCardsInTopDeck
		if (logicState.getTotalCardsInTopDeck() > 0 && backToBackTopDeck < logicState.getTotalCardsInTopDeck()) {
			//System.out.println("Turn the top deck.");
			//System.out.println("topDeck FÆRDIG");
			backToBackTopDeck++;
			logicState.getHistoricHiddenCards().add(logicState.getHiddenCards().clone());
			logicState.getHistoricCardsInTopDeck().add(logicState.getTotalCardsInTopDeck());
			return new Card[] {new Card(14, "")};
		} else {
			if (logicState.getFoundationsDeckDiamonds().getValue() == 13
					&& logicState.getFoundationsDeckHearts().getValue() == 13
					&& logicState.getFoundationsDeckClubs().getValue() == 13
					&& logicState.getFoundationsDeckSpades().getValue() == 13) {
				//System.out.println("CONGRATULATIONS - you won the game!");
				return new Card[] {new Card(15, "")};
			}
			else {
				//System.out.println("Game over!");
				return new Card[] {new Card(16, "")};
			}
		}
	}
}