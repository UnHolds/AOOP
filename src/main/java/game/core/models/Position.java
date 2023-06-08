package game.core.models;

import game.core.models.impl.Direction;

public record Position(float y, float x) {

    static float speed = 0.1f;

    public Position move(Direction direction) {
        switch (direction) {
            case UP:
                return up();
            case DOWN:
                return down();
            case LEFT:
                return left();
            case RIGHT:
                return right();
            case STOP:
                return stop();
        }
        return null;
    }

    public Position up() {
        return new Position(y - speed, x);
    }

    public Position down() {
        return new Position(y + speed, x);
    }

    public Position left() {
        return new Position(y, x - speed);
    }

    public Position right() {
        return new Position(y, x + speed);
    }

    public Position stop() {
        return this;
    }

}
