package game.core.models;

public class Player implements IPlayer {
    private int score;
    private int gameUIId;
    private String name;

    public Player(int gameUIId, String name){
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
