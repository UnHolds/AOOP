package game.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FieldPanel extends JPanel implements ActionListener, KeyListener {

    // controls the size of the board
    private int tile_size;
    private int rows;
    private int columns;

    public FieldPanel(int width, int height){
        // TODO move to constructor if this should be changeable
        this.rows = 12;
        this.columns = 18;
        this.tile_size = 30;

        this.setPreferredSize(new Dimension(tile_size * columns, tile_size * rows));


        // calculate number of tiles, rows and columns depending on size TODO
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    g.fillRect(
                            col * tile_size,
                            row * tile_size,
                            tile_size,
                            tile_size
                    );
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

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
