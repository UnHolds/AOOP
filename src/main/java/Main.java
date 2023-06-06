import game.ui.GameWindow;
import game.ui.IUiGameConfig;
import game.ui.UiGameConfig;
import networking.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        GameWindow gw = new GameWindow();
        gw.initWindow();
        IUiGameConfig uiGameConfig = gw.getGameConfig();
        uiGameConfig.isHost();
    }
}