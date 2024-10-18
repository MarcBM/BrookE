package brooke.GameObjects;

public class Card implements Comparable<Card> {
  private Rank rank;
  private Suit suit;

  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public Rank getRank() {
    return rank;
  }

  public Suit getSuit() {
    return suit;
  }

  @Override
  public int compareTo(Card other) {
    return rank.compareTo(other.getRank());
  }

  public String toString() {
    return rank + " of " + suit;
  }
}
