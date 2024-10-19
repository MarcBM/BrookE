package brooke.GameObjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class DeckTest {

  Deck deck;

  @BeforeEach
  void setUp() {
    deck = new Deck();
  }

  @Test
  void deckSize() {
    assertEquals(deck.size(), 52);
  }

  @Test
  void dealCard() {
    Card card = deck.dealCard();
    assertNotNull(card);
    assertEquals(deck.size(), 51);

    Card card2 = deck.dealCard();
    assertNotNull(card2);
    assertEquals(deck.size(), 50);
    assertNotEquals(card, card2);
  }

  @Test
  void drawSpecificCard() {
    Card card = new Card(Rank.ACE, Suit.SPADES);
    assertTrue(deck.drawSpecificCard(card));
    assertEquals(deck.size(), 51);
    assertFalse(deck.drawSpecificCard(card));
  }

  @Test
  void isEmpty() {
    assertFalse(deck.isEmpty());
    for (int i = 0; i < 52; i++) {
      deck.dealCard();
    }
    assertTrue(deck.isEmpty());
  }
}
