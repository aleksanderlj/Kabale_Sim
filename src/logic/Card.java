package logic;

public class Card  {
	private int value;
	private String suit;
	private boolean red;
	float confidence;
	//	// This value is only meant for cards in the stackdecks
	
	public Card (int value, String suit){
		this.value = value;
		this.suit = suit;
		red = suit.equals("Hearts") || suit.equals("Diamonds");
		confidence = 100;
	}

	public Card (int classID, float confidence) {
		this.confidence = confidence;

			if (classID % 4 == 0) {
				suit="Spades";
				red = false;
			} else if (classID % 4 == 1) {
				suit="Clubs";
				red = false;
			} else if (classID % 4 == 2) {
				suit="Hearts";
				red = true;
			} else if (classID % 4 == 3) {
				suit="Diamonds";
				red = true;
			}

			value = (classID / 4) + 1;
	}

	public Card() {
		this.confidence = 100;
		this.suit = "Dummy";
		this.value = 0;
	}

	@Override
	public String toString() {
		String returnString = "";
		if (this.value <= 10) {
			returnString = returnString + this.suit.charAt(0) + this.value;
			return returnString;
		}
		else if (this.value == 11){
			returnString = returnString + this.suit.charAt(0) + "J";
			return returnString;
		}
		else if (this.value == 12){
			returnString = returnString + this.suit.charAt(0) + "Q";
			return returnString;
		}
		else {
			returnString = returnString + this.suit.charAt(0) + "K";
			return returnString;
		}
	}
	
	public int getValue() {
		return value;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public boolean isRed() {
		return red;
	}
	
	public void setRed(boolean red) {
		this.red = red;
	}


	public float getConfidence() {
		return confidence;
	}
}
