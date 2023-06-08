package game.core.handler;

import game.core.models.IPlayer;
import game.core.models.impl.Direction;
import networking.MessageFactory;
import networking.client.IClient;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveAction extends AbstractAction {


    private IPlayer player;
    private Direction direction;

    private IClient client;

    private MessageFactory messageFactory;

    private static boolean up = false;
    private static boolean down = false;
    private static boolean left = false;
    private static boolean right = false;


    public MoveAction(IPlayer p, Direction dir, IClient client){
        this.player = p;
        this.direction = dir;
        this.client = client;
        this.messageFactory = new MessageFactory(client);
    }

    private void sendMoveMessage(){
        this.client.sendMessage(this.messageFactory.createCatDirectionChangeMessage(0, this.player, this.direction));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(direction == Direction.UP && up == false){
            up = true;
            sendMoveMessage();
        }

        if(direction == Direction.UP_STOP && up == true){
            up = false;
            sendMoveMessage();
        }

        if(direction == Direction.DOWN && down == false){
            down = true;
            sendMoveMessage();
        }

        if(direction == Direction.DOWN_STOP && down == true){
            down = false;
            sendMoveMessage();
        }

        if(direction == Direction.LEFT && left == false){
            left = true;
            sendMoveMessage();
        }

        if(direction == Direction.LEFT_STOP && left == true){
            left = false;
            sendMoveMessage();
        }

        if(direction == Direction.RIGHT && right == false){
            right = true;
            sendMoveMessage();
        }

        if(direction == Direction.RIGHT_STOP && right == true){
            right = false;
            sendMoveMessage();
        }
    }
}
