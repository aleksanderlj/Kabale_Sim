import logic.Card;
import logic.GameEngine;
import sim.Deck;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final int A = 100, B = 10000;
        Deck deck = new Deck();
        GameEngine ge = new GameEngine();
        ge.initiateGame();
        ArrayList<ArrayList<Card>> board = deck.initBoard();

        ArrayList<Card> startReserve = new ArrayList<>();
        ArrayList<Card> startReserveBackup = new ArrayList<>();
        ArrayList<ArrayList<Card>> startHiddenCards = new ArrayList<>();
        ArrayList<ArrayList<Card>> startBoard = new ArrayList<>();

        int[] totalScores = {0, 0, 0};
        int result = -1, iterations = 0;
        Card[] cards = new Card[2];

        for (int i = 0; i < A; i++) {
            System.out.println("Progess: " + i * B + "/" + A*B);
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

                /*
                if (result == 1) {
                    System.out.println("----------------------------------");
                    System.out.println(startReserve);
                    System.out.println(startReserveBackup);
                    System.out.println(startHiddenCards);

                    System.out.println(startBoard);
                }

                 */

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

                totalScores[result - 1]++;

                deck = new Deck();
                ge = new GameEngine();
                ge.initiateGame();
                board = deck.initBoard();

                /*
                startReserve = (ArrayList<Card>) deck.getReserve().clone();
                startReserveBackup = (ArrayList<Card>) deck.getReserveBackup().clone();
                startHiddenCards = copyArrayOfArray((ArrayList<ArrayList<Card>>) deck.getHiddenCards().clone());
                startBoard = copyArrayOfArray((ArrayList<ArrayList<Card>>) board.clone());

                 */
            }
        }

        System.out.println("Wins: " + totalScores[0]);
        System.out.println("Loses: " + totalScores[1]);
        System.out.println("Loops: " + totalScores[2]);
    }

    private static ArrayList<ArrayList<Card>> copyArrayOfArray(ArrayList<ArrayList<Card>> arr) {
        ArrayList<ArrayList<Card>> temp = new ArrayList<ArrayList<Card>>();

        for (int n = 0; n < arr.size(); n++) {
            if (arr.get(n) == null) {
                temp.add(null);
            } else {
                temp.add((ArrayList<Card>) arr.get(n).clone());
            }
        }

        return temp;
    }
}
