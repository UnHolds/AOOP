package networking.server;

public interface IServerClient {

    void dispatch();

    Thread getThread();

    void stop();
}
