package brooke;

import java.util.ArrayList;

import brooke.GameObjects.*;
import brooke.Players.*;
import brooke.util.InputHandler;

public class TableGame implements CrystalBrook {

  int brookeID;
  Player brooke;

  public TableGame(int numPlayers) {
    brookeID = InputHandler.gatherBrookeSeat(numPlayers) - 1;
    brooke = null;
  }

  @Override
  public Player generateNewPlayer() {
    if (Player._id == brookeID) {
      brooke = new BrookEHuman("BrookE");
      return brooke;
    } else {
      return new Human(InputHandler.gatherPlayerName(Player._id + 1));
    }
  }

  @Override
  public void setupRound(GameState state, ArrayList<Player> players) {
    brooke.newHand(state.handSize);

    // Need to tell BrookE what cards are hers.
    InputHandler.printNewSection();
    System.out.println("Please enter BrookE's hand:");
    for (int i = 0; i < state.handSize; i++) {
      System.out.println("\nCard " + (i + 1) + ":");
      brooke.drawCard(InputHandler.cardResponse());
    }

    InputHandler.printNewSection();
    System.out.println("BrookE's hand is:");
    System.out.println(brooke.getHand() + "\n");

    System.out.println("Is this correct? (y/n)");
    if (!InputHandler.yesNoResponse()) {
      setupRound(state, players);
    }

    if (state.handSize == 1 && state.currentRound < state.totalRounds / 2) {
      // Need to tell BrookE what her opponent's cards are.
      InputHandler.printNewSection();
      System.out.println("Please enter the cards you can see:");
      for (Player player : players) {
        if (player != brooke) {
          player.newHand(1);
          System.out.println("\n" + player.getName() + "'s Card:");
          player.drawCard(InputHandler.cardResponse());
        }
      }

      InputHandler.printNewSection();
      System.out.println("The cards you can see are:");
      for (Player player : players) {
        if (player != brooke) {
          System.out.println(player.getName() + ": " + player.getHand().cards.get(0));
        }
      }
      System.out.println("\nIs this correct? (y/n)");
      if (!InputHandler.yesNoResponse()) {
        setupRound(state, players);
      }
    }
  }

  public Card getTrumpCard() {
    // Need to input the phyical trump card.
    InputHandler.printNewSection();
    System.out.println("Please enter the trump card:");
    return InputHandler.cardResponse();
  }

}
