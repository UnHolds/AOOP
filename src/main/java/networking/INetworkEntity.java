package networking;

public interface INetworkEntity {

    /**
     * PRE: INetworkEntity is not null, id has been set previously
     * POST: Returns the id of the network entity
     *
     * @return id of network entity
     */
    String getId();

    /**
     * PRE: INetworkEntity is not null, name has been set previously
     * POST: Returns the name of the network entity
     *
     * @return name of the network entity
     */
    String getName();
}
