package game.ui;

import game.core.models.IPlayer;
import game.core.models.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class WinScreenPanel extends JPanel implements ActionListener {
    private Image background;
    private Font moon_cheese;
    private Font cheese;
    private Color cheese_orange = new Color(255, 118, 13);
    private ArrayList<Image> catImages;
    private Image podium;

    private ArrayList<IPlayer> playerScoreInfo;

    public WinScreenPanel(){
        setPreferredSize(new Dimension(900, 600));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.catImages = new ArrayList<>();

        loadResources();
        // TODO get winner name and winner_info here or make it part of the constructor

        // IMPORTANT winner info list must be sorted

        // TODO this is test data - pls remove when changed to game logic
        playerScoreInfo = new ArrayList<>();
        Player alice = new Player(3, "Alice");
        alice.incrementScore();
        alice.incrementScore();
        alice.incrementScore();
        alice.incrementScore();
        playerScoreInfo.add(alice);

        Player bob = new Player(1, "Bob");
        bob.incrementScore();
        bob.incrementScore();
        bob.incrementScore();
        playerScoreInfo.add(bob);

        Player bob2 = new Player(2, "Bob");
        bob2.incrementScore();
        bob2.incrementScore();
        playerScoreInfo.add(bob2);

        playerScoreInfo.add(new Player(4, "Eve")); // TODO remove end

        initializeWinScreenPanel("winnername");
    }

    private void initializeWinScreenPanel(String winnername){
        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(50, 0));
        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setText("Congrats");
        welcomeLabel.setFont(moon_cheese.deriveFont(70f));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setAlignmentX(this.CENTER_ALIGNMENT);
        this.add(welcomeLabel);

        JLabel welcomeLabel2 = new JLabel();
        welcomeLabel2.setText(winnername + " won!");
        welcomeLabel2.setFont(moon_cheese.deriveFont(70f));
        welcomeLabel2.setForeground(Color.BLACK);
        welcomeLabel2.setAlignmentX(this.CENTER_ALIGNMENT);
        this.add(welcomeLabel2);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(10, 0));

        JPanel podium = podiumPanel(300, 250);
        this.add(podium);

        this.add(createFillerWithSameValueForMinMaxAndPreferredDimension(40, 0));

        JPanel scoreBoard = new JPanel();
        scoreBoard.setLayout(new BoxLayout(scoreBoard, BoxLayout.LINE_AXIS));
        scoreBoard.setOpaque(false);

        for (int i = 0; i < playerScoreInfo.size(); i++) {
            String text = (i+1) + ". " + playerScoreInfo.get(i).getName() + " - " + playerScoreInfo.get(i).getScore();
            if(i <= 2){
                text += " *** ";
            }
            scoreBoard.add(createBasicLabel(text, Color.black));
        }

        this.add(scoreBoard);
    }

    @Override
    protected void paintComponent(Graphics g)     {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null); // image scaled
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

        // Name
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(createBasicLabel(player.getName(), Color.black), c);

        // Image
        JLabel image = new JLabel(new ImageIcon(catImages.get(player.getGameUIId() - 1).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(image,c);

        return panel;
    }

    private JPanel podiumPanel(int width, int height){
        JPanel podiumPanel = new JPanel();
        podiumPanel.setMaximumSize(new Dimension(width, height));
        podiumPanel.setOpaque(false);
        podiumPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // 1st place
        JPanel firstPlace = playerPanel(playerScoreInfo.get(0));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        podiumPanel.add(firstPlace,c);

        // 2nd place
        JPanel secondPlace = playerPanel(playerScoreInfo.get(1));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        podiumPanel.add(secondPlace,c);

        // 3rd place
        JPanel thirdPlace = playerPanel(playerScoreInfo.get(2));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        podiumPanel.add(thirdPlace,c);

        // Winner podium
        JLabel podiumImage = new JLabel(new ImageIcon(podium.getScaledInstance(300, 150, Image.SCALE_SMOOTH)));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        podiumPanel.add(podiumImage,c);

        return podiumPanel;
    }

    // UTIL
    // TODO move to another class that handles resources?
    private void loadResources(){
        try {
            background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("cheese_background.png"));
            moon_cheese = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("MoonCheese-Regular2.ttf"));
            cheese = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("cheeseusauceu.ttf"));
            catImages.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat1.png")));
            catImages.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat2.png")));
            catImages.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat3.png")));
            catImages.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat4.png")));
            podium = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Podest.png"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace(); // TODO use another way of error handling
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
