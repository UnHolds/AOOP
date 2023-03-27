package networking;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.net.Socket;

public class ServerTest {

    @Test
    @DisplayName("Just start the server and close the server when the server waits for clients")
    public void testStartServerAndStopServerWhenServerWaitsForClients() throws IOException {
        IServer server = new Server();
        server.start(18899);
        server.stop();
    }

    @Test
    public void testStartServerAndStopServerWhenServerAcceptsOneClientAndThenWaitsForOtherClients() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(18899);
        new Socket("127.0.0.1", 18899);
        Thread.sleep(1000);
        server.stop();
    }

    @Test
    public void testStartServerAndConnectClientAndStartGameAndStopServer() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(18899);
        new Socket("127.0.0.1", 18899);
        Thread.sleep(500);
        server.startGame();
        Thread.sleep(500);
        server.stop();
    }
}
