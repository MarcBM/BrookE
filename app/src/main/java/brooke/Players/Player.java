package brooke.Players;

import brooke.*;
import brooke.GameObjects.*;

public abstract class Player implements Comparable<Player> {
  public static int _id = 0;
  private int id;
  private String name;
  private Hand hand = null;
  private int score = 0;
  private int tricks = 0;
  protected int bid = -1;

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

  public int getScore() {
    return score;
  }

  public int getTricks() {
    return tricks;
  }

  public int getBid() {
    return bid;
  }

  public void newHand(int startingSize) {
    hand = new Hand(startingSize);
    tricks = 0;
    bid = -1;
  }

  public void drawCard(Card card) {
    hand.addCard(card);
  }

  public abstract Card playCard(GameState state);

  public void addScore(int points) {
    score += points;
  }

  public abstract int makeBid(GameState state);

  public void addTrick() {
    tricks++;
  }

  public boolean hasCards() {
    return !hand.isEmpty();
  }

  public boolean hasBid() {
    return bid != -1;
  }

  @Override
  public int compareTo(Player other) {
    if (this.score == other.score) {
      return this.id - other.id;
    }
    return this.score - other.score;
  }
}
