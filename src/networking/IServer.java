package networking;

import java.io.IOException;

public interface IServer extends Runnable{

    Thread start(int port, int numPlayers) throws IOException;
}
