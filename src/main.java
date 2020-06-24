import logic.Card;
import logic.GameEngine;
import sim.Deck;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        final int A = 100, B = 1000;
        Deck deck = new Deck();
        GameEngine ge = new GameEngine();
        ge.initiateGame();
        ArrayList<ArrayList<Card>> board = deck.initBoard();

        int[] scores = {0,0,0}; // Wins, loses, loops
        int[] totalScores = {0, 0, 0};
        int result = -1, iterations = 0;
        Card[] cards = new Card[2];

        for(int i=0 ; i < A ; i++) {
            System.out.println(i*B);
            for (int n = 0; n < B; n++) {
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

                if (iterations >= 1000) {
                    result = 3;
                    System.out.println("----------------------------------");
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

                //System.out.println(B*i+n);

            }

            totalScores[0] += scores[0];
            totalScores[1] += scores[1];
            totalScores[2] += scores[2];

            scores = new int[]{0, 0, 0};
        }

        System.out.println("Wins: " + totalScores[0]/(double)A);
        System.out.println("Loses: " + totalScores[1]/(double)A);
        System.out.println("Loops: " + totalScores[2]/(double)A);
    }
}
