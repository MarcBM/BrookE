package brooke.Players;

import brooke.*;
import brooke.GameObjects.*;
import brooke.util.*;

public class Strategies {
  // This method calculates the appropriate bid for the player in the head round.
  public static int idealHeadBid(GameState state, Card[] opponentCards) {
    System.out.println("The current Trump Card is: " + state.trumpCard);

    // Print out opponent cards
    System.out.println("\nThe opponent cards are:");
    for (int i = 0; i < opponentCards.length; i++) {
      if (opponentCards[i] == null) {
        System.out.print("You: ??");
      } else {
        System.out.print(state.playerNames[i] + ": " + opponentCards[i]);
      }
      if (i == state.currentLeader) {
        System.out.print(" (Leader)");
      }
      System.out.print("\n");
    }

    // Determine probability of having a better card than the current winner.
    int visibleCards = state.numPlayers;
    int remainingCards = 52 - visibleCards;
    float winChance = 0;

    // If the player is NOT the current leader:
    if (opponentCards[state.currentLeader] != null) {
      System.out.println("\nI am not the leader.\n");
      // Determine current winning card.
      Card winningCard = opponentCards[state.currentLeader];
      for (int i = 0; i < opponentCards.length; i++) {
        if (opponentCards[i] != null && Rules.isCardWinning(opponentCards[i], winningCard, state.trumpCard)) {
          winningCard = opponentCards[i];
        }
      }

      System.out.println("The current winning card is: " + winningCard);

      int betterCards = 0;

      // If the current best card is a trump, then the only better cards are higher
      // trumps.
      if (winningCard.getSuit() == state.trumpCard.getSuit()) {
        for (int i = winningCard.getRank().ordinal() + 1; i < 13; i++) {
          betterCards++;
        }
        if (state.trumpCard.getRank().ordinal() > winningCard.getRank().ordinal()) {
          betterCards--;
        }
      } else {
        // If the current best card is not a trump, any card of the same suit but higher
        // rank is better.
        for (int i = winningCard.getRank().ordinal() + 1; i < 13; i++) {
          betterCards++;
        }
        // Add all trumps to the better cards.
        betterCards += 12;
      }

      System.out.println("There are " + betterCards + " better cards.\n");

      winChance = (float) betterCards / remainingCards;
    } else {
      System.out.println("\nI am the leader.\n");
      // In the case where the player is the leader, they must determine their win
      // chance for each suit they could have.
      float[] winChanceBySuit = new float[4];
      float[] hasSuitChance = new float[4];

      for (Suit suit : Suit.values()) {
        System.out.println("Calculating win chance for suit: " + suit);
        int oppsWithSuit = 0;
        Rank highestRank = null;
        boolean oppHasTrump = false;
        // Look at the opponent cards.
        for (int i = 0; i < opponentCards.length; i++) {
          // If an opponent has a card in our suit, we count them.
          if (opponentCards[i] != null && opponentCards[i].getSuit() == suit) {
            oppsWithSuit++;
            // If that card is the highest rank we've seen in this suit, we update it.
            if (highestRank == null || opponentCards[i].getRank().ordinal() > highestRank.ordinal()) {
              highestRank = opponentCards[i].getRank();
            }
          }
          // If an opponent has a trump card, we note it.
          if (opponentCards[i] != null && opponentCards[i].getSuit() == state.trumpCard.getSuit()) {
            oppHasTrump = true;
          }
        }

        System.out
            .println("Opponents with suit: " + oppsWithSuit + ", Highest rank: " + highestRank + ", Opp has trump: "
                + oppHasTrump);

        // There are 13 cards in each suit, take away the ones we know about.
        int remainingCardsInSuit = 13 - oppsWithSuit;
        // If we are in trumps, then also take away the revealed trump card.
        if (suit == state.trumpCard.getSuit()) {
          remainingCardsInSuit--;
        }

        System.out.println("Remaining cards in suit: " + remainingCardsInSuit);

        // The chance we have this suit:
        hasSuitChance[suit.ordinal()] = (float) remainingCardsInSuit / remainingCards;

        System.out.println("Chance of having suit: " + hasSuitChance[suit.ordinal()]);

        // If we are not in trumps, and an opponent has trumps, then our win chance in
        // this suit is 0.
        if (suit != state.trumpCard.getSuit() && oppHasTrump) {
          winChanceBySuit[suit.ordinal()] = 0;
        } else {
          // Otherwise we just figure out how many cards are left that are higher than the
          // highest rank we saw.
          int betterCardsInSuit = 0;
          if (highestRank == null) {
            betterCardsInSuit = 13;
            if (suit == state.trumpCard.getSuit()) {
              betterCardsInSuit--;
            }
          } else {
            for (int i = highestRank.ordinal() + 1; i < 13; i++) {
              betterCardsInSuit++;
            }
            if (suit == state.trumpCard.getSuit() && state.trumpCard.getRank().ordinal() > highestRank.ordinal()) {
              betterCardsInSuit--;
            }
          }

          System.out.println("Number of better cards in suit: " + betterCardsInSuit);
          // The win chance assuming we have this suit is the number of better cards
          // divided by the remaining cards.
          winChanceBySuit[suit.ordinal()] = (float) betterCardsInSuit / remainingCardsInSuit;
        }
        System.out.println("Win chance in suit: " + winChanceBySuit[suit.ordinal()] + "\n");
      }

      // Calculate the final win chance.
      for (int i = 0; i < 4; i++) {
        winChance += winChanceBySuit[i] * hasSuitChance[i];
      }
    }
    System.out.println("The chance of winning is: " + winChance);

    // Determine EVs for bidding 0 or 1.
    float ev0 = (1 - winChance) * 7 + winChance;
    System.out.println("EV for bidding 0: " + ev0);
    float ev1 = winChance * 11;
    System.out.println("EV for bidding 1: " + ev1 + "\n");

    // Return the bid with the highest EV.
    if (ev0 > ev1) {
      return 0;
    } else {
      return 1;
    }
  }

  // This method calculates the appropriate bid for a player in a non-head 1
  // round.
  public static int idealOneBid(GameState state) {
    // TODO: Determine the chance that no-one else has a better card
    // (ie that all better cards are in the deck still).
    return 0;
  }

}
