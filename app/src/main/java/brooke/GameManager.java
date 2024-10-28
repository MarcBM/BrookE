package brooke;

import java.util.ArrayList;

import brooke.GameObjects.*;
import brooke.Players.*;
import brooke.util.*;

public class GameManager {
  private ArrayList<Player> players;
  private CrystalBrook game;
  private GameState state;

  public GameManager(CrystalBrook game, int numPlayers, int startingHandSize) {
    this.game = game;
    players = new ArrayList<>(numPlayers);

    initialiseGame(numPlayers);

    String[] playerNames = new String[numPlayers];
    for (int i = 0; i < numPlayers; i++) {
      playerNames[i] = getPlayerById(i).getName();
    }

    state = new GameState(numPlayers, startingHandSize, playerNames);
  }

  public void initialiseGame(int numPlayers) {
    for (int i = 0; i < numPlayers; i++) {
      players.add(game.generateNewPlayer());
    }

    System.out.print("The Players are: ");
    for (Player player : players) {
      System.out.print(player.getName() + " (" + player.getId() + "), ");
    }
    System.out.println();
  }

  public void playGame() {
    while (!isGameOver()) {

      // printCurrentGameState();

      if (state.currentPhase == 0) {
        // Setup Phase
        newRound();
        // Ready to start the bidding.
        state.currentPhase = 1;
      } else if (state.currentPhase == 1) {
        // Bidding Phase
        if (state.handSize != 1) {
          // Regular Rounds
          makeBid(state.currentPlayer, getPlayerById(state.currentPlayer).makeBid(state));
        } else {
          // One Rounds
          if (state.currentRound < state.totalRounds / 2) {
            // Head Round
            makeHeadBids();
          } else {
            // "Regular" One Round
            makeOneRoundBids();
          }
        }

        // If we are back at the current leader, then we are ready to start playing.
        if (state.currentPlayer == state.currentLeader) {
          state.currentPhase = 2;
        }
      } else if (state.currentPhase == 2) {
        // Playing Phase
        playCard(state.currentPlayer, getPlayerById(state.currentPlayer).playCard(state));

        // If we are back at the current leader, then we have finished the trick
        if (state.currentPlayer == state.currentLeader) {
          finishPlay();
        }

        // If we have played the full number of tricks this hand, it is time to finish
        // the round.
        if (state.playedTricks == state.handSize) {
          finishRound();

          // Print the Score Sheet
          printScoreSheet();

          InputHandler.pressEnterToContinue();

          state.currentPhase = 0;
        }
      }
    }
  }

  private void newRound() {
    // Have the game setup the new round.
    game.setupRound(state, players);
    // Get the trump card from the game.
    state.trumpCard = game.getTrumpCard();
  }

  private void makeBid(int player, int bid) {
    // Apply Bid
    state.currentBids[player] = bid;

    // Advance current player
    state.currentPlayer = (state.currentPlayer + 1) % state.numPlayers;
  }

  private void makeHeadBids() {
    // Get all cards from player hands.
    Card[] allCards = new Card[state.numPlayers];

    for (int i = 0; i < state.numPlayers; i++) {
      allCards[i] = getPlayerById(i).getHand().cards.get(0);
    }

    // Collect bids from all players.
    int[] headBids = new int[state.numPlayers];

    for (int i = 0; i < state.numPlayers; i++) {
      // Filter out the current player's card.
      Card[] opponentCards = new Card[state.numPlayers];
      for (int j = 0; j < state.numPlayers; j++) {
        if (j != i) {
          opponentCards[j] = allCards[j];
        } else {
          opponentCards[j] = null;
        }
      }

      headBids[i] = getPlayerById(i).makeHeadBid(state, opponentCards);
    }

    // Set bids.
    for (int i = 0; i < state.numPlayers; i++) {
      makeBid(i, headBids[i]);
    }
  }

  private void makeOneRoundBids() {
    // Collect all bids.
    int[] bids = new int[state.numPlayers];

    for (int i = 0; i < state.numPlayers; i++) {
      bids[i] = getPlayerById(i).makeBid(state);
    }

    // Set bids.
    for (int i = 0; i < state.numPlayers; i++) {
      makeBid(i, bids[i]);
    }
  }

  private void playCard(int player, Card card) {
    // Apply the play
    state.currentPlay[player] = card;

    // Check if we need to update the current winning card/player
    if (Rules.isCardWinning(card, state.winningCard, state.trumpCard)) {
      state.winningCard = card;
      state.winningPlayer = player;
    }

    // Advance current player
    state.currentPlayer = (state.currentPlayer + 1) % state.numPlayers;
  }

  private void finishPlay() {
    // Give a trick to the winning player.
    state.currentTricks[state.winningPlayer]++;

    // Increment the number of tricks played this hand
    state.playedTricks++;

    // Print out finished trick information.
    printTrickInfo();

    // Update the current leader based on who won the trick.
    state.currentLeader = state.winningPlayer;
    state.currentPlayer = state.currentLeader;

    // Reset the current play ready for the next trick.
    state.currentPlay = new Card[state.numPlayers];
    state.winningCard = null;
    state.winningPlayer = -1;
  }

  private void finishRound() {
    // Add this round's data to the history
    // Player-Specific Data
    for (int i = 0; i < state.numPlayers; i++) {
      // Bids
      state.bidHistory[state.currentRound][i] = state.currentBids[i];
      // Tricks Won
      state.trickHistory[state.currentRound][i] = state.currentTricks[i];
      // Scores
      // Grab Current Score from last round, or if it's the first round, set to 0.
      int currentScore;
      if (state.currentRound == 0) {
        currentScore = 0;
      } else {
        currentScore = state.scoreHistory[state.currentRound - 1][i];
      }
      // Add this round's score to the current score, and save it in this round's
      // history.
      state.scoreHistory[state.currentRound][i] = Rules.calculateScore(state.currentBids[i], state.currentTricks[i])
          + currentScore;
    }

    // Game-Wide Data
    // Trump Card
    state.trumpCardHistory[state.currentRound] = state.trumpCard;
    // Dealer
    state.dealerHistory[state.currentRound] = state.currentDealer;
    // Bid Delta
    state.bidDeltaHistory[state.currentRound] = Rules.calculateBidDelta(state.currentBids, state.numPlayers,
        state.handSize);
    // Score Delta
    state.scoreDeltaHistory[state.currentRound] = Rules.calculateScoreDelta(state.scoreHistory[state.currentRound],
        state.numPlayers);

    // Increment the round counter
    state.currentRound++;

    // Update the hand size
    if (state.currentRound < state.totalRounds / 2) {
      state.handSize--;
    } else if (state.currentRound > state.totalRounds / 2) {
      state.handSize++;
    }

    // Update Player Positions
    state.currentDealer = (state.currentDealer + 1) % state.numPlayers;
    state.currentPlayer = (state.currentDealer + 1) % state.numPlayers;
    state.currentLeader = state.currentPlayer;

    // Reset round data to default values.
    state.currentBids = new int[state.numPlayers];
    for (int i = 0; i < state.numPlayers; i++) {
      state.currentBids[i] = -1;
    }
    state.currentTricks = new int[state.numPlayers];
    state.trumpCard = null;
    state.playedTricks = 0;
  }

  public boolean isGameOver() {
    return state.currentRound >= state.totalRounds;
  }

  private void printTrickInfo() {
    InputHandler.printNewSection();

    System.out.println(state.playerNames[state.winningPlayer] + " won the trick with the " + state.winningCard + "!\n");

    System.out
        .println(state.playerNames[state.currentLeader] + " lead the " + state.currentPlay[state.currentLeader]
            + ", " + state.trumpCard.getSuit() + " is trumps.\n");

    System.out.println("The played cards were:");
    for (int i = 0; i < state.numPlayers; i++) {
      System.out.println(state.playerNames[i] + ": " + state.currentPlay[i]);
    }

    System.out.println("\nThe Bids for this round are:");
    for (int i = 0; i < state.numPlayers; i++) {
      System.out.println(state.playerNames[i] + ": " + state.currentBids[i] + " (" + state.currentTricks[i] + ")");
    }

    int remainingTricks = state.handSize - state.playedTricks;
    if (remainingTricks == 1) {
      System.out.println("\nThere is " + remainingTricks + " trick left to play this hand.");
    } else {
      System.out.println("\nThere are " + remainingTricks + " tricks left to play this hand.");
    }
    InputHandler.pressEnterToContinue();
  }

  public void printScoreSheet() {
    int numPlayers = players.size();
    boolean isLargeGame = state.totalRounds >= 20;

    int totalRowWidth = 0;
    if (!isLargeGame) {
      totalRowWidth = 6;
    } else {
      totalRowWidth = 7;
    }
    totalRowWidth += 5 + (13 * numPlayers) + 5 + 7;

    // Print Top Row
    printScoreSheetHorizontalBar(totalRowWidth, "=");
    printScoreSheetBufferRow(numPlayers, isLargeGame, false);

    String topRow = "||";
    // Num Cards
    if (isLargeGame) {
      topRow += " N  |";
    } else {
      topRow += " N |";
    }
    // Trump Suit
    topRow += " T ||";
    // Per Player:
    for (int i = 0; i < numPlayers; i++) {
      String playerName = getPlayerById(i).getName();
      int playerNameLength = playerName.length();
      if (playerNameLength > 11) {
        playerName = playerName.substring(0, 11);
      } else {
        playerName = " " + playerName;
        for (int j = 0; j < 10 - playerNameLength; j++) {
          playerName += " ";
        }
      }
      topRow += playerName + "||";
    }
    // Bid Delta
    topRow += " bD |";
    // Score Delta
    topRow += " scD ||";
    System.out.println(topRow);

    printScoreSheetBufferRow(numPlayers, isLargeGame, false);
    printScoreSheetHorizontalBar(totalRowWidth, "=");
    // Print Row for each Game Round

    int maxHandSize = state.totalRounds / 2;

    for (int i = 0; i < state.totalRounds; i++) {
      if (i != 0) {
        printScoreSheetHorizontalBar(totalRowWidth, "-");
      }
      printScoreSheetBufferRow(numPlayers, isLargeGame, true);

      String roundRow = "||";

      // Num Cards (3 or 4 in a 10+ card game)
      int cards;
      if (i >= maxHandSize) {
        cards = i - maxHandSize + 1;
      } else {
        cards = maxHandSize - i;
      }
      roundRow += " " + cards;
      if (isLargeGame && cards < 10) {
        roundRow += "  |";
      } else {
        roundRow += " |";
      }
      // Trump Suit (3)
      if (state.trumpCardHistory[i] != null) {
        roundRow += " " + state.trumpCardHistory[i].getSuit().toShortString() + " ||";
      } else {
        roundRow += "   ||";
      }
      // Per Player: Bid (* if dealer) (5) | Score (5)
      for (int j = 0; j < numPlayers; j++) {
        int bid = state.bidHistory[i][j];
        int score = state.scoreHistory[i][j];
        String bidString = " ";
        if (bid == -1) {
          bidString += "    |";
        } else {
          if (bid < 10) {
            bidString += " ";
          }
          bidString += bid;
          if (state.dealerHistory[i] == j) {
            bidString += "*";
          } else {
            bidString += " ";
          }
          bidString += " |";
        }
        roundRow += bidString;

        String scoreString = " ";
        if (score == -1) {
          scoreString += "    ||";
        } else {
          if (score < 100) {
            scoreString += " ";
          }
          if (score < 10) {
            scoreString += " ";
          }
          scoreString += score + " ||";
        }
        roundRow += scoreString;
      }
      // Bid Delta (4)
      int bidDelta = state.bidDeltaHistory[i];
      if (bidDelta == -100) {
        roundRow += "    |";
      } else {
        roundRow += " ";
        if (bidDelta == 0) {
          roundRow += "=";
        } else if (bidDelta > 0) {
          roundRow += "+";
        }
        roundRow += bidDelta;
        if (Math.abs(bidDelta) < 10) {
          roundRow += " ";
        }
        roundRow += "|";
      }
      // Score Delta (5)
      int scoreDelta = state.scoreDeltaHistory[i];
      if (scoreDelta == -1) {
        roundRow += "     ||";
      } else {
        roundRow += " ";
        if (scoreDelta < 100) {
          roundRow += " ";
        }
        if (scoreDelta < 10) {
          roundRow += " ";
        }
        roundRow += scoreDelta + " ||";
      }

      System.out.println(roundRow);
      printScoreSheetBufferRow(numPlayers, isLargeGame, true);

    }

    printScoreSheetHorizontalBar(totalRowWidth, "=");

    // Final Row: Per Player: Placing (5) | Average (5)
    printScoreSheetBufferRow(numPlayers, isLargeGame, true);

    String finalRow = "||";
    // Num Cards
    if (isLargeGame) {
      finalRow += "    |";
    } else {
      finalRow += "   |";
    }
    // Trump Suit
    finalRow += "   ||";
    // Per Player:
    for (int i = 0; i < numPlayers; i++) {
      if (state.currentRound == 0) {
        finalRow += "     |     ||";
      } else {
        // Placing
        String placing = findPlacing(state.scoreHistory[state.currentRound - 1], i);
        if (placing.length() == 3) {
          finalRow += " ";
        }
        finalRow += placing + " |";
        // Average
        float average = findAverage(i);
        if (average < 10) {
          finalRow += " ";
        }
        finalRow += String.format("%.1f", average) + " ||";
      }
    }
    // Bid Delta
    finalRow += "    |";
    // Score Delta
    finalRow += "     ||";

    System.out.println(finalRow);

    printScoreSheetBufferRow(numPlayers, isLargeGame, true);
    printScoreSheetHorizontalBar(totalRowWidth, "=");

  }

  private void printScoreSheetHorizontalBar(int width, String c) {
    for (int i = 0; i < width; i++) {
      System.out.print(c);
    }
    System.out.println();
  }

  private void printScoreSheetBufferRow(int numPlayers, boolean isLargeGame, boolean splitPlayers) {
    String buffer = "||";
    // Num Cards
    if (isLargeGame) {
      buffer += "    |";
    } else {
      buffer += "   |";
    }
    // Trump Suit
    buffer += "   ||";
    // Per Player:
    for (int i = 0; i < numPlayers; i++) {
      if (splitPlayers) {
        buffer += "     |     ||";
      } else {
        buffer += "           ||";
      }
    }
    // Bid Delta
    buffer += "    |";
    // Score Delta
    buffer += "     ||";

    System.out.println(buffer);
  }

  private String findPlacing(int[] scores, int player) {
    int playerScore = scores[player];
    int numScoresAbovePlayer = 0;
    for (int score : scores) {
      if (score > playerScore) {
        numScoresAbovePlayer++;
      }
    }

    switch (numScoresAbovePlayer) {
      case 0:
        return "1st";
      case 1:
        return "2nd";
      case 2:
        return "3rd";
      case 3:
        return "4th";
      case 4:
        return "5th";
      case 5:
        return "6th";
      case 6:
        return "7th";
      case 7:
        return "8th";
      case 8:
        return "9th";
      case 9:
        return "10th";
      default:
        return "ERR";
    }
  }

  private float findAverage(int player) {
    int score = state.scoreHistory[state.currentRound - 1][player];
    int rounds = state.currentRound;
    return (float) score / rounds;
  }

  private void printCurrentGameState() {
    InputHandler.printNewSection();

    System.out.println("General Game Information:\n");
    System.out.println("Number of Players: " + state.numPlayers);
    System.out.println("Max Hand Size: " + state.totalRounds / 2);

    System.out.print("\n");

    String roundString;
    int maxHandSize = state.totalRounds / 2;
    int cards = state.handSize;
    if (state.currentRound >= maxHandSize) {
      roundString = "" + cards + " UP";
    } else {
      roundString = "" + cards + " DOWN";
    }

    System.out.println("Current Round: " + roundString);
    System.out.println("Current Dealer: " + getPlayerById(state.currentDealer).getName());
    System.out.println("Current Leader: " + getPlayerById(state.currentLeader).getName());

    String stateString;
    switch (state.currentPhase) {
      case 0:
        stateString = "SETUP";
        break;
      case 1:
        stateString = "BIDDING";
        break;
      case 2:
        stateString = "PLAYING";
        break;
      default:
        stateString = "ERROR";
        break;
    }
    System.out.println("\nCurrent Game State: " + stateString);
    System.out.println("Currently on: " + getPlayerById(state.currentPlayer).getName());

    // System.out.println("\nScore Sheet:\n");
    // printScoreSheet();
  }

  private Player getPlayerById(int id) {
    for (Player player : players) {
      if (player.getId() == id) {
        return player;
      }
    }
    return null;
  }
}
