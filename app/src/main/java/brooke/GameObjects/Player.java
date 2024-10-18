package brooke.GameObjects;

public class Player {
  private String name;
  private Hand hand = null;

  public Player(String name) {
    this.name = name;
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

  public Card playCard(Card card) {
    return hand.playCard(card);
  }

  public boolean hasCards() {
    return !hand.isEmpty();
  }
}
