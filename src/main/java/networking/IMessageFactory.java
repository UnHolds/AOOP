package networking;

public interface IMessageFactory {

    IMessage createGameTickUpdateMessage(long gameTick);

    IMessage createCatPositionMessage(long gameTick, int x, int y, int z);
}
