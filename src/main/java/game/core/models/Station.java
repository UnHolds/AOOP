package game.core.models;

public class Station {
    int value, weight;
    boolean safe;

    public Station(int value, int weight, boolean safe) {
        this.value = value;
        this.weight = weight;
        this.safe = safe;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return this.value + " (" + this.weight + ")";
    }
}
