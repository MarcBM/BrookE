package brooke.util;

import java.util.ArrayList;

import brooke.GameObjects.*;
import brooke.*;

public class Rules {
  public static int calculateIllegalBid(int handSize, int[] bids) {
    int totalBids = 0;
    for (int bid : bids) {
      if (bid != -1) {
        totalBids += bid;
      }
    }
    return handSize - totalBids;
  }

  public static ArrayList<Card> calculateLegalPlays(Hand hand, GameState state, int thisPlayer) {
    ArrayList<Card> legalPlays = new ArrayList<Card>();
    if (state.currentLeader == thisPlayer) {
      legalPlays.addAll(hand.cards);
    } else {
      Suit leadSuit = state.currentPlay[state.currentLeader].getSuit();
      for (Card card : hand.cards) {
        if (card.getSuit() == leadSuit) {
          legalPlays.add(card);
        }
      }
      if (legalPlays.isEmpty()) {
        legalPlays.addAll(hand.cards);
      }
    }
    return legalPlays;
  }

  public static int calculateScore(int bid, int tricks) {
    if (bid == tricks) {
      if (bid == 0) {
        return 7;
      } else {
        return 10 + bid;
      }
    } else {
      return tricks;
    }
  }

  // Calculates what the difference between total bids and hand size is.
  // int[] bids is the bids made by each player, and is of length numPlayers.
  public static int calculateBidDelta(int[] bids, int numPlayers, int handSize) {
    int bidTotal = 0;
    for (int i = 0; i < numPlayers; i++) {
      bidTotal += bids[i];
    }
    return bidTotal - handSize;
  }

  // Calculates the difference between the highest and lowest score.
  // int[] scores is the scores of each player, and is of length numPlayers.
  public static int calculateScoreDelta(int[] scores, int numPlayers) {
    int minScore = Integer.MAX_VALUE;
    int maxScore = Integer.MIN_VALUE;

    for (int i = 0; i < numPlayers; i++) {
      if (scores[i] < minScore) {
        minScore = scores[i];
      }
      if (scores[i] > maxScore) {
        maxScore = scores[i];
      }
    }

    return maxScore - minScore;
  }

  public static boolean isCardWinning(Card newCard, Card oldWinner, Card trumpCard) {
    if (oldWinner == null) {
      return true;
    }

    if (newCard.getSuit() == trumpCard.getSuit() && oldWinner.getSuit() != trumpCard.getSuit()) {
      return true;
    }

    if (newCard.getSuit() == oldWinner.getSuit() && newCard.compareTo(oldWinner) > 0) {
      return true;
    }

    return false;
  }
}
