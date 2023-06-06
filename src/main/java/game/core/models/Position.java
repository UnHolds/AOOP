package game.core.models;

import game.core.models.impl.Direction;

public record Position(int y, int x) {

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
        return new Position(y -1, x);
    }

    public Position down() {
        return new Position(y +1, x);
    }

    public Position left() {
        return new Position(y, x -1);
    }

    public Position right() {
        return new Position(y, x +1);
    }

    public Position stop() {
        return this;
    }

}
