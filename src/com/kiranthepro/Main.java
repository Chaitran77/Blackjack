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
//        System.out.println(Arrays.toString(allDealtCards.get("computerNumbers")));
//        System.out.println(Arrays.toString(allDealtCards.get("playerNumbers")));
//
//        System.out.println(getSumOfHand(allDealtCards.get("computerNumbers")));
//        System.out.println(getSumOfHand(allDealtCards.get("playerNumbers")));
        
        int playerTotal;
        int decision;
        boolean playerPlaying = true; // not decided to stay yet
        boolean playerBust = false; // < 21
        boolean playerWon = false;
        
        while (playerPlaying && !playerBust && !playerWon) {
            // winner is whoever has the highest number <= 21
            // challenge is risking receiving a card which brings the total less than 21
            playerTotal = getSumOfHand(allDealtCards.get("playerNumbers"));
            showHand(allDealtCards.get("playerNumbers"));

            if (playerTotal < 21) {
                // still playing
                decision = makeDecision(scanner);

                if (decision == decision_receive) {
                    receiveCard(allDealtCards.get("playerNumbers"), 11, 1, random);
                    System.out.println("Dealing card... \nYour new card is: " + getLastDealtCard(allDealtCards.get("playerNumbers")));
                } else if (decision == decision_stay) {

                    System.out.println("Okay, the computer will now make its moves.");
                    playerPlaying = false;
                }

            } else if (playerTotal > 21) {
                // bust
                playerBust = true;
            } else {
                // won
                System.out.println("won!");
                playerWon = true;
            }
        }
        if (!playerBust && !playerWon) {
            System.out.println("Comp making moves. Will calculate who won after moves are made.");
            makeComputerMoves(allDealtCards, random);
        }

        calculateEnd(playerBust, playerWon, allDealtCards, scanner, random);

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


    public static int makeDecision(Scanner scanner) {
        System.out.println("\nWhat would you like to do?\ns) Stay with your card\nr) Receive another card");

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

        for (int i : hand) {
            sum += i;
        }

        return sum;
    }
    
    public static void showHand(int[] hand) {
        System.out.print("Your cards are: ");
        for (int card: hand) {
            if (card != 0) {
                System.out.print(card + "  ");
            } else {
                break;
            }
        }
        System.out.println("And your total is currently at " + getSumOfHand(hand) + "\n");
    }

    public static void receiveCard(int[] hand, int upperBound, int lowerBound, Random random) {
        for (int card = 0; card < hand.length; card++) {
            if (hand[card] == 0) {
                hand[card] = random.nextInt(upperBound) + lowerBound;
                break;
            }
        }
    }

    public static int getLastDealtCard(int[] hand) {
        for (int card = 0; card < hand.length; card++) {
            if (hand[card] == 0) {
                return hand[card-1];
            }
        }
        return 0;
    }

    public static void calculateEnd(boolean playerBust, boolean playerWon, Map<String, int[]> allDealtCards, Scanner scanner, Random random) {
        if (playerBust) {
            System.out.println("You went bust. You Lose. THE END. COMPUTER WON. OOF");
            stateComputerEndValue(allDealtCards);
        } else if (playerWon) {
            System.out.println("You WON! Congrats!");
            stateComputerEndValue(allDealtCards);
        } else {
            // player not bust but computer might be
            if (getSumOfHand(allDealtCards.get("computerNumbers")) <= 21) {
                if (getSumOfHand(allDealtCards.get("computerNumbers")) > getSumOfHand(allDealtCards.get("playerNumbers"))) {
                    System.out.println("Computer got a higher score. You Lose. THE END. COMPUTER WON. OOF");
                    stateComputerEndValue(allDealtCards);
                } else {
                    System.out.println("You WON! Congrats");
                    stateComputerEndValue(allDealtCards);
                }
            } else {
                // computer bust
                System.out.println("Computer went bust. You therefore WON! Congrats!");
                stateComputerEndValue(allDealtCards);
            }

        }

        playAgainPrompt(scanner, random);
    }

    public static void stateComputerEndValue(Map<String, int[]> allDealtCards) {
        System.out.println("The computer had " + getSumOfHand(allDealtCards.get("computerNumbers")));
    }

    public static void makeComputerMoves(Map<String, int[]> allDealtCards, Random random) {
        while (getSumOfHand(allDealtCards.get("computerNumbers")) < 17) {
            // whilst total < 17, keep receiving cards
            receiveCard(allDealtCards.get("computerNumbers"), 11, 1, random);
        }

        // stay
    }

    public static void playAgainPrompt(Scanner scanner, Random random) {
        System.out.println("Would you like to play again? (y/n)");
        switch (scanner.next().toLowerCase().charAt(0)) {
            case 'y':
                System.out.println("Nice! Starting a new game: ");
                gameLoop(scanner, random);
                break;
            case 'n':
                System.out.println("Okay then, thanks for playing. Goodbye.");
                break;
            default:
                System.out.println("Not understood :( Please check what you are typing and try again:");
                playAgainPrompt(scanner, random);
        }
    }

}
