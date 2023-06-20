package game.ui;

import game.core.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class FieldPanel extends JPanel implements ActionListener, KeyListener {

    public static int TILE_SIZE = 50;
    //public static final int ROWS = 12;
    //public static final int COLUMNS = 18;

    private IGame game;

    public FieldPanel(IGame game){
        // calculate number of tiles, rows and columns depending on size TODO
        this.game = game;
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));

        for (int row = 0; row < game.getField().getRowCount(); row++) {
            for (int col = 0; col < game.getField().getColumnCount(); col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current y/x position
                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );
                }
            }
        }
    }

    private void drawSubways(Graphics g) {
        for (ISubway subway : game.getField().getSubways()) {
            for (IExit exit : subway.getExits()) {
                g.setColor(Color.BLACK);
                g.drawOval(
                        Math.round(exit.getPosition().getX() * TILE_SIZE),
                        Math.round(exit.getPosition().getY() * TILE_SIZE),
                        TILE_SIZE, TILE_SIZE
                );
            }
        }
    }

    private void drawPlayers(Graphics g) {
        for (IPlayer player : game.getPlayers()) {
            g.drawImage(
                    player.getImage(),
                    Math.round(player.getPosition().getX() * TILE_SIZE),
                    Math.round(player.getPosition().getY() * TILE_SIZE),
                    TILE_SIZE,
                    TILE_SIZE,
                    null
                    );
        }
    }

    private void drawMice(Graphics g){
        for(IMouse mouse : game.getMouses()){
            g.drawImage(
                    mouse.getImage(),
                    Math.round(mouse.getPosition().getX() * TILE_SIZE),
                    Math.round(mouse.getPosition().getY() * TILE_SIZE),
                    TILE_SIZE,
                    TILE_SIZE,
                    null
            );
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        TILE_SIZE = Math.min(this.getWidth() / game.getField().getColumnCount(), this.getHeight() / game.getField().getRowCount());
        drawBackground(g);
        drawSubways(g);
        drawMice(g);
        drawPlayers(g);


        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
