package brooke.Players;

import brooke.GameObjects.*;
import brooke.util.*;
import brooke.*;

public class Human extends Player {

  public Human(String name) {
    super(name);
  }

  public int makeBid(GameState state) {
    InputHandler.printNewSection();
    System.out.println("It is time for " + name + " to make a bid.");
    System.out.println("What did they say?");

    // Get user input for bid
    int illegalBid = -1;
    if (state.currentDealer == id && state.handSize != 1) {
      illegalBid = Rules.calculateIllegalBid(state.handSize, state.currentBids);
    }
    // Return bid
    return InputHandler.gatherBid(state.handSize, illegalBid);
  }

  public int makeHeadBid(GameState state, Card[] opponentCards) {
    return makeBid(state);
  }

  public Card playCard(GameState state) {
    InputHandler.printNewSection();
    System.out.println("It is time for " + name + " to play a card.");
    if (state.handSize == 1 && state.currentRound < state.totalRounds / 2) {
      return hand.cards.get(0);
    } else {
      System.out.println("What did they play?");

      // Get user input for card
      return InputHandler.cardResponse();
    }
  }
}
