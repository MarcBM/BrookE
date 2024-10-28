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

  @Test
  void parseCard() {
    Card card = Card.parseCard("AS");
    assert card.getRank() == Rank.ACE;
    assert card.getSuit() == Suit.SPADES;

    Card card2 = Card.parseCard("10H");
    assert card2.getRank() == Rank.TEN;
    assert card2.getSuit() == Suit.HEARTS;

    Card card3 = Card.parseCard("2D");
    assert card3.getRank() == Rank.TWO;
    assert card3.getSuit() == Suit.DIAMONDS;

    Card card4 = Card.parseCard("Qd");
    assert card4.getRank() == Rank.QUEEN;
    assert card4.getSuit() == Suit.DIAMONDS;

    Card card5 = Card.parseCard("7c");
    assert card5.getRank() == Rank.SEVEN;
    assert card5.getSuit() == Suit.CLUBS;

    Card card6 = Card.parseCard("ah");
    assert card6.getRank() == Rank.ACE;
    assert card6.getSuit() == Suit.HEARTS;
  }
}
