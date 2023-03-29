package game.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PlayersJoinedPanel extends JPanel {

    private ArrayList<String> joinedPlayers;
    private ArrayList<JLabel> playerIconLabels;
    private Image unknown;
    private Image cat1;
    private Image cat2;
    private Image cat3;
    private Image cat4;

    public PlayersJoinedPanel(int width, int height){
        this.joinedPlayers = new ArrayList<>();
        this.playerIconLabels = new ArrayList<>();

        this.setMaximumSize(new Dimension(width, height));
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setAlignmentX(JPanel.CENTER_ALIGNMENT);


        loadResources();

        for (int i = 0; i < 4; i++) {
            JLabel image = new JLabel(new ImageIcon(unknown.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            image.setMaximumSize(new Dimension(100,100));
            image.setHorizontalAlignment(JLabel.CENTER);
            image.setVerticalAlignment(JLabel.CENTER);
            this.add(image);
            this.playerIconLabels.add(image);
        }

    }

    public void addPlayer(String name){
        this.joinedPlayers.add(name);

        this.revalidate();
        this.repaint();
    }

    // todo extract into resourceHandler
    private void loadResources(){
        try {
            unknown = ImageIO.read(getClass().getClassLoader().getResourceAsStream("question_mark.png"));
        } catch (IOException e) {
            e.printStackTrace(); // TODO use another way of error handling
        }
    }
}
