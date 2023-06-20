package networking;

import game.core.handler.Direction;
import game.core.models.*;
import networking.server.IServerClient;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageFactory implements IMessageFactory{

    private INetworkEntity entity;

    public MessageFactory(INetworkEntity entity){
        this.entity = entity;
    }

    @Override
    public IMessage createGameTickUpdateMessage(long gameTick) {
        return new Message(this.entity.getId(), this.entity.getName(), gameTick);
    }

    @Override
    public IMessage createClientConnectMessage() {
        return new Message(MessageType.CLIENT_CONNECT, this.entity.getId(), this.entity.getName(), -1, null);
    }

    @Override
    public IMessage createConnectedClientsUpdateMessage(List<IServerClient> clients){
        String data = "";

        for(IServerClient client : clients){
            String base64Name = Base64.getEncoder().encodeToString(client.getName().getBytes());
            data += client.getId() + "|" + base64Name + "#";
        }

        data = data.substring(0, data.length()-1);

        return new Message(MessageType.CONNECTED_CLIENTS_UPDATE, this.entity.getId(), this.entity.getName(), -1, data);
    }

    @Override
    public IMessage createGameFieldUpdateMessage(long gameTick, List<IPlayer> players, List<IMouse> mice){

        String playersData = "";

        for(IPlayer player : players){
            playersData += player.getId() + "~";
            playersData += player.getPosition().getX() + "|" + player.getPosition().getY();
            playersData += "#";
        }

        playersData = playersData.length() > 0 ? playersData.substring(0, playersData.length()-1) : "";

        String miceData = "";

        for(IMouse mouse : mice){
            miceData += mouse.getId() + "~";
            miceData += mouse.getPosition().getX() + "|" + mouse.getPosition().getY();
            miceData += "#";
        }

        miceData = miceData.length() > 0 ? miceData.substring(0, miceData.length()-1) : "";

        String data = playersData + "@" + miceData;


        return new Message(MessageType.GAME_FIELD_UPDATE, this.entity.getId(), this.entity.getName(), gameTick, data);
    }

    @Override
    public IMessage createGameInitMessage(List<ISubway> subways, int rowCount, int colCount) {

        String data = "";

        for(ISubway subway : subways){
            List<IExit> exits = subway.getExits();
            for(IExit exit : exits){
                data += exit.getPosition().getX() + "|" + exit.getPosition().getY();
                data += "#";
            }

            data = data.substring(0, data.length() - 1);
            data += "@";
        }

        data = data.length() > 0 ? data.substring(0, data.length() - 1) : "";

        data += "$";

        //returns the mice
        Set<String> filterSet = new HashSet<>();
        List<IMouse> mice = subways.stream().map(s -> s.getMice()).flatMap(List::stream).filter(m -> filterSet.add(m.getId())).collect(Collectors.toList());

        for(IMouse mouse : mice){
            data += mouse.getId() + "#" + "mouse.png" + "@";
        }

        if(mice.size() > 0){
            data = data.length() > 0 ? data.substring(0, data.length() - 1) : "";
        }


        data += "$" + rowCount + "#" + colCount;

        return new Message(MessageType.GAME_FIELD_INIT, this.entity.getId(), this.entity.getName(), -1, data);
    }

    @Override
    public IMessage createCatDirectionChangeMessage(long gameTick, ICharacter player, Direction direction) {
        return new Message(MessageType.CAT_DIRECTION_CHANGE, this.entity.getId(), this.entity.getName(), gameTick, player.getId() + "|" + direction.ordinal());
    }

    @Override
    public IMessage createCatEatMouseMessage(long gameTick, IPlayer player, IMouse mouse) {
        return new Message(MessageType.CAT_EAT_MOUSE_MESSAGE, this.entity.getId(), this.entity.getName(), gameTick, player.getId() + "|" + mouse.getId());
    }


}
