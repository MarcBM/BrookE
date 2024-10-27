package brooke;

import java.util.ArrayList;

import brooke.GameObjects.*;
import brooke.Players.*;

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
        roundRow += " " + state.trumpCardHistory[i].getSuit().toString().charAt(0) + " ||";
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

  private void printCurrentGameState() {

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
