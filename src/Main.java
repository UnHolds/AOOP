import networking.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Server().start(19987, 2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}