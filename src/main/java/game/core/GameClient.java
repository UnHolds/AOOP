package game.core;

import game.ui.GameWindow;
import game.ui.IUiGameConfig;
import networking.client.Client;
import networking.client.IClient;

import java.io.IOException;

public class GameClient {

    private IClient client;

    public GameClient(IClient client){
        this.client = client;
    }
}
