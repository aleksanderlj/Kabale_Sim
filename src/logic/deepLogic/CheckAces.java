package logic.deepLogic;


import logic.Card;
import logic.LogicState;

public class CheckAces {
	private LogicState logicState;

	public CheckAces(){}
	
	public Card[] checkTopDeckForAce(){
		if (logicState.getTopDeckCard().getValue() == 1) {
			logicState.setTotalCardsInTopDeck(logicState.getTotalCardsInTopDeck() - 1);
			switch (logicState.getTopDeckCard().getSuit()) {
				case "Diamonds":
					logicState.getFoundationsDeckDiamonds().setSuit("Foundation pile 1");
					return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckDiamonds()};
				case "Hearts":
					logicState.getFoundationsDeckHearts().setSuit("Foundation pile 2");
					return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckHearts()};
				case "Clubs":
					logicState.getFoundationsDeckClubs().setSuit("Foundation pile 3");
					return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckClubs()};
				case "Spades":
					logicState.getFoundationsDeckSpades().setSuit("Foundation pile 4");
					return new Card[] {logicState.getTopDeckCard(), logicState.getFoundationsDeckSpades()};
			}
		}
		return null;
	}
	
	public Card[] checkTableauRowsForAce(){
		for (int i = 0; i < logicState.getTableauRows().size(); i++) {
			if (logicState.getTableauRows().get(i).get(0).getValue() != 0) {
				if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() == 1) {
					int[] newHiddenCards = logicState.getHiddenCards();
					switch (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit()) {
						case "Diamonds":
							if (logicState.getHiddenCards()[i] != 0) {
								newHiddenCards[i] = newHiddenCards[i] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							logicState.getFoundationsDeckDiamonds().setSuit("Foundation pile 1");
							return new Card[] {logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1),
									logicState.getFoundationsDeckDiamonds()};
						case "Hearts":
							if (logicState.getHiddenCards()[i] != 0) {
								newHiddenCards[i] = newHiddenCards[i] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							logicState.getFoundationsDeckHearts().setSuit("Foundation pile 2");
							return new Card[] {logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1),
									logicState.getFoundationsDeckHearts()};
						case "Clubs":
							if (logicState.getHiddenCards()[i] != 0) {
								newHiddenCards[i] = newHiddenCards[i] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							logicState.getFoundationsDeckClubs().setSuit("Foundation pile 3");
							return new Card[] {logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1),
									logicState.getFoundationsDeckClubs()};
						case "Spades":
							if (logicState.getHiddenCards()[i] != 0) {
								newHiddenCards[i] = newHiddenCards[i] - 1;
								logicState.setHiddenCards(newHiddenCards);
							}
							logicState.getFoundationsDeckSpades().setSuit("Foundation pile 4");
							return new Card[] {logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1),
									logicState.getFoundationsDeckSpades()};

					}
				}
			}
		}
		return null;
	}
	public void setLogicState(LogicState logicState) {
		this.logicState = logicState;
	}
}
