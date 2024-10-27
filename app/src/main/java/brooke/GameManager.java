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
}
