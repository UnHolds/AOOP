package game.ui;

public interface IUiGameConfig {

    boolean isHost();
    String getIp();
    String getPort();

    String getPlayerName();
    void addPlayer(String name);
}
