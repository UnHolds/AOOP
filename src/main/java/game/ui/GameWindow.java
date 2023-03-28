package game.ui;
import javax.swing.*;

public class GameWindow {

    private JFrame window;
    private StartScreenPanel startScreenPanel;

    public GameWindow(){
        this.window = new JFrame("Cat & Mouse Game");
    }

    public void initWindow() {
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);

        this.startScreenPanel = new StartScreenPanel();
        this.window.add(this.startScreenPanel);

        this.window.pack(); // fit the window size around the components
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
    }

    public void showGameFieldPanel() {
        GameFieldPanel gameFieldPanel = new GameFieldPanel();
        window.remove(this.startScreenPanel);
        window.add(gameFieldPanel);
        window.addKeyListener(gameFieldPanel);
        window.pack();
        window.setVisible(true);
    }

    public void showWinScreenPanel() {

    }
}
