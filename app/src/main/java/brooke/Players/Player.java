package brooke.Players;

import brooke.*;
import brooke.GameObjects.*;

public abstract class Player {
  public static int _id = 0;
  protected int id;
  protected String name;
  protected Hand hand = null;

  public Player(String name) {
    this.id = _id++;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Hand getHand() {
    return hand;
  }

  public void newHand(int startingSize) {
    hand = new Hand(startingSize);
  }

  public void drawCard(Card card) {
    hand.addCard(card);
  }

  public boolean hasCards() {
    return !hand.isEmpty();
  }

  public abstract Card playCard(GameState state);

  public abstract int makeBid(GameState state);

  public abstract int makeHeadBid(GameState state, Card[] opponentCards);
}
