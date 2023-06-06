import game.ui.GameWindow;
import game.ui.IUiGameConfig;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        GameWindow gw = new GameWindow();
        gw.initWindow();
        IUiGameConfig uiGameConfig = gw.getGameConfig();
        uiGameConfig.isHost();

        Thread.sleep(1000);
        uiGameConfig.addPlayer("test");
        Thread.sleep(1000);
        uiGameConfig.addPlayer("test1");
        Thread.sleep(1000);
        uiGameConfig.addPlayer("test2");
    }
}