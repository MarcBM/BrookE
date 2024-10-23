package brooke;

import brooke.GameObjects.*;
import brooke.util.InputHandler;

public class TableGame implements CrystalBrook {

  int brookeID;

  public TableGame(int numPlayers) {
    brookeID = InputHandler.gatherBrookeSeat(numPlayers);
  }

  @Override
  public Player generateNewPlayer() {
    if (Player._id == brookeID) {
      return new Player("BrookE");
    } else {
      return new Player(InputHandler.gatherPlayerName(Player._id));
    }
  }

}
