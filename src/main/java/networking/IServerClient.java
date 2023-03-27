package networking;

public interface IServerClient {

    void dispatch();

    Thread getThread();

    void stop();
}
