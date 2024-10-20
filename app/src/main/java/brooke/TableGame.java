package brooke;

import java.util.Scanner;

import brooke.GameObjects.*;

public class TableGame implements CrystalBrook {

  int myPlayerId;

  private Scanner scanner = new Scanner(System.in);

  public TableGame(int numPlayers) {
    System.out.println("Please enter which seat I am sitting in. (Seat 1 being the first dealer)");

    boolean valid = false;
    int seat;
    while (!valid) {
      try {
        seat = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid seat. Please try again.");
        continue;
      }
      if (seat >= 1 && seat <= numPlayers) {
        myPlayerId = seat - 1;
        valid = true;
      } else {
        System.out.println("Invalid seat. Please try again.");
      }
    }
  }

  @Override
  public Player generateNewPlayer(int id) {
    if (id == myPlayerId) {
      return new Player(id, "BrookE");
    } else {
      System.out.println(
          "Please enter the name of the player sitting in seat " + (id + 1) + ". (Seat 1 being the first dealer)");

      String name = scanner.nextLine();
      return new Player(id, name);
    }
  }

}
