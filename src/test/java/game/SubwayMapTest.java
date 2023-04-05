package game;

import game.core.Game;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class SubwayMapTest {
    @Test
    @DisplayName("Initalize Game Logic!")
    public  void testInitalization() {
        Game game = new Game();
        game.initializeGameLogic();
    }
}
