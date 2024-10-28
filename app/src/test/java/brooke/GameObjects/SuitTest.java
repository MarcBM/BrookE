package brooke.GameObjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SuitTest {
  @Test
  void testSuit() {
    Suit suit = Suit.SPADES;
    assertEquals(suit.toString(), "SPADES");
    assertEquals(suit.ordinal(), 0);
  }

  @Test
  void testSuitValues() {
    Suit[] suits = Suit.values();
    assertEquals(suits[0], Suit.SPADES);
    assertEquals(suits[1], Suit.HEARTS);
    assertEquals(suits[2], Suit.DIAMONDS);
    assertEquals(suits[3], Suit.CLUBS);
  }

  @Test
  void testParseSuit() {
    Suit suit = Suit.parseSuit("S");
    assertEquals(suit, Suit.SPADES);

    suit = Suit.parseSuit("H");
    assertEquals(suit, Suit.HEARTS);

    suit = Suit.parseSuit("D");
    assertEquals(suit, Suit.DIAMONDS);

    suit = Suit.parseSuit("C");
    assertEquals(suit, Suit.CLUBS);

    assertThrows(IllegalArgumentException.class, () -> Suit.parseSuit("X"));
  }
}
