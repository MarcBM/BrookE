package brooke;

import brooke.GameObjects.*;
import brooke.Players.*;
import brooke.util.InputHandler;

public class TableGame implements CrystalBrook {

  int brookeID;

  public TableGame(int numPlayers) {
    brookeID = InputHandler.gatherBrookeSeat(numPlayers) - 1;
  }

  @Override
  public Player generateNewPlayer() {
    if (Player._id == brookeID) {
      return new BrookEHuman("BrookE");
    } else {
      return new Human(InputHandler.gatherPlayerName(Player._id + 1));
    }
  }

}
