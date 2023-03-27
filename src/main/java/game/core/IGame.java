package game.core;

/***
 * This is the games starting point (Main)
 * IGame puts all the parts of the game together
 */
public interface IGame {
    /**
     * showStartScreen - shows the start screen with the options needed to start a new game
     */
    public void showStartScreen();

    /**
     * establishNetworkConnection - handles the initialization of the network connection and gives back an object to
     * manage the network connection
     */
    public void establishNetworkConnection();

    /**
     * initializeGameUI - initializes the UI and gives back an object to manage the UI
     */
    public void initializeGameUI();

    /**
     * initializeGameLogic - prepares the model objects for the characters (mice, cats), the game field and prepares the
     * scoring
     */
    public void initializeGameLogic();
}
