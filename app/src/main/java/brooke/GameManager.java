package brooke;

import java.util.ArrayList;

import brooke.GameObjects.*;

public class GameManager {
  private ArrayList<Player> players;
  private Deck deck;
  private CrystalBrook game;
  private int startingHandSize;

  public GameManager(CrystalBrook game, int numPlayers, int startingHandSize) {
    this.game = game;
    players = new ArrayList<>(numPlayers);
    deck = new Deck();
    this.startingHandSize = startingHandSize;

    initialiseGame(numPlayers);
  }

  public void initialiseGame(int numPlayers) {
    for (int i = 0; i < numPlayers; i++) {
      players.add(game.generateNewPlayer(i));
    }

    System.out.print("The Players are: ");
    for (Player player : players) {
      System.out.print(player.getName() + " ");
    }
    System.out.println();
  }
}
