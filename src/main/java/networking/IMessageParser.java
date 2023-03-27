package networking;

public interface IMessageParser {

    IMessage parse(byte[] data);
}
