package brooke.GameObjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RankTest {
  @Test
  void testRank() {
    Rank rank = Rank.ACE;
    assertEquals(rank.toString(), "ACE");
    assertEquals(rank.ordinal(), 12);
  }

  @Test
  void testRankValues() {
    Rank[] ranks = Rank.values();
    assertEquals(ranks[0], Rank.TWO);
    assertEquals(ranks[1], Rank.THREE);
    assertEquals(ranks[2], Rank.FOUR);
    assertEquals(ranks[3], Rank.FIVE);
    assertEquals(ranks[4], Rank.SIX);
    assertEquals(ranks[5], Rank.SEVEN);
    assertEquals(ranks[6], Rank.EIGHT);
    assertEquals(ranks[7], Rank.NINE);
    assertEquals(ranks[8], Rank.TEN);
    assertEquals(ranks[9], Rank.JACK);
    assertEquals(ranks[10], Rank.QUEEN);
    assertEquals(ranks[11], Rank.KING);
    assertEquals(ranks[12], Rank.ACE);
  }

  @Test
  void testParseRank() {
    Rank rank = Rank.parseRank("2");
    assertEquals(rank, Rank.TWO);

    rank = Rank.parseRank("3");
    assertEquals(rank, Rank.THREE);

    rank = Rank.parseRank("4");
    assertEquals(rank, Rank.FOUR);

    rank = Rank.parseRank("5");
    assertEquals(rank, Rank.FIVE);

    rank = Rank.parseRank("6");
    assertEquals(rank, Rank.SIX);

    rank = Rank.parseRank("7");
    assertEquals(rank, Rank.SEVEN);

    rank = Rank.parseRank("8");
    assertEquals(rank, Rank.EIGHT);

    rank = Rank.parseRank("9");
    assertEquals(rank, Rank.NINE);

    rank = Rank.parseRank("10");
    assertEquals(rank, Rank.TEN);

    rank = Rank.parseRank("J");
    assertEquals(rank, Rank.JACK);

    rank = Rank.parseRank("Q");
    assertEquals(rank, Rank.QUEEN);

    rank = Rank.parseRank("K");
    assertEquals(rank, Rank.KING);

    rank = Rank.parseRank("A");
    assertEquals(rank, Rank.ACE);

    assertThrows(IllegalArgumentException.class, () -> Rank.parseRank("X"));
  }
}
