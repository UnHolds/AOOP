package game.core.handler;

import game.core.GameClient;
import game.core.models.ICharacter;
import game.core.models.impl.Direction;
import game.core.models.impl.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CharacterMovementController implements KeyListener {
    private ICharacter target;
    private GameClient gameClient;

    public CharacterMovementController(ICharacter target, GameClient gameClient) {
        this.target = target;
        this.gameClient = gameClient;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction newDirection;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> newDirection = Direction.LEFT;
            case KeyEvent.VK_W -> newDirection = Direction.UP;
            case KeyEvent.VK_D -> newDirection = Direction.RIGHT;
            case KeyEvent.VK_S -> newDirection = Direction.DOWN;
            case KeyEvent.VK_SPACE -> newDirection = Direction.STOP;
            default -> {
                return;
            }
        }
        target.setMovingDirection(newDirection);
        gameClient.sendDirectionChange(target, newDirection);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
