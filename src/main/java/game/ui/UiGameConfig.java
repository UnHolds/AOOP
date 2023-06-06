package game.ui;

public class UiGameConfig implements IUiGameConfig {

    private boolean isHost;
    private String ip;
    private String port;

    private String playerName;

    private StartScreenPanel startScreenPanel;

    public UiGameConfig(){

    }

    public UiGameConfig(boolean isHost, String ip, String port, String playerName, StartScreenPanel startScreenPanel){
        this.isHost = isHost;
        this.ip = ip;
        this.port = port;
        this.playerName = playerName;
        this.startScreenPanel = startScreenPanel;
    }

    @Override
    public boolean isHost() {
        return this.isHost;
    }

    @Override
    public String getIp() {
        return this.getIp();
    }

    @Override
    public String getPort() {
        return this.getPort();
    }

    @Override
    public String getPlayerName() {
        return this.playerName;
    }
}
