package game.ui;

public interface IUiGameConfig {

    boolean isHost();
    String getIp();
    int getPort();

    String getPlayerName();
    void addPlayer(String name);
}
