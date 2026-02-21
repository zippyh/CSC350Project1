package search;

import java.io.FileNotFoundException;
import java.util.*;
import subway.*;

/**
 * This code is adapted from search.py in the AIMA Python implementation, which
 * is published with the license below:
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 aima-python contributors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * 
 **/

public class Search {
	/* DO NOT MODIFY THE HEADERS OF ANY OF THESE FUNCTIONS */
	// im probably gonna modify this ngl
	// Uninformed Search algorithms

	public static Node breadthFirstSearch(Problem problem) {
		Queue<Node> frontier = new LinkedList<>();
		HashSet<State> exploredSet = new HashSet<>();
		// determines where we start
		Node start = new Node(problem.getInitial());

		int nodesVisited = 0;

		// check to see if start node is goal node ("i want to get from Fenway to Fenway!")
		if (problem.goalTest(start.getState())) {
			return start;
		}

		// add first node to frontier
		frontier.add(start);

		// runs while the frontier is not empty
		while (!frontier.isEmpty()) {
			Node node = frontier.poll();
			nodesVisited++;
			exploredSet.add(node.getState());

			for (Node child : node.expand(problem)) {
				// checks to see if each node expanded from the child node is in the frontier or explored set

				boolean inFrontier = false;

				for (Node n : frontier) {
					if (n.getState().equals(child.getState())) {
						inFrontier = true;
						break;
					}
				}

				if (!exploredSet.contains(child.getState()) && !inFrontier) {
					// is this node the goal node?
					if (problem.goalTest(child.getState())) {
						// if so, print out path, cost, etc
						System.out.println("Final Path:");

						for (Node n : child.path()) {
							System.out.println(n.getState());
						}

						System.out.println("Total cost: " + child.getPathCost());
						System.out.println("Nodes visited: " + nodesVisited);

						return child;
					}
					// if not, add to frontier
					frontier.add(child);
				}
			}
		}
		System.out.println("Nodes visited: " + nodesVisited);
		return null;
	}

	public static Node depthFirstSearch(Problem problem) {
		Stack<Node> frontier = new Stack<>();
		frontier.push(new Node(problem.getInitial()));
		HashSet<State> explored = new HashSet<>();
		// Checks to see if the start state is the goal state
		if (problem.goalTest(frontier.peek().getState())) {
			return frontier.peek();
		}
		while (!frontier.isEmpty()) {
			Node current = frontier.pop();
			explored.add(current.getState());
			Set<State> frontierStates = new HashSet<>();
			for (Node node : frontier) {
				frontierStates.add(node.getState());
			}
			// Checks to see is the child node is in the frontier or explored set
			for (Node node : current.expand(problem)) {
				if (!explored.contains(node.getState()) && !frontierStates.contains(node.getState())) {
					// Checks to see if the child node is the goal state
					if (problem.goalTest(node.getState())) {
						System.out.println("Final Path:");
						for (Node n : node.path()) {
							System.out.println(n.getState());
						}
						System.out.println("Total cost: " + node.getPathCost());
						System.out.println("Nodes visited: " + explored.size());
						return node;
					}
					// If the child node is not the goal state it gets added to the frontier
					frontier.push(node);
				}
			}

		}
		//If the frontier is empty return null
		System.out.println("Nodes visited: " + explored.size());
		return null;
	}
	
	public static Node uniformCostSearch(Problem problem) {

    	// ucs uses priority queue
    	PriorityQueue<Node> frontier = new PriorityQueue<>(
        	Comparator.comparingDouble(Node::getPathCost)
    	);

    	HashSet<State> explored = new HashSet<>();

    	Node start = new Node(problem.getInitial());
    	int nodesVisited = 0;

    	frontier.add(start);

    	while (!frontier.isEmpty()) {

        	Node current = frontier.poll();
        	nodesVisited++;

        	// goal test immediately 
        	if (problem.goalTest(current.getState())) {

            	System.out.println("Final Path:");
            	for (Node n : current.path()) {
                	System.out.println(n.getState());
            	}

            	System.out.println("Total cost: " + current.getPathCost());
            	System.out.println("Nodes visited: " + nodesVisited);

            	return current;
        	}

        	explored.add(current.getState());

        	for (Node child : current.expand(problem)) {

            	State childState = child.getState();
            	double childCost = child.getPathCost();

            	// check if explored already
            	if (explored.contains(childState)) {
               		continue;
            	}

            	// check if node not in frontier
            	Node frontierNode = null;
            	for (Node n : frontier) {
                	if (n.getState().equals(childState)) {
                    	frontierNode = n;
                    	break;
                	}
            	}
				
				//check if not in frontier and replace if in frontier but higher cost
            	if (frontierNode == null) {
                	frontier.add(child);
            	} else if (childCost < frontierNode.getPathCost()) {
                	frontier.remove(frontierNode);
                	frontier.add(child);
            	}
        	}
    	}

    	System.out.println("Nodes visited: " + nodesVisited);
    	return null;
	}

	// Informed (Heuristic) Search

	public static Node aStarSearch(Problem problem) {
		// ucs uses priority queue
    	PriorityQueue<Node> frontier =new PriorityQueue<>(Comparator.comparingDouble(n -> n.getPathCost() + problem.h(n)));

    	HashSet<State> explored = new HashSet<>();

    	Node start = new Node(problem.getInitial());
    	int nodesVisited = 0;

    	frontier.add(start);

    	while (!frontier.isEmpty()) {

        	Node current = frontier.poll();
        	nodesVisited++;

        	// goal test immediately 
        	if (problem.goalTest(current.getState())) {

            	System.out.println("Final Path:");
            	for (Node n : current.path()) {
                	System.out.println(n.getState());
            	}

            	System.out.println("Total cost: " + current.getPathCost());
            	System.out.println("Nodes visited: " + nodesVisited);

            	return current;
        	}

        	explored.add(current.getState());

        	for (Node child : current.expand(problem)) {

            	State childState = child.getState();
            	double childCost = child.getPathCost();

            	// check if explored already
            	if (explored.contains(childState)) {
               		continue;
            	}

            	// check if node not in frontier
            	Node frontierNode = null;
            	for (Node n : frontier) {
                	if (n.getState().equals(childState)) {
                    	frontierNode = n;
                    	break;
                	}
            	}
				
				//check if not in frontier and replace if in frontier but higher cost
            	if (frontierNode == null) {
                	frontier.add(child);
            	} else if (childCost < frontierNode.getPathCost()) {
                	frontier.remove(frontierNode);
                	frontier.add(child);
            	}
        	}
    	}

    	System.out.println("Nodes visited: " + nodesVisited);
    	return null;
	}

	// Main
	public static void main(String[] args) throws FileNotFoundException {

    if (args.length < 4 || args.length > 5) {
        System.out.println("Usage: java search.Search <map> <algorithm> <start> <goal> OR java search.Search <map> <algorithm> <start> <goal> <distance>");
        return;
    }

    String mapName = args[0].toLowerCase();
    String algorithm = args[1].toLowerCase();
    String startName = args[2];
    String goalName = args[3];

	double distance;

	if(args.length == 5){
		distance = Double.parseDouble(args[4]);
	}else{
		distance = 0;
	}

    // Build the correct map
    SubwayMap map = null;

    if (mapName.equals("boston")) {
        map = SubwayMap.buildBostonMap();
    } 
    else if(mapName.equals("london")) {
        map = SubwayMap.buildLondonMap();
    }else{
		System.out.println("Unknown map: " + mapName);
        return;
	}

    // Create states
    State start = new State(startName);
    State goal = new State(goalName);

    Problem problem = new SearchProblem(start, goal, map, distance);

    Node result = null;

    // Choose algorithm
    switch (algorithm) {
        case "bfs":
            result = breadthFirstSearch(problem);
            break;

        case "dfs":
            result = depthFirstSearch(problem);
            break;

        case "ucs":
            result = uniformCostSearch(problem);
            break;

        case "astar":
            result = aStarSearch(problem);
            break;

        default:
            System.out.println("Unknown algorithm: " + algorithm);
            return;
    }

    if (result == null) {
        System.out.println("No solution found.");
    } else {
        System.out.println("Search complete.");
    }
}
}