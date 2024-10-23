package brooke;

import brooke.util.*;

public class App {

    public static void main(String[] args) {
        System.out.println("Welcome to CrystalBrook!");

        int numPlayers = InputHandler.gatherNumPlayers();

        int startingHandSize = InputHandler.gatherStartingHandSize(numPlayers);

        CrystalBrook game = InputHandler.gatherGameType(numPlayers);

        GameManager gm = new GameManager(game, numPlayers, startingHandSize);

        cleanup();
    }

    private static void cleanup() {
        InputHandler.closeScanner();
    }
}
