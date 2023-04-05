package game.core.models;

import java.util.ArrayList;
import java.util.List;

public class SubwayMap {
    List<List<Station>> adjList = new ArrayList<>();
    public SubwayMap(List<Edge> edges){
        int n = 0;
        for (Edge e: edges) {
            n = Integer.max(n, Integer.max(e.src, e.dest));
        }
        for (int i = 0; i <= n; i++) {
            adjList.add(i, new ArrayList<>());
        }
        for (Edge e: edges){
            adjList.get(e.src).add(new Station(e.dest, e.weight, e.safe));
            adjList.get(e.dest).add(new Station(e.src, e.weight, e.safe));
        }
    }
    public static void printMap(SubwayMap submap){
        int src = 0;
        int n = submap.getNodeAmount(submap);

        while (src < n){
            for (Station edge: submap.adjList.get(src)) {
                System.out.printf("%d ——> %s\t", src, edge);
            }
            System.out.println();
            src++;
        }
    }
    public int getNodeAmount(SubwayMap map){
        int n = map.adjList.size();
        return n;
    }

    public List<Integer> getStationExit(int pos){
        List<Integer> exits = new ArrayList<>();
        List<Station> exitsList = adjList.get(pos);
        if (exitsList != null){
            for (Station station : exitsList){
                if (station.getWeight() == 0){
                    exits.add(station.getValue());
                }
            }
        }
        return exits;
    }

    public List<Station> getSubways(int pos){
        List<Station> subway = new ArrayList<>();
        List<Station> subwayList = adjList.get(pos);
        if (subwayList != null){
            for (Station station : subwayList){
                if (station.getWeight() > 0){
                    subway.add(station);
                }
            }
        }
        return subway;
    }

    public Station getShortestMove(int pos, List<Integer> goalList){
        List<Station> subway = new ArrayList<>();
        List<Station> subwayList = adjList.get(pos);
        Station LastStop = null;
        if (subwayList != null) {
            for (Station station: subwayList) {
                if (goalList.contains(station.getValue())) {
                    subway.add(station);
                }
            }
        }
        if ( subway != null) {
            for (Station station: subway){
                if (LastStop == null){
                    LastStop = station;
                }
                if (LastStop.getWeight() > station.getWeight()){
                    LastStop = station;
                }
            }
        }
        return LastStop;
    }
}
