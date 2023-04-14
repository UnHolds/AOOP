package game.core.models.impl;

import game.core.models.Position;
import game.core.models.IPlayer;

import java.awt.*;

public class Player extends Character implements IPlayer {
    private int score;
    private int gameUIId;
    private String name;

    private Image image;

    public Player(String id, int gameUIId, String name, Position startPosition, String imagePath){
        super(id, startPosition, imagePath);
        this.score = 0;
        this.gameUIId = gameUIId;
        this.name = name;
    }

    public void incrementScore() {
        this.score++;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int getGameUIId() {
        return this.gameUIId;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
