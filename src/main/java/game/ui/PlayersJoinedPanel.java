package game.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PlayersJoinedPanel extends JPanel {

    private ArrayList<String> joinedPlayers;
    private ArrayList<JPanel> playerPanels;
    private Image unknown;
    private Image cat1;
    private Image cat2;
    private Image cat3;
    private Image cat4;

    public PlayersJoinedPanel(int width, int height){
        this.joinedPlayers = new ArrayList<>();
        this.playerPanels = new ArrayList<>();

        this.setMaximumSize(new Dimension(width, height));
        this.setOpaque(false);
        this.setLayout(new GridLayout(1,4));

        loadResources();

        for (int i = 0; i < 4; i++) {
            JPanel panel = anonymousPlayerPanel();
            this.add(panel);
            this.playerPanels.add(panel);
        }

    }

    private JPanel anonymousPlayerPanel(){
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(100,100));
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        JLabel name = new JLabel("No player");
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(name, c);

        JLabel image = new JLabel(new ImageIcon(unknown.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(image,c);

        return panel;
    }

    public void addPlayer(String name){
        this.joinedPlayers.add(name);

        this.revalidate();
        this.repaint();
    }

    // todo extract into resourceHandler
    private void loadResources(){
        try {
            unknown = ImageIO.read(getClass().getClassLoader().getResourceAsStream("question_mark2.png"));
            cat1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat1.png"));
            cat2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat2.png"));
            cat3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat3.png"));
            cat4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat4.png"));

        } catch (IOException e) {
            e.printStackTrace(); // TODO use another way of error handling
        }
    }
}
