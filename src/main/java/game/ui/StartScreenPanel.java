package game.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class StartScreenPanel extends JPanel implements ActionListener{

    private Image background;
    private Font moon_cheese;
    private Font cheese;
    private Color cheese_orange = new Color(255, 118, 13);
    private BlockingQueue<IUiGameConfig> gameConfigs;

    public PlayersJoinedPanel playersJoinedPanel;

    public StartScreenPanel(BlockingQueue<IUiGameConfig> gameConfigs){
        // panel configurations
        this.gameConfigs = gameConfigs;
        setPreferredSize(new Dimension(900, 600));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        loadResources();
        initializeStartScreenPanel();
    }

    private void initializeStartScreenPanel(){
        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(100, 0));
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

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));

        JButton connectToGameButton = createCenteredButtonWithBasicLayout("Connect to game");
        connectToGameButton.addActionListener(new ConnectToGameListener());
        this.add(connectToGameButton);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));

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

        JLabel gameConfigLabel = createBasicLabel("Game host:");

        JTextField ipTextField = new JTextField();
        ipTextField.setFont(cheese.deriveFont(25f));
        JLabel ipLabel = createBasicLabel("IP");

        JTextField portTextField = new JTextField();
        portTextField.setFont(cheese.deriveFont(25f));
        JLabel portLabel = createBasicLabel("Port");

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

        JPanel namePanel = createBasicInputWithLabel("Name:");
        this.add(namePanel);
        JTextField nameText = (JTextField) Arrays.stream(namePanel.getComponents()).filter(c -> c instanceof JTextField).findFirst().orElse(null);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));

        JButton connectButton = createCenteredButtonWithBasicLayout("Connect");
        connectButton.addActionListener(new ConnectToGameHostListener(ipTextField, portTextField, nameText));
        this.add(connectButton);

        this.revalidate();
        this.repaint();
    }

    private void showHostInformationInput(){
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
        JPanel portPanel = createBasicInputWithLabel("Port:");
        this.add(portPanel);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));
        JPanel namePanel = createBasicInputWithLabel("Name:");
        this.add(namePanel);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));

        JButton connectButton = createCenteredButtonWithBasicLayout("Host game");

        JTextField portText = (JTextField) Arrays.stream(portPanel.getComponents()).filter(c -> c instanceof JTextField).findFirst().orElse(null);
        JTextField nameText = (JTextField) Arrays.stream(namePanel.getComponents()).filter(c -> c instanceof JTextField).findFirst().orElse(null);
        String ip = "ttttt127.0.0.1ttt"; //TODO

        connectButton.addActionListener(new HostScreenListener(ip, portText, nameText));
        this.add(connectButton);

        this.revalidate();
        this.repaint();
    }

    private void showHostNewGame(String ip, String port, boolean showStartGameButton){

        // Remove the buttons from the panel
        Component[] componentList = this.getComponents();
        ArrayList<Box.Filler> fillers = new ArrayList<>();

        for(Component c : componentList){
            if(c instanceof JButton){
                this.remove(c);
            }
            if(c instanceof JPanel){
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

        this.playersJoinedPanel = new PlayersJoinedPanel(this.getWidth(), 100);
        this.add(this.playersJoinedPanel);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(25, 0));

        if(showStartGameButton) {
            JButton connectButton = createCenteredButtonWithBasicLayout("Start mouse catching");
            connectButton.addActionListener(new StartGameAsHostListener());
            this.add(connectButton);
        }

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

    private JLabel createBasicLabel(String labelText){
        JLabel label = new JLabel(labelText);
        label.setFont(cheese.deriveFont(25f));
        label.setForeground(cheese_orange);
        return label;
    }

    private JPanel createBasicInputWithLabel(String labelText){
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(this.getWidth(), 50));
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel label = createBasicLabel(labelText);

        JTextField textField = new JTextField();
        textField.setFont(cheese.deriveFont(25f));

        panel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 200));
        panel.add(label);
        panel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(5, 0));
        panel.add(textField);
        panel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 200));
        return panel;
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
            StartScreenPanel.this.showHostInformationInput();
        }
    }

    private class HostScreenListener implements ActionListener {

        private String ip;
        private JTextField portText;
        private JTextField nameText;
        public HostScreenListener(String ip, JTextField portText, JTextField nameText) {
            this.ip = ip;
            this.portText = portText;
            this.nameText = nameText;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            StartScreenPanel.this.gameConfigs.add(new UiGameConfig(true, ip, this.portText.getText(), nameText.getText(), StartScreenPanel.this));
            StartScreenPanel.this.showHostNewGame(this.ip, this.portText.getText(), true);
        }
    }

    private class ConnectToGameHostListener implements ActionListener{

        private JTextField ipText;
        private JTextField portText;
        private JTextField nameText;
        public ConnectToGameHostListener(JTextField ipText, JTextField portText, JTextField nameText) {
            this.ipText = ipText;
            this.portText = portText;
            this.nameText = nameText;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            StartScreenPanel.this.gameConfigs.add(new UiGameConfig(false, this.ipText.getText(), this.portText.getText(), this.nameText.getText(), StartScreenPanel.this));
            StartScreenPanel.this.showHostNewGame(this.ipText.getText(), this.portText.getText(), false);
        }
    }

    private class StartGameAsHostListener implements ActionListener{
        // TODO implement
        @Override
        public void actionPerformed(ActionEvent e) {
            StartScreenPanel.this.gameConfigs.add(new UiGameConfig());
        }
    }
}
