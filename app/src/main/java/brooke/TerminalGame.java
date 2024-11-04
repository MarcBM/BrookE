package brooke;

import java.util.ArrayList;

import brooke.GameObjects.*;
import brooke.Players.*;

public class TerminalGame implements CrystalBrook {

  private Deck deck;

  @Override
  public Player generateNewPlayer() {
    return new BrookEBlindZero("Player " + (Player._id + 1));
  }

  public void setupRound(GameState state, ArrayList<Player> players) {
    deck = new Deck();
    for (Player player : players) {
      player.newHand(state.handSize);
      for (int i = 0; i < state.handSize; i++) {
        player.drawCard(deck.dealCard());
      }
    }
  }

  public Card getTrumpCard() {
    return deck.dealCard();
  }

}
