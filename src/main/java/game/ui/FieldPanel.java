package game.ui;

import game.core.models.IGame;
import game.core.models.Position;
import game.core.models.IPlayer;
import game.core.models.impl.Subway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

public class FieldPanel extends JPanel implements ActionListener, KeyListener {

    public static final int TILE_SIZE = 50;
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
        Map<Integer, Subway> subwayMap = game.getField().getSubways();
        int subs = subwayMap.size();
        for (int i = 0; i <subs; i++) {
            Subway sub =  subwayMap.get(i);
            for (Position exit : sub.getExits()) {
                g.setColor(Color.BLACK);
                g.drawOval(
                        exit.x(),
                        exit.y(),
                        TILE_SIZE, TILE_SIZE
                );
            }
        }
    }

    private void drawCharacters(Graphics g) {
        for (IPlayer player : game.getPlayers()) {
            g.drawImage(
                    player.getImage(),
                    player.getPosition().x() * TILE_SIZE,
                    player.getPosition().y() * TILE_SIZE,
                    TILE_SIZE,
                    TILE_SIZE,
                    null
                    );
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawSubways(g);
        drawCharacters(g);


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
