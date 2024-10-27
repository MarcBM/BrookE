package brooke;

import brooke.GameObjects.*;

public class GameState {
  public int numPlayers;
  public int currentPlayer;
  public int currentLeader;
  public int currentDealer;

  public int currentRound;
  public int totalRounds;
  public int handSize;

  public int[][] bidHistory;
  public int[][] trickHistory;
  public int[][] scoreHistory;

  public int[] currentBids;
  public int[] currentTricks;
  public Card trumpCard;

  public Card[] currentPlay;
  public Card winningCard;
  public int winningPlayer;

  public GameState(int numPlayers, int startingHandSize) {
    this.numPlayers = numPlayers;
    this.currentDealer = 0;
    this.currentPlayer = 1;
    this.currentLeader = 1;
    this.currentRound = 0;
    this.totalRounds = startingHandSize * 2;
    this.handSize = startingHandSize;

    this.bidHistory = new int[totalRounds][numPlayers];

    for (int i = 0; i < totalRounds; i++) {
      for (int j = 0; j < numPlayers; j++) {
        this.bidHistory[i][j] = -1;
      }
    }

    this.trickHistory = new int[totalRounds][numPlayers];
    this.scoreHistory = new int[totalRounds][numPlayers];
    this.currentBids = new int[numPlayers];

    for (int i = 0; i < numPlayers; i++) {
      this.currentBids[i] = -1;
    }

    this.currentTricks = new int[numPlayers];
  }

  public void newRound(Card trumpCard) {
    this.trumpCard = trumpCard;
  }

  public void finishRound() {
    for (int i = 0; i < numPlayers; i++) {
      this.bidHistory[this.currentRound][i] = this.currentBids[i];
      this.trickHistory[this.currentRound][i] = this.currentTricks[i];

      int currentScore;
      if (this.currentRound == 0) {
        currentScore = 0;
      } else {
        currentScore = this.scoreHistory[this.currentRound - 1][i];
      }

      this.scoreHistory[this.currentRound][i] = calculateScore(this.currentBids[i], this.currentTricks[i])
          + currentScore;
    }

    this.currentRound++;
    this.currentDealer = (this.currentDealer + 1) % this.numPlayers;
    this.currentPlayer = (this.currentDealer + 1) % this.numPlayers;
    this.currentLeader = this.currentPlayer;

    this.currentBids = new int[numPlayers];

    for (int i = 0; i < numPlayers; i++) {
      this.currentBids[i] = -1;
    }

    this.currentTricks = new int[numPlayers];

    this.trumpCard = null;
  }

  private int calculateScore(int bid, int tricks) {
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

  public void finishPlay() {
    this.currentLeader = this.winningPlayer;
    this.currentPlayer = this.winningPlayer;

    this.currentTricks[this.winningPlayer]++;

    this.currentPlay = new Card[this.numPlayers];
    this.winningCard = null;
    this.winningPlayer = -1;
  }

  public void playCard(int player, Card card) {
    this.currentPlay[player] = card;

    if (isCardWinning(this.winningCard, card)) {
      this.winningCard = card;
      this.winningPlayer = player;
    }
  }

  private boolean isCardWinning(Card oldWinner, Card newCard) {
    if (oldWinner == null) {
      return true;
    }

    if (newCard.getSuit() == this.trumpCard.getSuit() && oldWinner.getSuit() != this.trumpCard.getSuit()) {
      return true;
    }

    if (newCard.getSuit() == oldWinner.getSuit() && newCard.compareTo(oldWinner) > 0) {
      return true;
    }

    return false;
  }

}
