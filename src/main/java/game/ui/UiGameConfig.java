package game.ui;

public class UiGameConfig implements IUiGameConfig {

    private boolean isHost;
    private String ip;
    private String port;

    private String playerName;

    public UiGameConfig(){

    }

    public UiGameConfig(boolean isHost, String ip, String port, String playerName){
        this.isHost = isHost;
        this.ip = ip;
        this.port = port;
        this.playerName = playerName;
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
