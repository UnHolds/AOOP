package game.core.models;

import game.core.models.impl.Direction;

public record Position(int row, int column) {
    public Position move(Direction direction) {
        switch (direction) {
            case UP -> {
                return up();
            }
            case DOWN -> {
                return down();
            }
            case LEFT -> {
                return left();
            }
            case RIGHT -> {
                return right();
            }
        }
        return null;
    }

    public Position up() {
        return new Position(row-1, column);
    }

    public Position down() {
        return new Position(row+1, column);
    }

    public Position left() {
        return new Position(row, column-1);
    }

    public Position right() {
        return new Position(row, column+1);
    }

}
