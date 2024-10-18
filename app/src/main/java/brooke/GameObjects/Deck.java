package brooke.GameObjects;

public class Deck {
  private Card[] cards;
  private int topCardIndex;

  public Deck() {
    cards = new Card[52];
    topCardIndex = 0;
    int i = 0;
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        cards[i] = new Card(rank, suit);
        i++;
      }
    }
  }

  public void shuffle() {
    for (int i = 0; i < cards.length; i++) {
      int randomIndex = (int) (Math.random() * cards.length);
      Card temp = cards[i];
      cards[i] = cards[randomIndex];
      cards[randomIndex] = temp;
    }
  }

  public Card dealCard() {
    return cards[topCardIndex++];
  }

  public boolean hasMoreCards() {
    return topCardIndex < cards.length;
  }

  public void reset() {
    topCardIndex = 0;
  }
}
