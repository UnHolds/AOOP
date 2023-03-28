package game.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartScreenPanel extends JPanel implements ActionListener{

    private Image background;
    private Font moon_cheese;
    private Font cheese;

    // controls the size of the board
    private static final int TILE_SIZE = 50;
    private static final int ROWS = 12;
    private static final int COLUMNS = 18;

    public StartScreenPanel(){
        // panel configurations
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        loadResources();
        initializeStartScreenPanel();
    }

    private void initializeStartScreenPanel(){
        this.add(new Box.Filler(new Dimension(5, 100), new Dimension(5, 100), new Dimension(5, 100)));
        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setText("Welcome to");
        welcomeLabel.setFont(moon_cheese.deriveFont(70f));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setAlignmentX(this.CENTER_ALIGNMENT);
        this.add(welcomeLabel);

        JLabel welcomeLabel2 = new JLabel();
        welcomeLabel2.setText("Cat and Mouse Game!");
        welcomeLabel2.setFont(moon_cheese.deriveFont(70f));
        welcomeLabel2.setForeground(Color.BLACK);
        welcomeLabel2.setAlignmentX(this.CENTER_ALIGNMENT);
        this.add(welcomeLabel2);

        this.add(new Box.Filler(new Dimension(5, 50), new Dimension(5, 50), new Dimension(5, 50)));

        JButton connectToGameButton = new JButton();
        connectToGameButton.setFont(cheese.deriveFont(25f));
        connectToGameButton.setForeground(new Color(255, 118, 13));
        connectToGameButton.setText("Connect to game");
        connectToGameButton.setBackground(Color.white);
        connectToGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectToGameButton.addActionListener(new ConnectToGameListener());
        this.add(connectToGameButton);

        this.add(new Box.Filler(new Dimension(5, 50), new Dimension(5, 50), new Dimension(5, 50)));

        JButton hostNewGameButton = new JButton();
        hostNewGameButton.setFont(cheese.deriveFont(25f));
        hostNewGameButton.setForeground(new Color(255, 118, 13));
        hostNewGameButton.setText("Host new game");
        hostNewGameButton.setBackground(Color.white);
        hostNewGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hostNewGameButton.addActionListener(new HostNewGameListener());
        this.add(hostNewGameButton);
    }

    private void showConnectToGame(){
        // Remove the buttons from the panel
        Component[] componentList = this.getComponents();

        for(Component c : componentList){
            if(c instanceof JButton){
                this.remove(c);
            }
        }

        this.revalidate();
        this.repaint();
    }

    private void showHostNewGame(){
        // Remove the buttons from the panel
        Component[] componentList = this.getComponents();

        for(Component c : componentList){
            if(c instanceof JButton){
                this.remove(c);
            }
        }

        JPanel hostConfigPanel = new JPanel();
        hostConfigPanel.setOpaque(false);
        hostConfigPanel.setLayout(new BoxLayout(hostConfigPanel, BoxLayout.LINE_AXIS));

        JLabel gameConfigLabel = new JLabel("Game host:");
        gameConfigLabel.setFont(cheese.deriveFont(20f));
        gameConfigLabel.setForeground(new Color(255, 118, 13));
        JTextField ipTextField = new JTextField();
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(cheese.deriveFont(20f));
        ipLabel.setForeground(new Color(255, 118, 13));
        JTextField portTextField = new JTextField();
        JLabel portLabel = new JLabel("Port");
        portLabel.setFont(cheese.deriveFont(20f));
        portLabel.setForeground(new Color(255, 118, 13));

        hostConfigPanel.add(gameConfigLabel);
        hostConfigPanel.add(ipTextField);
        hostConfigPanel.add(ipLabel);
        hostConfigPanel.add(portTextField);
        hostConfigPanel.add(portLabel);

        this.add(hostConfigPanel);

        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g)     {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null); // image scaled
    }

    // UTIL
    // TODO move to another class that handles resources?
    private void loadResources(){
        try {
            background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("cheese_background.png"));
            moon_cheese = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("MoonCheese-Regular2.ttf"));
            cheese = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("cheeseusauceu.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace(); // TODO use another way of error handling
        }
    }

    // EVENTS
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // ACTION LISTENERS FOR BUTTONS
    private class ConnectToGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StartScreenPanel.this.showConnectToGame();
        }
    }

    private class HostNewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StartScreenPanel.this.showHostNewGame();
        }
    }
}
