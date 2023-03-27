package networking;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

public class ServerTest {

    @Test
    @DisplayName("Just start the server and close the server")
    public void testNormalStartStopOfServer() throws IOException {
        IServer server = new Server();
        server.start(18899);
    }
}
