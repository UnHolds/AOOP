package networking;

public interface IMessageFactory {

    public IMessage createGameTickUpdateMessage(long gameTick);
}
