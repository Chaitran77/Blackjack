package com.kiranthepro;

import java.util.*;

public class Main {

    public static final int decision_stay = 432789;
    public static final int decision_receive = 162437;

    public static void main(String[] args) {
	// write your code here
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        gameLoop(scanner, random);
    }

    public static void gameLoop(Scanner scanner, Random random) {
        Map<String, int[]> allDealtCards = generateStartingNumbers(random, 11,1);
        System.out.println(Arrays.toString(allDealtCards.get("computerNumbers")));
        System.out.println(Arrays.toString(allDealtCards.get("playerNumbers")));

        System.out.println(getSumOfHand(allDealtCards.get("computerNumbers")));
        System.out.println(getSumOfHand(allDealtCards.get("playerNumbers")));
    }



    public static Map<String, int[]> generateStartingNumbers(Random random, int upperBound, int lowerBound) {
        // basically deal the cards

        Map<String, int[]> numbers = new HashMap<>();

        int[] computerNumbers = new int[14];
        computerNumbers[0] = random.nextInt(upperBound) + lowerBound;
        computerNumbers[1] = random.nextInt(upperBound) + lowerBound;


        int[] playerNumbers = new int[14];
        playerNumbers[0] = random.nextInt(upperBound) + lowerBound;
        playerNumbers[1] = random.nextInt(upperBound) + lowerBound;

        numbers.put("computerNumbers", computerNumbers);
        numbers.put("playerNumbers", playerNumbers);

        return numbers;
    }


    public int makeDecision(Scanner scanner) {
        System.out.println("What would you like to do?\ns) Stay with your card\nr) Receive another card");

        String userInput = scanner.next();

        switch (userInput.toLowerCase().charAt(0)) {
            case 's':
                // stay
                return decision_stay;
            case 'r':
                // receive
                return decision_receive;
            default:
                System.out.println("Error, please type 'stay' or 'receive'. Try again:");
                makeDecision(scanner);
                return 0;
        }

    }

    public static int getSumOfHand(int[] hand) {
        int sum = 0;

        for (int card = 0; card < hand.length; card++) {
            sum += hand[card];
        }

        return sum;
    }

    public int checkIfWonLost() {
        return 0;
    }
}
