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
    assertEquals(player.getScore(), 0);
    assertEquals(player.getTricks(), 0);
    assertNull(player.getHand());
  }

  @Test
  void newHand() {
    player.addTrick();
    player.newHand(5);
    assertNotNull(player.getHand());
    assertEquals(player.getTricks(), 0);
  }

  @Test
  void drawCard() {
    player.newHand(5);
    Card card = new Card(Rank.ACE, Suit.SPADES);
    player.drawCard(card);
    assertTrue(player.hasCards());
  }

  @Test
  void addScore() {
    player.addScore(5);
    assertEquals(player.getScore(), 5);
  }

  @Test
  void comparePlayers() {
    Player player2 = new Human("Player2");
    player.addScore(5);
    player2.addScore(10);
    assertTrue(player.compareTo(player2) < 0);

    player.addScore(5);

    assertTrue(player.compareTo(player2) < 0);

    player.addScore(5);

    assertTrue(player.compareTo(player2) > 0);
  }
}
