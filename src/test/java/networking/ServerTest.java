package networking;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

public class ServerTest {

    @Test
    @DisplayName("Just start the server and close the server when the server waits for clients")
    public void testStartServerAndStopServerWhenServerWaitsForClients() throws IOException {
        IServer server = new Server();
        server.start(18899);
        server.stop();
    }
}
