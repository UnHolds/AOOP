package game.core.handler;

import game.core.models.IPlayer;
import networking.MessageFactory;
import networking.client.IClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class CharacterMovementController{


    private IClient client;
    private MessageFactory messageFactory;
    private IPlayer player;

    private static Logger log = LogManager.getLogger(CharacterMovementController.class);



    public CharacterMovementController(IClient client, IPlayer player){
        this.client = client;
        this.messageFactory = new MessageFactory(client);
        this.player = player;
    }

    public void registerKeyBinding(JComponent panel){

        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "MOVE_UP_PRESS");
        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "MOVE_UP_RELEASE");
        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "MOVE_LEFT_PRESS");
        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "MOVE_LEFT_RELEASE");
        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "MOVE_DOWN_PRESS");
        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "MOVE_DOWN_RELEASE");
        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "MOVE_RIGHT_PRESS");
        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "MOVE_RIGHT_RELEASE");

        panel.getActionMap().put("MOVE_UP_PRESS", new MoveAction(this.player, Direction.UP, this.client));
        panel.getActionMap().put("MOVE_UP_RELEASE", new MoveAction(this.player, Direction.UP_STOP, this.client));
        panel.getActionMap().put("MOVE_LEFT_PRESS", new MoveAction(this.player, Direction.LEFT, this.client));
        panel.getActionMap().put("MOVE_LEFT_RELEASE", new MoveAction(this.player, Direction.LEFT_STOP, this.client));
        panel.getActionMap().put("MOVE_DOWN_PRESS", new MoveAction(this.player, Direction.DOWN, this.client));
        panel.getActionMap().put("MOVE_DOWN_RELEASE", new MoveAction(this.player, Direction.DOWN_STOP, this.client));
        panel.getActionMap().put("MOVE_RIGHT_PRESS", new MoveAction(this.player, Direction.RIGHT, this.client));
        panel.getActionMap().put("MOVE_RIGHT_RELEASE", new MoveAction(this.player, Direction.RIGHT_STOP, this.client));
    }

}
