package brooke;

import java.util.ArrayList;

import brooke.GameObjects.*;

public class GameManager {
  private ArrayList<Player> players;
  private CrystalBrook game;
  private int startingHandSize;
  private int currentHandSize;
  private boolean downRiver;

  public GameManager(CrystalBrook game, int numPlayers, int startingHandSize) {
    this.game = game;
    players = new ArrayList<>(numPlayers);
    this.startingHandSize = startingHandSize;
    currentHandSize = startingHandSize;
    downRiver = true;

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

  public boolean isGameOver() {
    return currentHandSize == startingHandSize && !downRiver;
  }
}
