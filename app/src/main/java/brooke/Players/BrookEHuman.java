package brooke.Players;

import java.util.ArrayList;

import brooke.*;
import brooke.GameObjects.*;
import brooke.util.*;

public class BrookEHuman extends Player {

  public BrookEHuman(String name) {
    super(name);
  }

  public int makeBid(GameState state) {
    // Print out current bids
    InputHandler.printNewSection();
    System.out.println("It is time for " + name + " to make a bid.");
    System.out.println("The current Trump Card is: " + state.trumpCard);
    System.out.println("The current Dealer is: " + state.playerNames[state.currentDealer]);

    if (state.handSize != 1) {
      System.out.println("\nThe current bids are:");
      for (int i = 0; i < state.numPlayers; i++) {
        int otherBid = state.currentBids[i];
        System.out.print(state.playerNames[i] + ": ");
        if (otherBid == -1) {
          System.out.print("-\n");
        } else {
          System.out.print(otherBid + "\n");
        }
      }
    }

    // Print out hand
    System.out.println("\nYour hand is:");
    System.out.println(hand);

    // Get user input for bid
    int illegalBid = -1;
    if (state.currentDealer == id && state.handSize != 1) {
      illegalBid = Rules.calculateIllegalBid(state.handSize, state.currentBids);
    }
    // Return bid
    System.out.println("\nWhat would you like to bid?");
    return InputHandler.gatherBid(state.handSize, illegalBid);
  }

  public int makeHeadBid(GameState state, Card[] opponentCards) {
    // Print out general trick information
    InputHandler.printNewSection();
    System.out.println("It is time for " + name + " to make a bid.");
    System.out.println("The current Trump Card is: " + state.trumpCard);

    // Print out opponent cards
    System.out.println("\nThe opponent cards are:");
    for (int i = 0; i < opponentCards.length; i++) {
      if (i == id) {
        System.out.print("You: ??");
      } else {
        System.out.print(state.playerNames[i] + ": " + opponentCards[i]);
      }
      if (i == state.currentLeader) {
        System.out.print(" (Leader)");
      }
      System.out.print("\n");
    }

    // Get user input for bid
    System.out.println("\nWhat would you like to bid?");
    return InputHandler.gatherBid(state.handSize, -1);
  }

  public Card playCard(GameState state) {
    // Print out current state.
    InputHandler.printNewSection();
    System.out.println("It is time for " + name + " to play a card.");
    System.out.println("The current Trump Card is: " + state.trumpCard);
    if (state.winningCard != null) {
      System.out.println("\nThe current Winning Card is: " + state.winningCard + ", played by "
          + state.playerNames[state.winningPlayer]);
      System.out.println("The leading suit is: " + state.currentPlay[state.currentLeader].getSuit());
    }

    // Print out current plays
    System.out.println("\nThe current plays are:");
    for (int i = 0; i < state.numPlayers; i++) {
      Card otherCard = state.currentPlay[i];
      System.out.print(state.playerNames[i] + ": ");
      if (otherCard == null) {
        System.out.print("-\n");
      } else {
        System.out.print(otherCard + "\n");
      }
    }

    // Print out bids and tricks
    System.out.println("\nThe current bids are:");
    for (int i = 0; i < state.numPlayers; i++) {
      int otherBid = state.currentBids[i];
      System.out.print(state.playerNames[i] + ": ");
      if (otherBid == -1) {
        System.out.print("-\n");
      } else {
        System.out.print(otherBid + " (" + state.currentTricks[i] + ")\n");
      }
    }

    // Print out hand
    System.out.println("\nYour hand is:");
    System.out.print(hand);

    // Print out your own bid
    System.out.println("and your bid is: " + state.currentBids[id]);

    // Get user input for card
    ArrayList<Card> legalPlays = Rules.calculateLegalPlays(hand, state, id);
    if (legalPlays.size() == 1) {
      System.out.println("\nYou must play " + legalPlays.get(0));
      return hand.playCard(legalPlays.get(0));
    } else {
      return hand.playCard(InputHandler.gatherPlay(legalPlays));
    }
  }
}
