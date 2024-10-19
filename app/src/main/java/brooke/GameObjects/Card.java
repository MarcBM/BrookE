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

  public static Card parseCard(String cardString) {
    String rankString;
    if (cardString.startsWith("1")) {
      rankString = cardString.substring(0, 2).toUpperCase();
    } else {
      rankString = cardString.substring(0, 1).toUpperCase();
    }

    String suitString = cardString.substring(cardString.length() - 1).toUpperCase();
    return new Card(Rank.parseRank(rankString), Suit.parseSuit(suitString));
  }

  @Override
  public int compareTo(Card other) {
    return rank.compareTo(other.getRank());
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Card) {
      Card otherCard = (Card) other;
      return rank == otherCard.getRank() && suit == otherCard.getSuit();
    }
    return false;
  }

  public String toString() {
    return rank + " of " + suit;
  }
}
