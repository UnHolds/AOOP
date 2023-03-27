package networking;

import java.net.Socket;

public class ServerClient {

    private Socket client;
    public ServerClient(Socket client){
        this.client = client;
    }
}
