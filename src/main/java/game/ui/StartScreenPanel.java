package game.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class StartScreenPanel extends JPanel implements ActionListener{

    private Image background;
    private Font moon_cheese;
    private Font cheese;
    private Color cheese_orange = new Color(255, 118, 13);

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

        JButton connectToGameButton = createCenteredButtonWithBasicLayout("Connect to game");
        connectToGameButton.addActionListener(new ConnectToGameListener());
        this.add(connectToGameButton);

        this.add(new Box.Filler(new Dimension(5, 50), new Dimension(5, 50), new Dimension(5, 50)));

        JButton hostNewGameButton = createCenteredButtonWithBasicLayout("Host new game");
        hostNewGameButton.addActionListener(new HostNewGameListener());
        this.add(hostNewGameButton);
    }

    private void showConnectToGame(){
        // Remove the buttons from the panel
        Component[] componentList = this.getComponents();
        ArrayList<Box.Filler> fillers = new ArrayList<>();

        for(Component c : componentList){
            if(c instanceof JButton){
                this.remove(c);
            }
            else if(c instanceof Box.Filler){
                fillers.add((Box.Filler)c);
            }
        }

        for (int i = 2; i < fillers.size(); i++) {
            this.remove(fillers.get(i));
        }

        JPanel hostConfigPanel = new JPanel();
        hostConfigPanel.setMaximumSize(new Dimension(this.getWidth(), 50));
        hostConfigPanel.setOpaque(false);
        hostConfigPanel.setLayout(new BoxLayout(hostConfigPanel, BoxLayout.LINE_AXIS));

        JLabel gameConfigLabel = new JLabel("Game host:");
        gameConfigLabel.setFont(cheese.deriveFont(25f));
        gameConfigLabel.setForeground(cheese_orange);

        JTextField ipTextField = new JTextField();
        ipTextField.setFont(cheese.deriveFont(25f));
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(cheese.deriveFont(25f));
        ipLabel.setForeground(cheese_orange);
        JTextField portTextField = new JTextField();
        portTextField.setFont(cheese.deriveFont(25f));
        JLabel portLabel = new JLabel("Port");
        portLabel.setFont(cheese.deriveFont(25f));
        portLabel.setForeground(cheese_orange);

        hostConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 20));
        hostConfigPanel.add(gameConfigLabel);
        hostConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(5, 0));
        hostConfigPanel.add(ipTextField);
        hostConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(5, 0));
        hostConfigPanel.add(ipLabel);
        hostConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 20));
        hostConfigPanel.add(portTextField);
        hostConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(5, 0));
        hostConfigPanel.add(portLabel);
        hostConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 20));

        this.add(hostConfigPanel);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));

        JPanel nameConfigPanel = new JPanel();
        nameConfigPanel.setMaximumSize(new Dimension(this.getWidth(), 50));
        nameConfigPanel.setOpaque(false);
        nameConfigPanel.setLayout(new BoxLayout(nameConfigPanel, BoxLayout.LINE_AXIS));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(cheese.deriveFont(25f));
        nameLabel.setForeground(cheese_orange);
        JTextField nameTextField = new JTextField();
        nameTextField.setFont(cheese.deriveFont(25f));

        nameConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 200));
        nameConfigPanel.add(nameLabel);
        nameConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(5, 0));
        nameConfigPanel.add(nameTextField);
        nameConfigPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 200));

        this.add(nameConfigPanel);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));

        JButton connectButton = createCenteredButtonWithBasicLayout("Connect");
        connectButton.addActionListener(new ConnectToGameHostListener());
        this.add(connectButton);

        this.revalidate();
        this.repaint();
    }

    private void showHostNewGame(){
        String ip = "127.0.0.1"; // TODO change via game logic
        String port = "1234"; // TODO change via game logic

        // Remove the buttons from the panel
        Component[] componentList = this.getComponents();
        ArrayList<Box.Filler> fillers = new ArrayList<>();

        for(Component c : componentList){
            if(c instanceof JButton){
                this.remove(c);
            }
            else if(c instanceof Box.Filler){
                fillers.add((Box.Filler)c);
            }
        }

        for (int i = 1; i < fillers.size(); i++) {
            this.remove(fillers.get(i));
        }

        JLabel letJoinLabel = new JLabel("Let your friends join:");
        letJoinLabel.setFont(cheese.deriveFont(25f));
        letJoinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(letJoinLabel);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 5));

        JPanel gameInfoPanel = new JPanel();
        gameInfoPanel.setMaximumSize(new Dimension(this.getWidth(), 50));
        gameInfoPanel.setOpaque(false);
        gameInfoPanel.setLayout(new BoxLayout(gameInfoPanel, BoxLayout.LINE_AXIS));

        JTextField hostInfoText = new JTextField();
        hostInfoText.setFont(cheese.deriveFont(25f));
        hostInfoText.setForeground(cheese_orange);
        hostInfoText.setBackground(Color.white);
        hostInfoText.setEditable(false);
        hostInfoText.setText("IP: " + ip + "    Port: " + port);
        hostInfoText.setHorizontalAlignment(JTextField.CENTER);
        hostInfoText.setCaretColor(Color.WHITE);

        gameInfoPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 100));
        gameInfoPanel.add(hostInfoText);
        gameInfoPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 100));
        this.add(gameInfoPanel);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(25, 5));

        JPanel playersJoinedPanel = new PlayersJoinedPanel(this.getWidth(), 100);
        this.add(playersJoinedPanel);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(25, 0));

        JButton connectButton = createCenteredButtonWithBasicLayout("Start mouse catching");
        connectButton.addActionListener(new StartGameAsHostListener());
        this.add(connectButton);

        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g)     {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null); // image scaled
    }


    // COMMON UI
    private JButton createCenteredButtonWithBasicLayout(String buttonText){
        JButton basicButton = new JButton();
        basicButton.setFont(cheese.deriveFont(25f));
        basicButton.setForeground(new Color(255, 118, 13));
        basicButton.setText(buttonText);
        basicButton.setBackground(Color.white);
        basicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return basicButton;
    }

    private Box.Filler createFillerWithSameValueForMinMaxAndPreferredDimension(int height, int width){
        return new Box.Filler(new Dimension(width, height), new Dimension(width, height), new Dimension(width, height));
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

    private class ConnectToGameHostListener implements ActionListener{
        // TODO implement
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class StartGameAsHostListener implements ActionListener{
        // TODO implement
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
