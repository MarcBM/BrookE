package brooke.GameObjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {
  @Test
  void testToString() {
    Card card = new Card(Rank.ACE, Suit.SPADES);
    assert card.toString().equals("ACE of SPADES");
  }

  @Test
  void cardHasRank() {
    Card card = new Card(Rank.ACE, Suit.SPADES);
    assertNotNull(card.getRank());
  }

  @Test
  void cardHasSuit() {
    Card card = new Card(Rank.ACE, Suit.SPADES);
    assertNotNull(card.getSuit());
  }

  @Test
  void cardCompare() {
    Card card1 = new Card(Rank.ACE, Suit.SPADES);
    Card card2 = new Card(Rank.ACE, Suit.SPADES);
    assert card1.compareTo(card2) == 0;

    Card card3 = new Card(Rank.KING, Suit.SPADES);
    assertTrue(card1.compareTo(card3) > 0);

    assertTrue(card3.compareTo(card1) < 0);
  }
}
