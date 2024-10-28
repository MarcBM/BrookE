package brooke.GameObjects;

import java.util.ArrayList;

public class Deck {
  private ArrayList<Card> cards;

  public Deck() {
    cards = new ArrayList<Card>(52);
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        cards.add(new Card(rank, suit));
      }
    }
  }

  public ArrayList<Card> getRemainingCards() {
    return cards;
  }

  public boolean isCardInDeck(Card card) {
    return cards.contains(card);
  }

  public Card dealCard() {
    return cards.remove((int) (Math.random() * cards.size()));
  }

  public boolean drawSpecificCard(Card card) {
    return cards.remove(card);
  }

  public boolean isEmpty() {
    return cards.isEmpty();
  }

  public int size() {
    return cards.size();
  }
}
