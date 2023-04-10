package game.ui;

import game.core.Position;
import game.core.models.Field;
import game.core.models.Subway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FieldPanel extends JPanel implements ActionListener, KeyListener {

    public static final int TILE_SIZE = 50;
    //public static final int ROWS = 12;
    //public static final int COLUMNS = 18;

    private Field field;

    public FieldPanel(Field field){
        // calculate number of tiles, rows and columns depending on size TODO
        this.field = field;
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));

        for (int row = 0; row < field.getRowCount(); row++) {
            for (int col = 0; col < field.getColumnCount(); col++) {
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        // Draw Subways
        for(Subway subway : field.getSubways()) {
            for(Position exit : subway.getExits()) {
                g.setColor(Color.BLACK);
                g.drawOval(
                        exit.column() * TILE_SIZE,
                        exit.row() * TILE_SIZE,
                        TILE_SIZE, TILE_SIZE
                );
            }
        }

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
