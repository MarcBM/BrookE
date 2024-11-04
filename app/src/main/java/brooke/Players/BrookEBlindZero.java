package brooke.Players;

import java.util.ArrayList;

import brooke.*;
import brooke.GameObjects.*;
import brooke.util.*;

public class BrookEBlindZero extends Player {
  public BrookEBlindZero(String name) {
    super(name);
  }

  public int makeBid(GameState state) {
    InputHandler.printNewSection();
    System.out.println("It is time for " + name + " to make a bid.");

    // Print out hand
    System.out.println("\nYour hand is:");
    System.out.println(hand);

    // Determine Bid
    int illegalBid = -1;
    if (state.currentDealer == id && state.handSize != 1) {
      illegalBid = Rules.calculateIllegalBid(state.handSize, state.currentBids);
    }

    int bid;
    if (state.handSize == 1) {
      bid = Strategies.idealOneBid(state);
    } else {
      bid = defensiveBid(state, illegalBid);
    }

    // Print Bid and await continue.
    System.out.println("\n" + name + " bids " + bid + ".");
    InputHandler.pressEnterToContinue();

    return bid;
  }

  public int makeHeadBid(GameState state, Card[] opponentCards) {
    InputHandler.printNewSection();
    System.out.println("It is time for " + name + " to make a bid.");

    // Determine Bid
    int bid = Strategies.idealHeadBid(state, opponentCards);

    // Print Bid and await continue.
    System.out.println("\n" + name + " bids " + bid + ".");
    InputHandler.pressEnterToContinue();

    return bid;
  }

  public Card playCard(GameState state) {
    InputHandler.printNewSection();
    System.out.println("It is time for " + name + " to play a card.");

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

    // Print out hand
    System.out.println("\nYour hand is:");
    System.out.print(hand);

    // Determine Play
    Card play;
    ArrayList<Card> legalPlays = Rules.calculateLegalPlays(hand, state, id);
    if (legalPlays.size() == 1) {
      play = hand.playCard(legalPlays.get(0));
    } else {
      play = defensivePlay(state, legalPlays);
    }

    // Print Play and await continue.
    System.out.println("\n\n" + name + " plays " + play + ".");
    InputHandler.pressEnterToContinue();

    return play;
  }

  private int defensiveBid(GameState state, int illegalBid) {
    // TODO: Bid one if we have high trumps.
    if (illegalBid == 0) {
      return 1;
    } else {
      return 0;
    }
  }

  private Card defensivePlay(GameState state, ArrayList<Card> legalPlays) {
    // TODO: Play the highest card we can while still losing the trick.
    int randomCardIndex = (int) (Math.random() * legalPlays.size());
    return hand.playCard(legalPlays.get(randomCardIndex));
  }
}
