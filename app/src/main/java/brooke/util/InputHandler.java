package brooke.util;

import java.util.ArrayList;
import java.util.Scanner;

import brooke.*;
import brooke.GameObjects.*;

public class InputHandler {
  private static Scanner scanner = new Scanner(System.in);
  public static final String NEW_SECTION = "\n==============================\n\n";

  public static void closeScanner() {
    scanner.close();
  }

  public static String gatherPlayerName(int seat) {
    printNewSection();
    System.out
        .println("Please enter the name of the player sitting in seat " + seat + ". (Seat 1 being the first dealer)");
    return stringResponse();
  }

  public static int gatherBrookeSeat(int numPlayers) {
    printNewSection();
    System.out.println("Please enter which seat I am sitting in. (Seat 1 being the first dealer)");
    return intResponse(1, numPlayers);
  }

  public static int gatherStartingHandSize(int numPlayers) {
    printNewSection();
    int maxHandSize = 51 / numPlayers;
    System.out.println("Please enter the starting hand size:");
    return intResponse(3, maxHandSize);
  }

  public static int gatherNumPlayers() {
    printNewSection();
    System.out.println("How many players are there?");
    return intResponse(4, 10);
  }

  public static CrystalBrook gatherGameType(int numPlayers) {
    printNewSection();
    System.out.println("Is this a real game? (y/n)");
    if (yesNoResponse()) {
      return new TableGame(numPlayers);
    } else {
      return new TerminalGame();
    }
  }

  public static int gatherBid(int handSize, int illegalBid) {
    if (illegalBid >= 0) {
      System.out.println("You cannot bid " + illegalBid + ".");
    }

    int bid = intResponse(0, handSize);
    if (bid == illegalBid) {
      System.out.println("You cannot bid " + illegalBid + ".");
      return gatherBid(handSize, illegalBid);
    }

    return bid;
  }

  public static Card gatherPlay(ArrayList<Card> legalPlays) {
    System.out.println("\nWhat card would you like to play?");
    Card card = cardResponse();
    if (!legalPlays.contains(card)) {
      System.out.println("You cannot play that card.");
      return gatherPlay(legalPlays);
    }
    return card;
  }

  public static Card cardResponse() {
    String response = scanner.nextLine();
    try {
      return Card.parseCard(response);
    } catch (IllegalArgumentException e) {
      System.out.println("Please enter a valid card.");
      return cardResponse();
    }
  }

  public static void printNewSection() {
    System.out.print(NEW_SECTION);
  }

  public static void pressEnterToContinue() {
    System.out.println("\nPress Enter to continue...");
    scanner.nextLine();
  }

  private static String stringResponse() {
    return scanner.nextLine();
  }

  public static boolean yesNoResponse() {
    String response = scanner.nextLine();
    while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
      System.out.println("Please enter 'y' or 'n'");
      response = scanner.nextLine();
    }
    return response.equalsIgnoreCase("y");
  }

  private static int intResponse(int min, int max) {
    int response;
    try {
      response = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Please enter a number between " + min + " and " + max + ".");
      return intResponse(min, max);
    }

    if (response >= min && response <= max) {
      return response;
    } else {
      System.out.println("Please enter a number between " + min + " and " + max + ".");
      return intResponse(min, max);
    }
  }
}
