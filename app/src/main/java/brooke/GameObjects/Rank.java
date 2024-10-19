package brooke.GameObjects;

public enum Rank {
  TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

  public static Rank parseRank(String rankString) {
    switch (rankString) {
      case "2":
        return TWO;
      case "3":
        return THREE;
      case "4":
        return FOUR;
      case "5":
        return FIVE;
      case "6":
        return SIX;
      case "7":
        return SEVEN;
      case "8":
        return EIGHT;
      case "9":
        return NINE;
      case "10":
        return TEN;
      case "J":
        return JACK;
      case "Q":
        return QUEEN;
      case "K":
        return KING;
      case "A":
        return ACE;
      default:
        throw new IllegalArgumentException("Invalid rank: " + rankString);
    }
  }
}
