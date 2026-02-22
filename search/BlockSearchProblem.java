/*
* BlockSearchProblem.java
* Description: Handles the 8 block/tile puzzle search problems. 
*/

package search;

import java.util.ArrayList;

public class BlockSearchProblem extends Problem {
    public BlockSearchProblem(State startOrder, State goalOrder) {
        super(startOrder, goalOrder);
    }

    @Override
    public ArrayList<Tuple> successor(State state) {
        ArrayList<Tuple> successors = new ArrayList<>();
        // use getName to get the raw string (i.e. 142305678)
        String currentString = state.getName(); 
        char[] charState = currentString.toCharArray();
        
        // find the empty space (the 0) in the raw string representation
        int blankIndex = -1;
        for (int i = 0; i < charState.length; i++) {
            if (charState[i] == '0') {
                blankIndex = i;
                break;
            }
        }
        
        // define moves for the blank space
        String[] moves = {"UP", "LEFT", "RIGHT", "DOWN"};
        for (String move : moves) {
            int targetIndex = -1;
            switch (move) {
                case "UP":
                    // cannot move up if the blank is in the top row
                    if (blankIndex >= 3) targetIndex = blankIndex - 3;
                    break;
                case "LEFT":
                    // cannot move left if the blank is in the left column
                    if (blankIndex % 3 != 0) targetIndex = blankIndex - 1;
                    break;
                case "RIGHT":
                    // cannot move right if the blank is in the right column
                    if (blankIndex % 3 != 2) targetIndex = blankIndex + 1;
                    break;
                case "DOWN":
                    // cannot move down if the blank is in the bottom row
                    if (blankIndex < 6) targetIndex = blankIndex + 3;
                    break;
            }

            if (targetIndex != -1) {
                // Swap the blank space and the given tile
                char[] nextChars = currentString.toCharArray();
                nextChars[blankIndex] = nextChars[targetIndex];
                nextChars[targetIndex] = '0';
                
                // Create a new tuple from the modified string 
                successors.add(new Tuple(new Action(move), new State(new String(nextChars))));
            }
        }
        return successors;
    }

    @Override
    public double h(Node node) {
        String currentState = node.getState().getName(); // Current tile arrangement
        String goalState = this.goal.getName(); // Target tile arrangement
        int distance = 0;

        for (int i = 0; i < currentState.length(); i++) {
            char tile = currentState.charAt(i);
            // Manhattan distance must ignore the blank to remain admissible
            if (tile != '0') {
                int currentPos = i;
                int goalPos = goalState.indexOf(tile);

                // Convert 1D array index into (x,y) coords for 3x3 grif
                int currentX = currentPos % 3;
                int currentY = currentPos / 3;
                int goalX = goalPos % 3;
                int goalY = goalPos / 3;

                // Manhattan distance calculation
                distance += Math.abs(currentX - goalX) + Math.abs(currentY - goalY);
            }
        }
        return distance;
    }
}