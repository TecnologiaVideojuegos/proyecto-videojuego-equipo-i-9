package PathPlan;

import java.util.Date;

// To do:
// Separate out MDP functionality into an interface.
// What if action labels aren't the resulting next state?
// Where should prunePath() go?


/**
 * Class to plan paths in a deterministic MDP with positive costs
 * using Dijkstra's algorithm or A*.  Plans "backwards" (ie, processes
 * goals first and works towards start) so that we can handle multiple
 * (disjunctive) goals.
 */

public class PathPlanner {

    // The MDP we're planning for
    MDP world;

    // State of planner: current value function, mark for whether the
    // value of each node is accurate, current policy, start node
    // which we're using to sort priority queue.
    double [] totalcosts;
    boolean [] working;
    int [] policy;
    int currentStartState = -1;

    // Currently eligible states for expanding
    PriorityQueue q = new PriorityQueue(0);

    /**
     * Get ready to plan paths in a given MDP.  We will use the MDP
     * to decide how much storage to allocate, and later (in {@link
     * #plan}) to compute the allowable predecessors for a given
     * state.
     * @param m The MDP.
     */
    public void init(MDP m) {
	int numStates = m.numStates();
	world = m;
	if (totalcosts == null || totalcosts.length < numStates) {
	    totalcosts = new double[numStates];
	    working = new boolean[numStates];
	    policy = new int[numStates];
	    q = new PriorityQueue(numStates);
	}
	for (int i = 0; i < numStates; i++) {
	    totalcosts[i] = 0.0;
	    working[i] = true;
	}
	q.empty();
    }


    /**
     * Set the start state.  Set to -1 (the default) to use Dijkstra's
     * algorithm (and thereby plan for all possible start states at
     * once).  Setting to a nonnegative value selects A* planning
     * instead, which attempts to focus the search to expand fewer
     * nodes.
     * @param start The desired starting state.
     */
    public void setStart(int start) {
	if (start != currentStartState) {
	    reorderQueue(start);
	}
    }


    /**
     * Get the start state.
     */
    public int getStart() {
	return currentStartState;
    }


    /**
     * Add a goal state.  This function should be called after {@link
     * #init} and before {@link #plan}, and may be called as many
     * times as desired during this interval.
     * @param node The goal node to add.  The planning problem is over
     * once we've reached any one of the goal nodes.
     */
    public void addGoal(int node) {
	addGoal(node, 0);
    }


    /**
     * Add a goal state with a specified cost.  See {@link
     * #addGoal(int)} for restrictions.
     * @param node The goal node to add.
     * @param cost The cost of using this node to finish the planning
     * problem.
     */
    public void addGoal(int node, double cost) {
	double dist = 0;
	if (currentStartState != -1)
	    dist = world.getDistance(node, currentStartState);
	q.set(node, dist + cost);
	totalcosts[node] = cost;
	policy[node] = -1;
    }


    /**
     * Find lowest-cost paths.  This is the main computational
     * function in the class: it repeatedly selects a state to process
     * ("expand"), computes its cost-to-go and policy, and marks its
     * predecessors for expansion at some future iteration.  Planning
     * is over when we have expanded the current start state (or all
     * states, if the current start state is -1).
     * @param maxiters Expand no more than this many nodes.  Set to 0
     * to allow however many expansions it takes to finish planning.
     */
    public double plan(int maxiters) {
	int pb = 0;
	if (world != null) pb = world.predBound();
	int[] preds = new int[pb];
	double[] costs = new double[pb];
	double[] heurs = new double[pb];
	int numPreds;

	int iters = 0;
	while (q.getSize() > 0 && (maxiters <= 0 || iters < maxiters)) {
	    int node = q.pop();
	    iters++;
	    working[node] = false;
	    numPreds = world.predecessors(node, currentStartState, working,
					  preds, costs, heurs);
	    for (int i = 0; i < numPreds; i++) {
		int pred = preds[i];
		double val = totalcosts[node] + costs[i];
		double pri = val + heurs[i];

		if (q.raise(pred, pri)) {
		    policy[pred] = node;
		    totalcosts[pred] = val;
		}
	    }

	    if (node == currentStartState) break;
	}

	return q.getLastPriority();
    }


    /** Plan using however many iterations it takes; equivalent to
     * {@link #plan} with maxiter=0}. */
    public double plan() {
	return plan(0);
    }

    /** Check whether we've planned an action for a given state.
     * @param state Do we have an action for this state? */
    public boolean getActionOK(int state) {
	return (working != null && !working[state] && policy[state] != -1);
    }

    /** Check whether we're at a goal.
     * @param state Is this state a goal? */
    public boolean isGoal(int state) {
	return (working != null && !working[state] && policy[state] == -1);
    }

    /** Check whether we know the cost-to-go of a state.
     * @param state Do we know the cost for this state? */
    public boolean costOK(int state) {
	return (working != null && !working[state]);
    }

    /** Get the next action in our plan.  Actions are labeled
     * according to the state they end up in, so after a call to plan,
     * we can follow a path by repeated calls to this function
     * starting with the start state.
     * @param state Get the action for this state.
     */
    public int getAction(int state) {
	if (getActionOK(state))
	    return policy[state];
	else
	    throw new RuntimeException("no action known for state " + state);
    }

    /**
     * Get the path from a state to the nearest goal.  Like a bunch of
     * calls to getAction.
     * @param state The starting state.
     * @param path Preallocated array to hold path, or null.  Will be
     * reallocated if necessary to hold all returned states.
     * @return Returns path[], or the reallocated version of path[].
     * There will be a sentinel -1 after the end of the computed
     * states if (and only if) there's extra space left.
     */
    public int[] getPath(int state, int[] path) {
	if (!costOK(state))
	    throw new RuntimeException("no plan known for state " + state);
	if (path == null)
	    path = new int[100];
	int idx = 0;
	do {
	    if (idx >= path.length) {
		int[] newpath = new int[path.length * 2 + 10];
		System.arraycopy(path, 0, newpath, 0, path.length);
		path = newpath;
	    }
	    path[idx] = state;
	    state = policy[state];
	    idx++;
	} while (state != -1);
	if (idx < path.length)
	    path[idx] = -1;
	return path;
    }

    /**
     * Get the path from a state to the nearest goal.  Equivalent to
     * getPath(state, null).
     * @param state The starting state.
     */
    public int[] getPath(int state) {
	return getPath(state, null);
    }

    /**
     * Delete nodes from a path to try to make it shorter.  Works only
     * because the planner considers an 8-connected grid while
     * straight lines in arbitrary directions make sense.
     * @param path  The path to prune.
     */
    public int[] prunePath(int[] path) {
	int[] res = new int[path.length];
	int wayno = 0;
	for (int i = 0; i < path.length; i++) {
	    res[wayno++] = path[i];
	    if (path[i] == -1) break;
	    boolean done = false;
	    while (!done && wayno > 2) {
		done = true;
		double c12 = world.getLineCost(res[wayno-3], res[wayno-2]);
		double c23 = world.getLineCost(res[wayno-2], res[wayno-1]);
		double c13 = world.getLineCost(res[wayno-3], res[wayno-1]);
		if (c13 <= c12 + c23) {
		    done = false;
		    res[wayno-2] = res[wayno-1];
		    wayno--;
		}
	    }
	}
	if (wayno < path.length)
	    res[wayno] = -1;
	return res;
    }


    /** Get the planned cost of a state.
     * @param state How much does it cost to get to a goal from here?
     */
    public double costOf(int state) {
	if (costOK(state)) {
	    return totalcosts[state];
	} else {
	    throw new RuntimeException("no plan yet for state " + state);
	}
    }

    // Reorder the queue for planning with a different start state
    void reorderQueue(int startState) {
	PriorityQueue newq = new PriorityQueue(q.getMaxSize());

	for (PriorityQueue.Iterator i = q.getIterator(); i.hasNext(); ) {
	    int x = i.next();
	    double dist = world.getDistance(x, startState);
	    newq.set(x, totalcosts[x] + dist);
	}

	q = newq;
	currentStartState = startState;
    }


    // What are the active states in the queue?
    int[] activeStates() {
	return q.getIDs();
    }

    // return a copy of the total costs array
    public double [] getTotalCosts() {
	return (double []) totalcosts.clone();
    }

    // return a copy of the "plan valid" array
    public boolean [] getWorking() {
	return (boolean []) working.clone();
    }

    // return a copy of the policy array
    public int [] getPolicy() {
	return (int []) policy.clone();
    }
 

    //
    // Test main.
    //

    public static void main(String[] args) {
	PathPlanner pp = new PathPlanner();
	MDP world = new MDP(150, 150, 6000);
	pp.init(world);

	// add goals (these correspond to a circular region in state space)
	pp.addGoal(13135, 0.0);
	pp.addGoal(13285, 0.0);
	pp.addGoal(13435, 0.0);
	pp.addGoal(13585, 0.0);
	pp.addGoal(13735, 0.0);
	pp.addGoal(12986, 0.0);
	pp.addGoal(13136, 0.0);
	pp.addGoal(13286, 0.0);
	pp.addGoal(13436, 0.0);
	pp.addGoal(13586, 0.0);
	pp.addGoal(13736, 0.0);
	pp.addGoal(12987, 0.0);
	pp.addGoal(13137, 0.0);
	pp.addGoal(13287, 0.0);
	pp.addGoal(13437, 0.0);
	pp.addGoal(13587, 0.0);
	pp.addGoal(13737, 0.0);
	pp.addGoal(13887, 0.0);
	pp.addGoal(12988, 0.0);
	pp.addGoal(13138, 0.0);
	pp.addGoal(13288, 0.0);
	pp.addGoal(13438, 0.0);
	pp.addGoal(13588, 0.0);
	pp.addGoal(13738, 0.0);
	pp.addGoal(13888, 0.0);
	pp.addGoal(12989, 0.0);
	pp.addGoal(13139, 0.0);
	pp.addGoal(13289, 0.0);
	pp.addGoal(13439, 0.0);
	pp.addGoal(13589, 0.0);
	pp.addGoal(13739, 0.0);
	pp.addGoal(13889, 0.0);
	pp.addGoal(12990, 0.0);
	pp.addGoal(13140, 0.0);
	pp.addGoal(13290, 0.0);
	pp.addGoal(13440, 0.0);
	pp.addGoal(13590, 0.0);
	pp.addGoal(13740, 0.0);
	pp.addGoal(13890, 0.0);
	pp.addGoal(12991, 0.0);
	pp.addGoal(13141, 0.0);
	pp.addGoal(13291, 0.0);
	pp.addGoal(13441, 0.0);
	pp.addGoal(13591, 0.0);
	pp.addGoal(13741, 0.0);

	// plan a few paths, and print the working policy after each one.
	pp.setStart(0);
	pp.plan(0);
	printpolicy(pp.policy, pp.working, 150);

	pp.setStart(11971);
	pp.plan(0);
	printpolicy(pp.policy, pp.working, 150);

	pp.setStart(11949);
	pp.plan(0);
	printpolicy(pp.policy, pp.working, 150);

	pp.setStart(10921);
	pp.plan(0);
	printpolicy(pp.policy, pp.working, 150);

	pp.setStart(10910);
	pp.plan(0);
	printpolicy(pp.policy, pp.working, 150);

	pp.setStart(10888);
	pp.plan(0);
	printpolicy(pp.policy, pp.working, 150);

	pp.setStart(13471);
	pp.plan(0);
	printpolicy(pp.policy, pp.working, 150);

	pp.setStart(10899);
	pp.plan(0);
	printpolicy(pp.policy, pp.working, 150);
    }


    // debugging output: print the current policy assuming a 2d world
    // with a given line length
    static void printpolicy(int[] policy, boolean[] working, int wrap) {
	for (int i = 0; i < policy.length; i++) {
	    if (i > 0 && i % wrap == 0)
		System.out.println('|');
	    if (working[i])
		System.out.print(" ");
	    else if (policy[i] == -1)
		System.out.print("*");
	    else {
		int diff = policy[i] - i;
		char c = '@';
		if (diff == -1) {
		    c = '<';
		} else if (diff == 1) {
		    c = '>';
		} else if (diff == -wrap) {
		    c = '^';
		} else if (diff == wrap) {
		    c = 'v';
		} else if (diff == -1-wrap) {
		    c = '`';
		} else if (diff == -wrap+1) {
		    c = '\'';
		} else if (diff == wrap-1) {
		    c = ',';
		} else if (diff == wrap+1) {
		    c = '.';
		}
		System.out.print(c);
	    }
	}
	System.out.println();
    }
}
