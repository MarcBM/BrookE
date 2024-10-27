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
    state = new GameState(numPlayers, startingHandSize);

    initialiseGame(numPlayers);
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
      printCurrentGameState();
      // TODO
      state.finishRound();
    }
  }

  public boolean isGameOver() {
    return state.currentRound >= state.totalRounds;
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
      String playerName = getPlayerNameById(i);
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
      int bidDelta = state.deltaBidHistory[i];
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
      int scoreDelta = state.deltaScoreHistory[i];
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
        finalRow += " " + findPlacing(state.scoreHistory[state.currentRound - 1], i) + " |";
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
    int rounds = state.currentRound + 1;
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
    System.out.println("Current Dealer: " + getPlayerNameById(state.currentDealer));
    System.out.println("Current Leader: " + getPlayerNameById(state.currentLeader));

    String stateString;
    switch (state.currentPhase) {
      case 0:
        stateString = "BIDDING";
        break;
      case 1:
        stateString = "PLAYING";
        break;
      default:
        stateString = "ERROR";
        break;
    }
    System.out.println("\nCurrent Game State: " + stateString);
    System.out.println("Currently on: " + getPlayerNameById(state.currentPlayer));

    System.out.print("\n");
    printScoreSheet();
  }

  private String getPlayerNameById(int id) {
    for (Player player : players) {
      if (player.getId() == id) {
        return player.getName();
      }
    }
    return null;
  }
}
