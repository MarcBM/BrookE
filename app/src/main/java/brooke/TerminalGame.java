package brooke;

import brooke.GameObjects.*;

public class TerminalGame implements CrystalBrook {

  @Override
  public Player generateNewPlayer() {
    return new Player("Player " + (Player._id));
  }

}
