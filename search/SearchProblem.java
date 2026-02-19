package search;

import java.util.ArrayList;
import subway.*;

public class SearchProblem extends Problem {

    private int d; // distance
    private SubwayMap map;

    public SearchProblem(State startStation) {
        super(startStation);
    }

    public SearchProblem(State startStation, State destination, SubwayMap map){
        super(startStation, destination);
        this.map = map;
    }

    public SearchProblem(State startStation, State destination, SubwayMap map, int d) {
        super(startStation, destination);
        this.map = map;
        this.d = d;
    }

    @Override
    public ArrayList<Tuple> successor(State state) {

        ArrayList<Tuple> successors = new ArrayList<>();

        // Convert search.State to subway.Station
        Station currentStation = map.getStationByName(state.getName());

        if (currentStation == null) {
            return successors; // no station found, return empty list
        }

        // Get adjacent stations from the SubwayMap
        ArrayList<Station> adjacent = map.adjacentStations(currentStation);

        for (Station neighbor : adjacent) {

            // Create a new State using the neighbor's name
            State nextState = new State(neighbor.name);

            // Create an Action describing the move
            Action action = new Action("Go to " + neighbor.name);

            // Add (action, state) pair
           successors.add(new Tuple(action, nextState));
        }

        return successors;
    }
}
