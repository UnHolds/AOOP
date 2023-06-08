package game.ui;

import game.core.models.IGame;
import game.core.models.impl.Game;
import game.core.models.IPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameFieldPanel extends JPanel implements ActionListener {

    private Image background;
    private Image keyLeft;
    private Image keyRight;
    private Image keyDown;
    private Image keyUp;
    private Font cheese;

    // controls the delay between each tick in ms
    private final int DELAY = 25;

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;

    private int windowWidth = 900;
    private int windowHeight = 600;
    private IGame game;

    private Map<String, JLabel> scoreLabels = new HashMap<>();

    public GameFieldPanel(IGame game) {
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.setLayout(new BorderLayout());
        this.game = game;


        loadResources();

        // initialize the game state
        initializeGameFieldPanel();

        // this timer will call the actionPerformed() method every DELAY ms
       // timer = new Timer(DELAY, this);
       // timer.start();
    }

    public void initializeGameFieldPanel(){
        this.add(playerInfoPanel(), BorderLayout.LINE_END);
        JPanel gameField = new FieldPanel(game);
        this.add(gameField, BorderLayout.CENTER);

        // TODO: readd manual panel
        //this.add(gameManualPanel(), c);
    }

    private JPanel gameManualPanel(){
        JPanel gameManualPanel = new JPanel();
        gameManualPanel.setMaximumSize(new Dimension(200,this.getHeight()));
        gameManualPanel.setOpaque(false);
        gameManualPanel.setLayout(new BoxLayout(gameManualPanel, BoxLayout.PAGE_AXIS));

        gameManualPanel.add(playingInstructionPanel(keyLeft, "Move left"));
        gameManualPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(40, 0));
        gameManualPanel.add(playingInstructionPanel(keyRight, "Move right"));
        gameManualPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(40, 0));
        gameManualPanel.add(playingInstructionPanel(keyUp, "Move up"));
        gameManualPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(40, 0));
        gameManualPanel.add(playingInstructionPanel(keyDown, "Move down"));

        return gameManualPanel;
    }

    private JPanel playerInfoPanel(){
        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setMaximumSize(new Dimension(100,this.getHeight()));
        playerInfoPanel.setOpaque(false);
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < game.getPlayers().size(); i++) {
            playerInfoPanel.add(playerPanel(game.getPlayers().get(i)));
            if (i <= 2){
                playerInfoPanel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));
            }
        }
        return playerInfoPanel;
    }

    // COMMON UI
    private Box.Filler createFillerWithSameValueForMinMaxAndPreferredDimension(int height, int width){
        return new Box.Filler(new Dimension(width, height), new Dimension(width, height), new Dimension(width, height));
    }

    private JLabel createBasicLabel(String labelText, Color color){
        JLabel label = new JLabel(labelText);
        label.setFont(cheese.deriveFont(25f));
        label.setForeground(color);
        return label;
    }

    private JPanel playerPanel(IPlayer player){
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(100,100));
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        // Score
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        JLabel score = createBasicLabel(Integer.toString(player.getScore()), Color.black);
        panel.add(score, c);
        this.scoreLabels.put(player.getId(), score);
        // Image
        JLabel image = new JLabel(new ImageIcon(player.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(image,c);

        return panel;
    }

    private JPanel playingInstructionPanel(Image image, String instruction){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(200, 100));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel imageLabel = new JLabel(new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        panel.add(imageLabel);
        panel.add(createFillerWithSameValueForMinMaxAndPreferredDimension(0, 10));
        JLabel instructionLabel = createBasicLabel(instruction, Color.black);
        panel.add(instructionLabel);
        return panel;
    }


    private void updateScoreBoard(){
        for(Map.Entry<String, JLabel> entry : scoreLabels.entrySet()){
            IPlayer p = this.game.getPlayers().stream().filter(pp -> pp.getId().equals(entry.getKey())).findFirst().orElse(null);
            entry.getValue().setText(p.getScore() + "");
        }
    }

    @Override
    protected void paintComponent(Graphics g)     {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null); // image scaled
        updateScoreBoard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // the state of your game or animation before the graphics are redrawn.

        //repaint();
    }

    // UTIL
    // TODO move to another class that handles resources?
    private void loadResources(){
        try {
            background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("cheese_background.png"));
            cheese = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("cheeseusauceu.ttf"));
            keyLeft = ImageIO.read(getClass().getClassLoader().getResourceAsStream("keyboard_left_key.png"));
            keyRight = ImageIO.read(getClass().getClassLoader().getResourceAsStream("keyboard_right_key.png"));
            keyUp = ImageIO.read(getClass().getClassLoader().getResourceAsStream("keyboard_up_key.png"));
            keyDown = ImageIO.read(getClass().getClassLoader().getResourceAsStream("keyboard_down_key.png"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace(); // TODO use another way of error handling
        }
    }

}
