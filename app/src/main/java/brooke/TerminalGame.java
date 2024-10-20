package brooke;

import java.util.Scanner;

import brooke.GameObjects.*;

public class TerminalGame implements CrystalBrook {

  private Scanner scanner = new Scanner(System.in);

  @Override
  public Player generateNewPlayer(int id) {
    return new Player(id, "Player " + (id + 1));
  }

}
