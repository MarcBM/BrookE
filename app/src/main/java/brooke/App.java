package brooke;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to CrystalBrook!");

        int numPlayers = gatherNumPlayers(sc);

        int startingHandSize = gatherStartingHandSize(sc, numPlayers);

        CrystalBrook game = gatherGameType(sc, numPlayers);

        GameManager gm = new GameManager(game, numPlayers, startingHandSize);
    }

    private static CrystalBrook gatherGameType(Scanner sc, int numPlayers) {
        System.out.println("Is this a real game? (y/n)");
        String response = sc.nextLine();

        if (response.equals("y")) {
            return new TableGame(numPlayers);
        } else if (response.equals("n")) {
            return new TerminalGame();
        } else {
            System.out.println("Invalid response. Please try again.");
            return gatherGameType(sc, numPlayers);
        }
    }

    private static int gatherNumPlayers(Scanner sc) {
        System.out.println("Please enter the number of players: ");

        int response;

        try {
            response = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number between 4 and 10.");
            return gatherNumPlayers(sc);
        }

        if (response >= 4 && response <= 10) {
            return response;
        } else {
            System.out.println("Please enter a number between 4 and 10.");
            return gatherNumPlayers(sc);
        }
    }

    private static int gatherStartingHandSize(Scanner sc, int numPlayers) {
        System.out.println("Please enter the starting hand size: ");

        int response;

        try {
            response = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number!");
            return gatherStartingHandSize(sc, numPlayers);
        }

        if (response >= 2 && response * numPlayers <= 52) {
            return response;
        } else {
            System.out.println("Hand size must be at least 2 and the total number of cards must be less than 52.");
            return gatherStartingHandSize(sc, numPlayers);
        }
    }
}
