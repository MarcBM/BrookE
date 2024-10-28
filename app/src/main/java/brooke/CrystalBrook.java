package brooke;

import java.util.ArrayList;

import brooke.GameObjects.*;
import brooke.Players.*;

public interface CrystalBrook {

  public Player generateNewPlayer();

  public void setupRound(GameState state, ArrayList<Player> players);

  public Card getTrumpCard();

}
