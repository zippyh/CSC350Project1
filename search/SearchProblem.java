package search;

import java.util.ArrayList;

public class SearchProblem extends Problem {

    private int d; // distance

    public SearchProblem(State startStation) {
        super(startStation);
    }

    public SearchProblem(State startStation, State destination, int d) {
        super(startStation, destination);
        this.d = d;
    }

    @Override
    public ArrayList<Tuple> successor(State state) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'successor'");
    }
    
}
