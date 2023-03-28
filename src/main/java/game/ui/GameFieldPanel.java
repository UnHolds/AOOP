package game.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFieldPanel extends JPanel implements ActionListener, KeyListener {

    // controls the delay between each tick in ms
    private final int DELAY = 25;

    // controls the size of the board
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 12;
    public static final int COLUMNS = 18;

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;


    public GameFieldPanel() {
        // set the game board size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board background color
        setBackground(new Color(232, 232, 232));

        // initialize the game state
        // TODO

        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // the state of your game or animation before the graphics are redrawn.

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO check which key was pressed here
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
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

}
