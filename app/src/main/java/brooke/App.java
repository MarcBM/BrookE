package brooke;

import brooke.util.*;

public class App {

    public static void main(String[] args) {
        System.out.println("Welcome to CrystalBrook!");

        // int numPlayers = InputHandler.gatherNumPlayers();

        // int startingHandSize = InputHandler.gatherStartingHandSize(numPlayers);

        // CrystalBrook game = InputHandler.gatherGameType(numPlayers);

        int numPlayers = 5;
        int startingHandSize = 3;
        CrystalBrook game = new TerminalGame();

        GameManager gm = new GameManager(game, numPlayers, startingHandSize);

        gm.playGame();

        System.out.println("Game Over!");

        cleanup();
    }

    private static void cleanup() {
        InputHandler.closeScanner();
    }
}
