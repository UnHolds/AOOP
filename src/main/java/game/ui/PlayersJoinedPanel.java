package game.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PlayersJoinedPanel extends JPanel {

    private ArrayList<String> joinedPlayers;
    private ArrayList<JPanel> playerPanels;
    private ArrayList<Image> catImages;
    private Image unknown;

    private int nextEmptySlot;

    public PlayersJoinedPanel(int width, int height){
        this.joinedPlayers = new ArrayList<>();
        this.playerPanels = new ArrayList<>();
        this.catImages = new ArrayList<>();
        this.nextEmptySlot = 1;

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
        if (nextEmptySlot < 4){
            joinedPlayers.add(name);
            JPanel panel = playerPanels.get(nextEmptySlot);

            JLabel nameLabel = (JLabel) panel.getComponent(0);
            nameLabel.setText(name);

            panel.remove(1);
            JLabel image = new JLabel(new ImageIcon(catImages.get(nextEmptySlot).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.weighty = 0.5;
            c.gridx = 0;
            c.gridy = 1;
            panel.add(image,c);

            updateNextEmptySlot();

            this.revalidate();
            this.repaint();
        }
    }

    public void removePlayer(String name){
        if(joinedPlayers.contains(name)){
            for (int i = 0; i < joinedPlayers.size(); i++) {
                if (joinedPlayers.get(i).equals(name)){
                    joinedPlayers.remove(i);
                    playerPanels.set(i, anonymousPlayerPanel());
                }
            }
        }

        updateNextEmptySlot();

        this.revalidate();
        this.repaint();
    }

    private void updateNextEmptySlot(){
        for (int i = 0; i < joinedPlayers.size(); i++) {
            if (joinedPlayers.get(i) == null){
                nextEmptySlot = i;
            }
        }
    }

    // todo extract into resourceHandler
    private void loadResources(){
        try {
            unknown = ImageIO.read(getClass().getClassLoader().getResourceAsStream("question_mark2.png"));
            catImages.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat1.png")));
            catImages.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat2.png")));
            catImages.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat3.png")));
            catImages.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream("cat4.png")));

        } catch (IOException e) {
            e.printStackTrace(); // TODO use another way of error handling
        }
    }
}
