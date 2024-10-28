package brooke.GameObjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
  @Test
  void newHand() {
    Hand hand = new Hand(5);
    assertNotNull(hand);
    assertTrue(hand.isEmpty());
  }

  @Test
  void addCard() {
    Hand hand = new Hand(5);
    Card card = new Card(Rank.ACE, Suit.SPADES);
    hand.addCard(card);
    assertFalse(hand.isEmpty());
  }

  @Test
  void playCard() {
    Hand hand = new Hand(5);
    Card card = new Card(Rank.ACE, Suit.SPADES);
    hand.addCard(card);
    Card playedCard = hand.playCard(card);
    assertEquals(card, playedCard);
    assertTrue(hand.isEmpty());
  }
}
