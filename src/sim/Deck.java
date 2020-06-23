package sim;

import logic.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards, reserve, reserveBackup;
    private ArrayList<ArrayList<Card>> hiddenCards, board;

    public Deck(){
        this.cards = new ArrayList<>();
        this.board = new ArrayList<>();
        this.reserve = new ArrayList<>();
        this.reserveBackup = new ArrayList<>();
        this.hiddenCards = new ArrayList<>();

        for(int n=0 ; n<13 ; n++){
            hiddenCards.add(new ArrayList<>());
        }

        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        for(String suit : suits){
            for(int n=1; n<14 ; n++){
                cards.add(new Card(n, suit));
            }
        }

        Collections.shuffle(cards);
    }

    public ArrayList<ArrayList<Card>> initBoard(){
        board.add(null);

        // Initialize all the rows, reserve and foundations
        for(int n=1 ; n<13 ; n++){
            board.add(new ArrayList<>());
        }

        // Put dummy into empty arrays
        for(int n=1 ; n<6 ; n++){
            board.get(n).add(new Card());
        }

        // Put card into each row and add hidden cards
        for(int n=6 ; n<13 ; n++){
            board.get(n).add(cards.remove(0));
            for(int i=0 ; i<n-6 ; i++){
                hiddenCards.get(n).add(cards.remove(0));
            }
        }

        // Add rest of cards to reserve
        while(!cards.isEmpty()){
            reserve.add(cards.remove(0));
        }

        return board;
    }

    public int moveCards(Card[] cards){
        int[] id = {-1, -1};
        int result = 0; // 0 = not done, 1 = win, 2 = lose

        // Finds which pile the cards are in
        for(int n=1 ; n<board.size() ; n++){
            for(Card c : board.get(n)){
                if(c==cards[0]){
                    id[0] = n;
                }

                if(cards.length > 1) {
                    if (c == cards[1]) {
                        id[1] = n;
                    }
                }
            }
        }


        if(id[0] == 1){ // From reserve
            if((id[1] > 1) && (id[1] < 6)){ // To foundation
                reserve2foundation();
            } else if(id[1] > 5){
                reserve2tableau(id); // To tableau
            }
        } else if((id[0] > 5) && (id[0] < 13)){ // From tableau
            if((id[1] > 1) && (id[1] < 6)){ // To foundation
                tableau2foundation(id);
            } else if(id[1] > 5){
                tableau2tableau(id, cards); // To tableau
            }
        } else if(cards[0].getValue() == 14){ // Turn top deck
            turnTopDeck();
        } else if(cards[0].getValue() == 15){ // Win/Lose
            result = 1;
        } else if(cards[0].getValue() == 16){
            result = 2;
        }

        return result;
    }

    private void turnTopDeck(){
        if(!reserve.isEmpty()) {
            Card c = reserve.remove(0);
            reserveBackup.add(c);
            board.get(1).set(0, c);
        } else{
            reserve = (ArrayList<Card>) reserveBackup.clone();
            reserveBackup = new ArrayList<>();
            board.set(1, new ArrayList<Card>());
            board.get(1).add(0, new Card());
        }
    }

    private void reserve2foundation(){
        Card c = board.get(1).remove(0);
        reserveBackup.remove(reserveBackup.size()-1);

        switch (c.getSuit()){
            case "Diamonds":
                board.get(2).set(0, c);
                break;

            case "Hearts":
                board.get(3).set(0, c);
                break;

            case "Clubs":
                board.get(4).set(0, c);
                break;

            case "Spades":
                board.get(5).set(0, c);
                break;
        }

        if(reserveBackup.isEmpty()){
            board.get(1).add(new Card());
        } else{
            board.get(1).add(reserveBackup.get(reserveBackup.size() -1));
        }
    }

    private void reserve2tableau(int[] ids){
        Card c = board.get(1).remove(0);
        reserveBackup.remove(reserveBackup.size()-1);

        if(board.get(ids[1]).get(0).getValue()==0){
            board.get(ids[1]).remove(0);
        }

        board.get(ids[1]).add(c);

        if(reserveBackup.isEmpty()){
            board.get(1).add(new Card());
        } else{
            board.get(1).add(reserveBackup.get(reserveBackup.size() - 1));
        }
    }

    private void tableau2tableau(int[] ids, Card[] cards){
        ArrayList<Card> holder = new ArrayList<>();
        ArrayList<Card> firstTableau = board.get(ids[0]);

        int pos;
        for(pos = 0 ; pos<firstTableau.size() ; pos++){
            if(cards[0] == firstTableau.get(pos)){
                break;
            }
        }

        while(firstTableau.size() != pos){
            holder.add(firstTableau.remove(pos));
        }

        if(board.get(ids[1]).get(0).getValue()==0){
            board.get(ids[1]).remove(0);
        }

        while(!holder.isEmpty()){
            board.get(ids[1]).add(holder.remove(0));
        }

        if(firstTableau.isEmpty()) {
            if (!hiddenCards.get(ids[0]).isEmpty()) {
                firstTableau.add(hiddenCards.get(ids[0]).remove(0));
            } else {
                firstTableau.add(new Card());
            }
        }
    }

    private void tableau2foundation(int[] ids){
        Card c = board.get(ids[0]).remove(board.get(ids[0]).size()-1);

        switch (c.getSuit()){
            case "Diamonds":
                board.get(2).set(0, c);
                break;

            case "Hearts":
                board.get(3).set(0, c);
                break;

            case "Clubs":
                board.get(4).set(0, c);
                break;

            case "Spades":
                board.get(5).set(0, c);
                break;
        }

        if(board.get(ids[0]).isEmpty()) {
            if (!hiddenCards.get(ids[0]).isEmpty()) {
                board.get(ids[0]).add(hiddenCards.get(ids[0]).remove(0));
            } else {
                board.get(ids[0]).add(new Card());
            }
        }
    }

    public ArrayList<ArrayList<Card>> getBoard() {
        return board;
    }

    public ArrayList<ArrayList<Card>> getHiddenCards() {
        return hiddenCards;
    }

    public ArrayList<Card> getReserveBackup() {
        return reserveBackup;
    }

    public ArrayList<Card> getReserve() {
        return reserve;
    }
}
