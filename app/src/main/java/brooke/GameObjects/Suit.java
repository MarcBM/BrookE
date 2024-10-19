package brooke.GameObjects;

public enum Suit {
  SPADES, HEARTS, DIAMONDS, CLUBS;

  public static Suit parseSuit(String suitString) {
    switch (suitString) {
      case "S":
        return SPADES;
      case "H":
        return HEARTS;
      case "D":
        return DIAMONDS;
      case "C":
        return CLUBS;
      default:
        throw new IllegalArgumentException("Invalid suit: " + suitString);
    }
  }
}
