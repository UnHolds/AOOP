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


    /**
     * returns the X position of the cat if the message is of the type CAT_POSITION otherwise it will return -1
     * @return X position of cat or -1
     */
    int getCatPositionX();

    /**
     * returns the Y position of the cat if the message is of the type CAT_POSITION otherwise it will return -1
     * @return Y position of cat or -1
     */
    int getCatPositionY();

    /**
     * returns the Z position of the cat if the message is of the type CAT_POSITION otherwise it will return -1
     * @return Z position of cat or -1
     */
    int getCatPositionZ();

    /**
     * returns the position of the cat as a array (x,y,z) if the message is of type CAT_POSITION otherwise the array
     * will be of size 0
     * @return (x, y, z) array of cat position or array with size 0
     */
    int[] getCatPosition();

}
