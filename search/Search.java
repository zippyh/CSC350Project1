package search;

import java.util.*;

import java.io.FileNotFoundException;

import subway.*;

/**
This code is adapted from search.py in the AIMA Python implementation, which is published with the license below:

	The MIT License (MIT)

	Copyright (c) 2016 aima-python contributors

	Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


**/

public class Search{
	/* DO NOT MODIFY THE HEADERS OF ANY OF THESE FUNCTIONS */
	//im probably gonna modify this ngl
	// Uninformed Search algorithms
	
	public static Node breadthFirstSearch(Problem problem){
		Queue<Node> frontier = new LinkedList<>();
		HashSet<State> exploredSet = new HashSet<>();
		// determines where we start
		Node start = new Node(problem.getInitial());

		int nodesVisited = 0;


		// check to see if start node is goal node ("i want to get from Fenway to Fenway!")
		if(problem.goalTest(start.getState())){
			return start;
		}

		// add first node to frontier
		frontier.add(start);

		// runs while the frontier is not empty
		while(!frontier.isEmpty()){
			Node node = frontier.poll();
			nodesVisited++;
			exploredSet.add(node.getState());

			for(Node child : node.expand(problem)){
				// checks to see if each node expanded from the child node is in the frontier or explored set

				boolean inFrontier = false;

    			for (Node n : frontier) {
        			if (n.getState().equals(child.getState())) {
            			inFrontier = true;
            			break;
        			}
    			}
				
				if(!exploredSet.contains(child.getState()) && !inFrontier){
					// is this node the goal node?
					if(problem.goalTest(child.getState())){
						// if so, print out path, cost, etc
    					System.out.println("Final Path:");

						for(Node n : child.path()){
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
	
	public static Node depthFirstSearch(Problem problem){
		Stack<Node> frontier = new Stack<>();
		frontier.push(new Node(problem.getInitial(), null, null, 0));
		Set<State> explored = new HashSet<>();
		while(!frontier.isEmpty()){
		Node current = frontier.pop();
		if (problem.goalTest(current.getState())) {
			return current;
		}
		explored.add(current.getState());
		for (Tuple tuple : problem.successor(current.getState())) {
			if(!explored.contains(tuple.getState()) && !frontier.contains(tuple)) {
				frontier.push(new Node(tuple.getState(), current, tuple.getAction(), current.getPathCost() + 1));
			}
		}
	}
		return null;
	}
	
	public static Node uniformCostSearch(Problem problem){
		//YOUR CODE HERE
		return null;
	}

	// Informed (Heuristic) Search
	
	public static Node aStarSearch(Problem problem){
		//YOUR CODE HERE
		return null;
	}
	
	// Main
	public static void main(String[] args) throws FileNotFoundException{
		//Replace this code with code that runs the program specified by
		//the command arguments
		
		SubwayMap hahaYeah = SubwayMap.buildBostonMap();

		State start = new State("Assembly");
		State goal = new State("Fenway");


		SearchProblem what = new SearchProblem(start, goal, hahaYeah);

		System.out.println(breadthFirstSearch(what));
		System.out.println("ran bfs");

		//System.out.println(args[0]);
		System.out.println("Usage: java Search <inputFile>");
		//Problem p = new SearchProblem(new State(args[2]), new State(args[3]), args[0]);
		//depthFirstSearch(p);
	}
}