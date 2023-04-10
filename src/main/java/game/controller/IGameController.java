package game.controller;

/***
 * This is the games starting point (Main)
 * IGame puts all the parts of the game together
 */
public interface IGameController {
    /**
     * showStartScreen - shows the start screen with the options needed to start a new game
     */
    void showStartScreen();

    /**
     * establishNetworkConnection - handles the initialization of the network connection and gives back an object to
     * manage the network connection
     */
    void establishNetworkConnection();

    /**
     * initializeGameUI - initializes the UI and gives back an object to manage the UI
     */
    void initializeGameUI();

    /**
     * initializeGameLogic - prepares the model objects for the characters (mice, cats), the game field and prepares the
     * scoring
     */
    void initializeGameLogic();


    /**
     * showWinningScreen - shows the winning screen with a summary of the game
     */
    void showWinningScreen();


    /**
     * deconnectFromNetwork - deconnect from the network and in case of a host machine shut down server
     */
    void deconnectFromNetwork();

}
