package brooke;

import brooke.GameObjects.*;

public class GameState {
  public int numPlayers;
  public String[] playerNames;
  public int currentPlayer;
  public int currentLeader;
  public int currentDealer;

  public int currentRound;
  public int totalRounds;
  public int handSize;

  public int[][] bidHistory;
  public int[][] trickHistory;
  public int[][] scoreHistory;

  public Card[] trumpCardHistory;
  public int[] dealerHistory;
  public int[] scoreDeltaHistory;
  public int[] bidDeltaHistory;

  public int[] currentBids;
  public int[] currentTricks;
  public Card trumpCard;
  public int playedTricks;

  public Card[] currentPlay;
  public Card winningCard;
  public int winningPlayer;

  public int currentPhase;

  public GameState(int numPlayers, int startingHandSize, String[] playerNames) {
    this.numPlayers = numPlayers;
    this.playerNames = playerNames;
    this.currentDealer = 0;
    this.currentPlayer = 1;
    this.currentLeader = 1;
    this.currentRound = 0;
    this.totalRounds = startingHandSize * 2;
    this.handSize = startingHandSize;

    this.bidHistory = new int[totalRounds][numPlayers];
    this.scoreHistory = new int[totalRounds][numPlayers];

    for (int i = 0; i < totalRounds; i++) {
      for (int j = 0; j < numPlayers; j++) {
        this.bidHistory[i][j] = -1;
        this.scoreHistory[i][j] = -1;
      }
    }

    this.trickHistory = new int[totalRounds][numPlayers];

    this.trumpCardHistory = new Card[totalRounds];
    this.dealerHistory = new int[totalRounds];
    this.scoreDeltaHistory = new int[totalRounds];
    this.bidDeltaHistory = new int[totalRounds];

    for (int i = 0; i < totalRounds; i++) {
      this.dealerHistory[i] = -1;
      this.scoreDeltaHistory[i] = -1;
      this.bidDeltaHistory[i] = -100;
    }

    this.currentBids = new int[numPlayers];

    for (int i = 0; i < numPlayers; i++) {
      this.currentBids[i] = -1;
    }

    this.currentTricks = new int[numPlayers];
    this.trumpCard = null;
    this.playedTricks = 0;
    this.currentPhase = 0;

    this.currentPlay = new Card[this.numPlayers];
    this.winningCard = null;
    this.winningPlayer = -1;
  }
}
