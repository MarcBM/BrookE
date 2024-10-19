package brooke.GameObjects;

public class Player {
  private String name;
  private Hand hand = null;
  private int score = 0;
  private int tricks = 0;

  public Player(String name) {
    this.name = name;
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

  public void newHand(int startingSize) {
    hand = new Hand(startingSize);
    tricks = 0;
  }

  public void drawCard(Card card) {
    hand.addCard(card);
  }

  public Card playCard(Card card) {
    return hand.playCard(card);
  }

  public void addScore(int points) {
    score += points;
  }

  public void addTrick() {
    tricks++;
  }

  public boolean hasCards() {
    return !hand.isEmpty();
  }
}
