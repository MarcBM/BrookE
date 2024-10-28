package brooke.GameObjects;

import org.junit.jupiter.api.Test;

import brooke.Players.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class PlayerTest {

  Player player;

  @BeforeEach
  void setUp() {
    player = new Human("BrookE");
  }

  @Test
  void newPlayer() {
    assertNotNull(player);
    assertEquals(player.getName(), "BrookE");
    assertNull(player.getHand());
  }

  @Test
  void newHand() {
    player.newHand(5);
    assertNotNull(player.getHand());
    assertFalse(player.hasCards());
  }

  @Test
  void drawCard() {
    player.newHand(5);
    Card card = new Card(Rank.ACE, Suit.SPADES);
    player.drawCard(card);
    assertTrue(player.hasCards());
  }
}
