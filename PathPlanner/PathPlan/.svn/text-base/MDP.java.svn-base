package PathPlan;

import java.util.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * A 2-dimensional MDP.  Contains methods for managing per-state
 * costs, converting between (x,y) pairs and state indices, and
 * finding possible predecessors of a given state.
 */

public class MDP {

    /**
     * Size of the MDP.
     */
    public int width, height;
    int numStates;

    // Cost of action
    double stepCost;		// per step
    double [] costs;		// extra cost for each state

    // List of actions: for each action, the relative coordinates of
    // the states that the action passes through.  First index is
    // action number, second ranges from 0 through (num states passed
    // through)-1.  Note that we include start and end states; these
    // must be the first and last entries, respectively.
    int passthrudx[][] = null;
    int passthrudy[][] = null;

    // Precomputed occupancy times for different passthru states for
    // each action -- should be consistent with getLineCost below.
    double passthrutime[][] = null;


    //
    // Manage state costs and connectivity
    //


    /**
     * Get the cost of every state as an array.  Excludes the per-step
     * base cost.
     */
    public double[] getCosts() {
	double [] res = new double[costs.length];
	System.arraycopy(costs, 0, res, 0, costs.length);
	return res;
    }

    /**
     * Get the per-step base cost.
     */
    public double getStepCost() {
	return stepCost;
    }

    /**
     * Set the per-step base cost.
     */
    public void setStepCost(double c) {
	stepCost = c;
    }

    /**
     * Set the state costs for the MDP.
     * @param c The cost vector.  Should be numStates elements long.
     * @param baseCost Additional cost per step beyond what's in c.
     */
    public void setCosts(double [] c, double baseCost) {
	if (c.length != numStates)
	    throw new RuntimeException("cost vector should have " + 
				       numStates + " elements, has " + 
				       c.length);
	costs = c;
	stepCost = baseCost;
    }

    /**
     * Initialize to a constant cost per step.
     * @param c The cost for a 1-unit step.  (Some actions may move
     * more than 1 unit (e.g. diagonal moves have length sqrt(2)) and
     * so cost a multiple of this cost.)
     */
    public void setCosts(double c) {
	double [] cc = new double[numStates]; // java automatically zeros
	setCosts(cc, c);
    }

    /**
     * Add a constant to the costs at a list of states.
     * @param xs The x coordinates of the states to modify.
     * @param ys The y coordinates of the states to modify.
     * @param length Use this many elements of xs and ys.
     * @param c The cost increment.
     */
    public void addCost(int[] xs, int[] ys, int length, double c) {
	for (int i = 0; i < length; i++) {
	    costs[stateIndex(xs[i], ys[i])] += c;
	}
    }


    /**
     * Reset all edge costs to default.
     */
    public void clear() {
	setCosts(stepCost);
    }


    /**
     * Return a grayscale image of the state costs.
     * @param mincost Scale so that mincost is intensity 0.
     * @param maxcost Scale so that maxcost is intensity 1.
     */
    public Image getCostImage(double mincost, double maxcost) {
	double[] scaledcosts = new double[costs.length];
	for (int i = 0; i < costs.length; i++) {
	    double s = (costs[i] - mincost) / (maxcost - mincost);
	    if (s < 0) s = 0;
	    if (s > 1) s = 1;
	    scaledcosts[i] = 65535 * s;
	}
	BufferedImage theImage = new BufferedImage
	    (width, height, BufferedImage.TYPE_USHORT_GRAY);
	WritableRaster imdata = theImage.getRaster();
	imdata.setPixels(0, 0, width, height, scaledcosts);
	return theImage;
    }


    /**
     * Set to an 8-connected (3x3 actions) grid
     */
    public void set8connected() {
	int dx[][] = {{0, -1}, {0, -1}, {0, -1}, {0, 0}, 
		      {0, 0}, {0, 1}, {0, 1}, {0, 1}};
	int dy[][] = {{0, -1}, {0, 0}, {0, 1}, {0, -1},
		      {0, 1}, {0, -1}, {0, 0}, {0, 1}};
	double pt[][] = {{.707, .707}, {.5, .5}, {.707, .707}, {.5, .5},
			 {.5, .5}, {.707, .707}, {.5, .5}, {.707, .707}};
	passthrudx = dx;
	passthrudy = dy;
	passthrutime = pt;
    }


    /**
     * Set to a 16-connected (5x5 actions, removing duplicate
     * directions) grid
     */
    public void set16connected() {
	int dx[][] = {{0, -1, 0, -2},
		      {0, -1, 0, -2},
		      {0, -1, -1, -1},
		      {0, -1},
		      {0, -1},
		      {0, -1},
		      {0, -1, -1, -1},
		      {0, 0},
		      {0, 0},
		      {0, 1, 1, 1},
		      {0, 1},
		      {0, 1},
		      {0, 1},
		      {0, 1, 1, 1},
		      {0, 1, 0, 2},
		      {0, 1, 0, 2}};
	int dy[][] = {{0, 0, -1, -1},
		      {0, 0, 1, 1},
		      {0, -1, -1, -2},
		      {0, -1},
		      {0, 0},
		      {0, 1},
		      {0, 1, 1, 2},
		      {0, -1},
		      {0, 1},
		      {0, -1, -1, -2},
		      {0, -1},
		      {0, 0},
		      {0, 1},
		      {0, 1, 1, 2},
		      {0, 0, -1, -1},
		      {0, 0, 1, 1}};
	double pt[][] = {{0.5590, 0.5590, 0.5590, 0.5590},
			 {0.5590, 0.5590, 0.5590, 0.5590},
			 {0.5590, 0.5590, 0.5590, 0.5590},
			 {0.7071, 0.7071},
			 {0.5000, 0.5000},
			 {0.7071, 0.7071},
			 {0.5590, 0.5590, 0.5590, 0.5590},
			 {0.5000, 0.5000},
			 {0.5000, 0.5000},
			 {0.5590, 0.5590, 0.5590, 0.5590},
			 {0.7071, 0.7071},
			 {0.5000, 0.5000},
			 {0.7071, 0.7071},
			 {0.5590, 0.5590, 0.5590, 0.5590},
			 {0.5590, 0.5590, 0.5590, 0.5590},
			 {0.5590, 0.5590, 0.5590, 0.5590}};
	passthrudx = dx;
	passthrudy = dy;
	passthrutime = pt;
    }


    /**
     * Accumulate total costs over a line.  That is, if we move in a
     * straight line from startState to endState, accumulating costs
     * in proportion to the time we spend in each grid cell, what is
     * the total?  Uses the stored state costs for the MDP.
     * @param startState Starting position.
     * @param endState Ending position.
     */
    public double getLineCost(int startState, int endState) {
	return getLineCost(startState, endState, costs);
    }


    /**
     * Accumulate total costs over a line.  That is, if we move in a
     * straight line from startState to endState, accumulating cost in
     * proportion to the time we spend in each grid cell (at a rate
     * costs[i] for cell i), what is the total?
     * @param startState Starting position.
     * @param endState Ending position.
     * @param costs State costs.
     */
    public double getLineCost(int startState, int endState, double[] costs) {
	int sx = getX(startState);
	int sy = getY(startState);
	int ex = getX(endState);
	int ey = getY(endState);
	int deltax = ex - sx;
	int deltay = ey - sy;
	double cost;

	if (deltax == 0) {	// vertical line
	    if (sy > ey) {
		int swap = sy;
		sy = ey;
		ey = swap;
		swap = sx;
		sx = ex;
		ex = swap;
	    }
	    cost = .5 * costs[stateIndex(sx, sy)] +
		.5 * costs[stateIndex(ex, ey)];
	    for (int y = sy+1; y < ey; y++) {
		cost += costs[stateIndex(sx, y)];
	    }
	} else {		// finite slope
	    double slope = (double) deltay / (double) deltax;
	    if (slope > 1 || slope < -1) { // y changes faster
		if (sy > ey) {
		    int swap = sy;
		    sy = ey;
		    ey = swap;
		    swap = sx;
		    sx = ex;
		    ex = swap;
		}
		slope = 1. / slope;
		cost = .5 * costs[stateIndex(sx, sy)] +
		    .5 * costs[stateIndex(ex, ey)];
		double x = sx + slope;
		for (int y = sy+1; y < ey; y++) {
		    int ix = (int) Math.floor(x);
		    double fx = x - ix;
		    cost += (1 - fx) * costs[stateIndex(ix, y)];
		    if (fx > 1e-6)
			cost += fx * costs[stateIndex(ix+1, y)];
		    x += slope;
		}
	    } else {		// x changes faster
		if (sx > ex) {
		    int swap = sy;
		    sy = ey;
		    ey = swap;
		    swap = sx;
		    sx = ex;
		    ex = swap;
		}
		cost = .5 * costs[stateIndex(sx, sy)] +
		    .5 * costs[stateIndex(ex, ey)];
		double y = sy + slope;
		for (int x = sx+1; x < ex; x++) {
		    int iy = (int) Math.floor(y);
		    double fy = y - iy;
		    cost += (1 - fy) * costs[stateIndex(x, iy)];
		    if (fy > 1e-6)
			cost += fy * costs[stateIndex(x, iy+1)];
		    y += slope;
		}
	    }
	    cost *= Math.sqrt(1 + slope*slope);
	}
	cost += Math.sqrt(deltax * deltax + deltay * deltay) * stepCost;
	return cost;
    }


    //
    // Planning support
    //


    /**
     * Get the constant portion of the cost of moving in a straight
     * line between states.  (That is, use only the "additional cost
     * per step" term from {@link #setCosts}.)  This is faster to
     * compute than the full answer which {@link #getLineCost}
     * returns, and it is a lower bound under the assumption that all
     * costs are positive.
     * @param from Starting position.
     * @param to Ending position.
     */
    public double getDistance(int from, int to) {
	int fromx = getX(from);
	int fromy = getY(from);
	int tox = getX(to);
	int toy = getY(to);
	int distx = fromx - tox;
	int disty = fromy - toy;
	return stepCost * Math.sqrt(distx*distx + disty*disty);
    }


    /**
     * Get a bound on the number of predecessors of a state.
     */
    public int predBound() {
	return passthrudx.length;
    }

    /**
     * Get a bound on the number of successors of a state.
     */
    public int succBound() {
	return passthrudx.length;
    }

    /**
     * Get information about the possible successors of a state.
     * Store the info in a preallocated array and return the number of
     * states computed. 
     * @param state The state whose successors are required.
     * @param mask Consider only successors s such that mask[s] is false.
     * @param succs Store the successors in this array (which must be
     * preallocated large enough, see succBound).
     * @param scosts Store the cost of each successor in this array
     * (which must be preallocated large enough, see succBound).
     */
    final public int successors(int state, boolean[] mask, int[] succs,
				double[] scosts) {
	int numSuccs = 0;

	// get x and y coords of state
	int x = getX(state);
	int y = getY(state);

	// iterate through possible successors
	for (int i = 0; i < passthrudx.length; i++) {
	    int parx = x + passthrudx[i][passthrudx[i].length-1];
	    int pary = y + passthrudy[i][passthrudy[i].length-1];
	    if (parx >= 0 && parx < width &&
		pary >= 0 && pary < height) {
		int par = state + parx + width*pary;
		if (!mask[par]) {
		    double cost = 0;
		    for (int j = 0; j < passthrudx[i].length; j++) {
			int pt = state + passthrudx[i][j] +
			    width * passthrudy[i][j];
			cost += (stepCost + costs[pt]) * passthrutime[i][j];
		    }

		    // store the info
		    succs[numSuccs] = par;
		    scosts[numSuccs] = cost;
		    numSuccs++;
		}
	    }
	}
	return numSuccs;
    }


    /**
     * Get information about the possible predecessors of a state.
     * Store the info in some preallocated arrays and return the
     * number of predecessors computed.
     * @param state The state whose predecessors are required.
     * @param distState If nonnegative, compute a lower bound on the 
     *    distance from each predecessor to distState and store in dists.
     * @param mask Consider only predecessors p where mask[p]=true.
     * @param preds Store the predecessors in this array (which must be 
     *    preallocated large enough -- see predBound)
     * @param pcosts For each predecessor p, store the cost of reaching
     *    state from p.  (Must be preallocated also.)
     * @param dists If distances were requested, store them in here.  
     *    (Must be preallocated also.)
     */
    final public int predecessors
	(int state, int distState, boolean[] mask,
	 int[] preds, double[] pcosts, double[] dists) {
	int numPreds = 0;

	// get x and y coords of state and distState
	int x = getX(state);
	int y = getY(state);
	int curx = 0, cury = 0;
	if (distState >= 0) {
	    curx = getX(distState);
	    cury = getY(distState);
	}

	// iterate through possible predecessors
	for (int i = 0; i < passthrudx.length; i++) {
	    int parx = x - passthrudx[i][passthrudx[i].length-1];
	    int pary = y - passthrudy[i][passthrudy[i].length-1];
	    if (parx >= 0 && parx < width &&
		pary >= 0 && pary < height) {
		int par = stateIndex(parx, pary);
		if (mask[par]) {

		    // compute edge cost
		    double cost = 0;
		    for (int j = 0; j < passthrudx[i].length; j++) {
			int pt = state - passthrudx[i][j] -
			    width * passthrudy[i][j];
			cost += (stepCost + costs[pt]) * passthrutime[i][j];
		    }

		    // compute heuristic if desired
		    double dist = 0;
		    if (distState >= 0) {
			int distx = parx - curx;
			int disty = pary - cury;
			dist = Math.sqrt(distx*distx + disty*disty);
		    }

		    // store the info
		    preds[numPreds] = par;
		    pcosts[numPreds] = cost;
		    dists[numPreds] = stepCost * dist;
		    numPreds++;
		}
	    }
	}
	return numPreds;
    }


    /**
     * How many states do we have?
     */
    public int numStates() {
	return numStates;
    }

    /**
     * Translate x, y into state index.
     */
    public int stateIndex(int x, int y) {
	int state = x + width * y;
	if (state < 0 || state >= numStates)
	    throw new RuntimeException
		("Coordinate out of bounds: " + x + "," + y);
	return x + width * y;
    }

    /**
     * Get the x coordinate corresponding to a state index.
     */
    public int getX(int index) {
	return index % width;
    }

    /**
     * Get the y coordinate corresponding to a state index.
     */
    public int getY(int index) {
	return index / width;
    }

    /**
     * Constructor.
     */
    public MDP(int w, int h, double c) {
	width = w;
	height = h;
	numStates = w * h;
	setCosts(c);
	set8connected();
	set16connected();
    }
}







    // push the possible predecessors of a state
//     final public void pushPredecessors(int node, PriorityQueue q, boolean [] working,
// 				 double [] totalcosts, int [] nextState,
// 				 int startState) {
// 	int x = getX(node);
// 	int y = getY(node);
// 	int curx = 0, cury = 0;
// 	if (startState >= 0) {
// 	    curx = getX(startState);
// 	    cury = getY(startState);
// 	}
// 	for (int i = -1; i <= 1; i++) {
// 	    for (int j = -1; j <= 1; j++) {
// 		int parx = x + i;
// 		int pary = y + j;
// 		if (parx >= 0 && parx < width &&
// 		    pary >= 0 && pary < height) {
// 		    int par = stateIndex(parx, pary);
// 		    if (working[par]) {
// 			double cost = costs[node] + stepCost;
// 			if (i != 0 && j != 0) {
// 			    cost *= 1.4142136;
// 			}
// 			double val = totalcosts[node] + cost;
// 			double pri = val;

// 			// compute heuristic if desired
// 			if (startState >= 0) {
// 			    int distx = parx - curx;
// 			    int disty = pary - cury;
// 			    double dist = Math.sqrt(distx*distx + disty*disty);
// 			    pri += stepCost * dist;
// 			}

// 			if (q.raise(par, pri)) {
// 			    nextState[par] = node;
// 			    totalcosts[par] = val;
// 			}
// 		    }
// 		}
// 	    }
// 	}
//     }



//     // Add a constant to the costs in a circular region.
//     void addCostCircle(double x, double y, double r, double c) {

// 	// find a bounding box
// 	int xmin = (int) Math.floor(x - r);
// 	int xmax = (int) Math.ceil(x + r);
// 	int ymin = (int) Math.floor(y - r);
// 	int ymax = (int) Math.ceil(y + r);
// 	if (xmin < 0) xmin = 0;
// 	if (xmax >= width) xmax = width-1;
// 	if (ymin < 0) ymin = 0;
// 	if (ymax >= height) ymax = height-1;

// 	// iterate over bounding box and test circle inclusion
// 	for (int i = xmin; i <= xmax; i++) {
// 	    for (int j = ymin; j <= ymax; j++) {
// 		double dx = i - x;
// 		double dy = j - y;
// 		if (dx * dx + dy * dy <= r * r) {
// 		    costs[stateIndex(i,j)] += c;
// 		}
// 	    }
// 	}
//     }
