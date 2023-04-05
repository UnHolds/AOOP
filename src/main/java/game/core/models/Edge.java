package game.core.models;

public class Edge {
    boolean safe;
    int src, dest, weight;

    public Edge(int src, int dest, int weight, boolean safe) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this. safe = safe;
    }
}
