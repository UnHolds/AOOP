package networking;

import java.io.IOException;

public interface IMessage {


    /**
     * converts the message to a base64 encoded string that can be sent via the network
     * this function is used by IClient and IServerClient for the communication.
     * @return base64 encoded representation of the message
     * @throws IOException
     */
    String toBase64String() throws IOException;

    /**
     * returns the type of the message
     * the types of messages are defined in the enum MessageType
     * @return the type of the message
     */
    MessageType getMessageType();

    /**
     * returns the id of the client that has sent the message to server
     * @return id of the client that has sent the message
     */
    String getSenderId();

    /**
     * returns the name of the client that has sent the message to server
     * @return name of the client that has sent the message
     */
    String getSenderName();

    /**
     * returns the currentGameTick stored in the message
     * @return the currentGameTick when the message was sent.
     */
    long getCurrentGameTick();

}
