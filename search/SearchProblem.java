package search;

import java.util.ArrayList;
import subway.*;

public class SearchProblem extends Problem {

    private double d; // distance
    private SubwayMap map;

    public SearchProblem(State startStation) {
        super(startStation);
    }

    public SearchProblem(State startStation, State destination, SubwayMap map){
        super(startStation, destination);
        this.map = map;
    }

    public SearchProblem(State startStation, State destination, SubwayMap map, double d) {
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

    @Override
    public double pathCost(double c, State state1, Action action, State state2){
        Station s1 = map.getStationByName(state1.getName());
        Station s2 = map.getStationByName(state2.getName());
		ArrayList<Link> linkList = map.getLinksBetween(s1, s2);

        double cost = c;

        for(Link l : linkList){
            cost += l.getDistance();
        }

        return cost;
        
        //return c + 1;
	}
    @Override
    public double h(Node node) {
        Station s1 = map.getStationByName(node.getState().getName());
        Station s2 = map.getStationByName(this.goal.getName());
        double distance = SubwayMap.straightLineDistance(s1, s2);
        return distance;
    }

    @Override
    public boolean goalTest(State state) {
        // check if it's an exact match first
        if (state.equals(this.goal)) {
            return true;
        }

        // if not, check if it's within the given walking distance
        Station s1 = map.getStationByName(state.getName());
        Station s2 = map.getStationByName(this.goal.getName());
    
        if (s1 != null && s2 != null) {
            double currentDist = SubwayMap.straightLineDistance(s1, s2);
            // if it is, it's a goal
            if (currentDist <= this.d) {
                return true;
            }
        }

        // if not, then it is not
        return false;
    }

    public static double getDistance(SearchProblem problem){
        return problem.d;
    }
}
