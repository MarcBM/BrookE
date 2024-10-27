package brooke;

import brooke.GameObjects.*;
import brooke.Players.*;

public class TerminalGame implements CrystalBrook {

  @Override
  public Player generateNewPlayer() {
    return new BrookEHuman("Player " + (Player._id + 1));
  }

}
