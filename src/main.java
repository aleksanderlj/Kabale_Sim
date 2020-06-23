import logic.Card;
import logic.GameEngine;
import sim.Deck;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        GameEngine ge = new GameEngine();
        ge.initiateGame();
        ArrayList<ArrayList<Card>> board = deck.initBoard();

        int[] scores = {0,0}; // Wins, loses
        int result = -1, iterations = 0;
        Card[] cards = new Card[2];

        for(int n=0 ; n<10000 ; n++) {
            iterations = 0;
            do {


                /*
                System.out.println(deck.getReserve());
                System.out.println(deck.getReserveBackup());
                System.out.print(deck.getHiddenCards());
                System.out.print(" = ");

                try {
                    System.out.print(cards[0]);
                    System.out.println(cards[1]);
                } catch (Exception e) {
                    System.out.println();
                }

                System.out.println(board);
                System.out.println("----------Round Start-----------");

                 */

                cards = ge.updateGameState(board);

                iterations++;

            } while ((result = deck.moveCards(cards)) == 0 && iterations < 1000);

            if(iterations >= 1000){
                result = 2;
                System.out.println(deck.getReserve());
                System.out.println(deck.getReserveBackup());
                System.out.println(deck.getHiddenCards());

                System.out.println(board);

                try {
                    System.out.print(cards[0]);
                    System.out.println(cards[1]);
                } catch (Exception e) {
                    System.out.println();
                }
            }

            scores[result - 1]++;


            /*
            System.out.println(deck.getReserve());
            System.out.println(deck.getReserveBackup());
            System.out.println(deck.getHiddenCards());

            System.out.println(board);

             */

            deck = new Deck();
            ge = new GameEngine();
            ge.initiateGame();
            board = deck.initBoard();


        }

        System.out.println("Wins: " + scores[0]);
        System.out.println("Loses: " + scores[1]);
    }
}
