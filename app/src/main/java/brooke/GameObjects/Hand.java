package brooke.GameObjects;

import java.util.ArrayList;

public class Hand {
  public ArrayList<Card> cards;

  public Hand(int startingSize) {
    cards = new ArrayList<Card>(startingSize);
  }

  public void addCard(Card card) {
    cards.add(card);
  }

  public Card playCard(Card card) {
    cards.remove(card);
    return card;
  }

  public boolean hasCard(Card card) {
    return cards.contains(card);
  }

  public boolean isEmpty() {
    return cards.isEmpty();
  }

  @Override
  public String toString() {
    String hand = "";
    for (Card card : cards) {
      hand += card.toString() + ", ";
    }
    return hand;
  }
}
