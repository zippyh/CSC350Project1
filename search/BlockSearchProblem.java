package search;

import java.util.ArrayList;

public class BlockSearchProblem extends Problem {
    public BlockSearchProblem(State startOrder) {
        super(startOrder);
    }

    public BlockSearchProblem(State startOrder, State goalOrder) {
        super(startOrder, goalOrder);
    }

    @Override
    public ArrayList<Tuple> successor(State state) {
        ArrayList<Tuple> successors = new ArrayList<>();
        char[] charState = state.toString().toCharArray();
        int blankIndex = 0;
        for (int i = 0; i < charState.length; i++) {
            if (charState[i] == '0') {
                blankIndex = i;
            }
        }
        // List of moves
        ArrayList<String> moves = new ArrayList<>();
        moves.add("UP");
        moves.add("LEFT");
        moves.add("RIGHT");
        moves.add("DOWN");
        // Makes a new state for every move
        for (String move : moves) {
            char[] newState = charState.clone();
            switch (move) {
                case "UP":
                    if (blankIndex >= 3) {
                        char temp = newState[blankIndex - 3];
                        newState[blankIndex - 3] = '0';
                        newState[blankIndex] = temp;
                        successors.add(new Tuple(new Action(move), new State(newState.toString())));
                    }
                    break;
                case "LEFT":
                    if (blankIndex % 3 != 0) {
                        char temp = newState[blankIndex - 1];
                        newState[blankIndex - 1] = '0';
                        newState[blankIndex] = temp;
                        successors.add(new Tuple(new Action(move), new State(newState.toString())));
                    }
                    break;
                case "RIGHT":
                    if (blankIndex % 3 != 2) {
                        char temp = newState[blankIndex + 1];
                        newState[blankIndex + 1] = '0';
                        newState[blankIndex] = temp;
                        successors.add(new Tuple(new Action(move), new State(newState.toString())));
                    }
                    break;
                case "DOWN":
                    if (blankIndex < 6) {
                        char temp = newState[blankIndex + 3];
                        newState[blankIndex + 3] = '0';
                        newState[blankIndex] = temp;
                        successors.add(new Tuple(new Action(move), new State(newState.toString())));
                    }
                    break;
            }
        }
        return successors;
    }

    @Override
    public double h(Node node) {
        String currentState = node.getState().toString();
        String goalState = this.goal.toString();
        int distance = 0;
        for (int i = 0; i < currentState.length(); i++) {
            char c = currentState.charAt(i);
            if (c != '0') {
                int goalIndex = goalState.indexOf(c);
                distance += Math.abs(i / 3 - goalIndex / 3) + Math.abs(i % 3 - goalIndex % 3);
            }
        }
        return distance;
    }

}
