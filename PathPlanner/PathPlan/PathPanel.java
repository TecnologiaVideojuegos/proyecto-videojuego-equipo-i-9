package PathPlan;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.image.*;

/**
 * A simple user interface for a path planner.
 */

public class PathPanel extends JPanel {

    // We will use this MDP, planner, and initial state to find paths
    // around obstacles.
    PathPlanner planner;
    MDP world;
    Point pos;

    // Coordinate transform between screen and planner.
    Discretizer disc;

    // Decoration
    Image background = null;

    // The parameters of the path planning problem.
    // Goals and obstacles are Lists of Points.
    List goals = new ArrayList();
    List expensivegoals = new ArrayList();
    List obstacles = new ArrayList();

    // Size of obstacles.
    final double obstacleRadius = 5;

    // plan info returned from planner
    double planLength = -1;
    double planCost = -1;

    // How many steps of planning to run at a time?  0 ==> all of them.
    int planStepping = 0;

    // Use the planner to find a path
    public void replan() {
	System.err.print("replanning... ");
	Date before = new Date();
	planner.init(world);
	for (ListIterator i = goals.listIterator(); i.hasNext(); ) {
	    Point ob = (Point) i.next();
	    int state = world.stateIndex(ob.x, ob.y);
	    planner.addGoal(state);
	}
	for (ListIterator i = expensivegoals.listIterator(); i.hasNext(); ) {
	    Point ob = (Point) i.next();
	    int state = world.stateIndex(ob.x, ob.y);
	    planner.addGoal(state, 100);
	}
	planner.plan(planStepping);
	// planner.plan(planStepping, -1);
	Date after = new Date();
	System.err.print((after.getTime() - before.getTime()) + " ms ");
	System.err.print("done\n");
    }

    // Keep planning if we haven't finished.
    public void planmore() {
	System.err.print("planning more... ");
	Date before = new Date();
	planner.plan(planStepping);
	// planner.plan(planStepping, -1);
	Date after = new Date();
	System.err.print((after.getTime() - before.getTime()) + " ms ");
	System.err.print("done\n");
    }

    // Try to step forward
    public void stepForward() {
	disc.setContinuousRange(0, 0, getWidth(), getHeight());
	Discretizer.Rectangle r = 
	    disc.Rectangle(pos.x-2, pos.y-2, pos.x+2, pos.y+2);
	int nextstate = planner.getAction(world.stateIndex(pos.x, pos.y));
	pos.x = world.getX(nextstate);
	pos.y = world.getY(nextstate);
	repaint(r.ixmin, r.iymin, r.ixmax - r.ixmin, r.iymax - r.iymin);
    }

    // Draw ourself
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

	disc.setContinuousRange(0, 0, getWidth(), getHeight());

	// Background image
	if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), 
			Color.black, null);
	}

	// Draw obstacles
	for (Iterator i = obstacles.iterator(); i.hasNext(); ) {
	    Point ob = (Point) i.next();
	    Discretizer.Rectangle r = 
		disc.Rectangle((int)(ob.x-obstacleRadius), 
			       (int)(ob.y-obstacleRadius),
			       (int)(ob.x+obstacleRadius),
			       (int)(ob.y+obstacleRadius));
	    g.setColor(new Color(1.0F, 0.0F, 0.0F, 0.5F));
	    g.fillOval((int)r.dxmin, (int)r.dymin,
		       (int)r.dxmax-(int)r.dxmin, (int)r.dymax-(int)r.dymin);
	    g.setColor(Color.black);
	    g.drawOval((int)r.dxmin, (int)r.dymin,
		       (int)r.dxmax-(int)r.dxmin, (int)r.dymax-(int)r.dymin);
	}

	// Draw goals
	for (Iterator i = goals.iterator(); i.hasNext(); ) {
	    Point ob = (Point) i.next();
	    Discretizer.Rectangle r = 
		disc.Rectangle((int)(ob.x),
			       (int)(ob.y),
			       (int)(ob.x),
			       (int)(ob.y));
	    g.setColor(new Color(0.0F, 1.0F, 0.0F, 0.5F));
	    g.fillOval((int)r.dxmin-2, (int)r.dymin-2, 
		       (int)(r.dxmax-r.dxmin+4), (int)(r.dymax-r.dymin+4));
	    g.setColor(Color.black);
	    g.drawOval((int)r.dxmin-2, (int)r.dymin-2, 
		       (int)(r.dxmax-r.dxmin+4), (int)(r.dymax-r.dymin+4));
	}
	for (Iterator i = expensivegoals.iterator(); i.hasNext(); ) {
	    Point ob = (Point) i.next();
	    Discretizer.Rectangle r = 
		disc.Rectangle((int)(ob.x),
			       (int)(ob.y),
			       (int)(ob.x),
			       (int)(ob.y));
	    g.setColor(new Color(1.0F, 0.0F, 1.0F, 0.5F));
	    g.fillOval((int)r.dxmin-2, (int)r.dymin-2, 
		       (int)(r.dxmax-r.dxmin+4), (int)(r.dymax-r.dymin+4));
	    g.setColor(Color.black);
	    g.drawOval((int)r.dxmin-2, (int)r.dymin-2, 
		       (int)(r.dxmax-r.dxmin+4), (int)(r.dymax-r.dymin+4));
	}

	// Draw robot
	Discretizer.Rectangle r = 
	    disc.Rectangle(pos.x-1, pos.y-1, pos.x+1, pos.y+1);
	g.setColor(new Color(0.0F, 0.0F, 1.0F, 0.8F));
	g.fillOval((int)r.dxmin, (int)r.dymin, (int)(r.dxmax-r.dxmin),
		   (int)(r.dymax-r.dymin));
	g.setColor(Color.black);
	g.drawOval((int)r.dxmin, (int)r.dymin, (int)(r.dxmax-r.dxmin),
		   (int)(r.dymax-r.dymin));

	// Draw nodes on priority queue
	if (planStepping > 0) {
	    int[] states = planner.activeStates();
	    for (int i = 0; i < states.length; i++) {
		int state = states[i];
		Discretizer.Point p = 
		    disc.Point(world.getX(state), world.getY(state));
		g.setColor(Color.black);
		g.fillRect((int)p.dx, (int)p.dy, 2, 2);
	    }
	}

	// Draw path
	{
	    Discretizer.Point p = disc.Point(pos.x, pos.y);
	    int state = world.stateIndex(p.ix, p.iy);
	    if (planner.costOK(state)) {
		planCost = planner.costOf(state);
	    }
	    if (planner.getActionOK(state)) {
		int path[] = planner.getPath(state);
		planLength = 0;
		g.setColor(Color.black);
		for (int i = 0; (i < path.length) && (path[i]>=0); i++) {
		    Discretizer.Point np = 
			disc.Point(world.getX(path[i]), world.getY(path[i]));
		    g.drawLine((int)p.dx, (int)p.dy, (int)np.dx, (int)np.dy);
		    planLength += Math.sqrt((p.ix - np.ix) * (p.ix - np.ix) +
					    (p.iy - np.iy) * (p.iy - np.iy));
		    p = np;
		}
	    }
	}
// 	System.out.println();
// 	System.out.println("Plan cost " + planCost + " = " + planLength +
// 			   " * " + (world.stepCost-1) + " + " + 
// 			   (planCost - planLength * (world.stepCost-1))
// 			   + "\n");
    }

    private class mouser extends MouseAdapter {
        public void mousePressed(MouseEvent e) {

	    disc.setContinuousRange(0, 0, getWidth(), getHeight());
	    Discretizer.Point m =
		disc.Point((double) e.getX(), (double) e.getY());

	    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
		Point p = new Point();
		p.x = m.ix;
		p.y = m.iy;
		if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0)
		    expensivegoals.add(p);
		else
		    goals.add(p);
		replan();
	    } else if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
		Point p = new Point();
		p.x = m.ix;
		p.y = m.iy;
		obstacles.add(p);
		int[][] states = new int[2][];
		Discretizer.Rectangle rect = 
		    disc.Rectangle((int)(m.ix-obstacleRadius),
				   (int)(m.iy-obstacleRadius),
				   (int)Math.ceil(m.ix+obstacleRadius),
				   (int)Math.ceil(m.iy+obstacleRadius));
		int size = disc.discretizeOval(rect, states);
		world.addCost(states[0], states[1], size, 50);
		replan();
	    } else if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
		pos.x = m.ix;
		pos.y = m.iy;
		planner.setStart(world.stateIndex(pos.x, pos.y));
 		if (!planner.costOK(world.stateIndex(pos.x, pos.y)))
		    planmore();
	    }
            repaint();
        }
        public void mouseReleased(MouseEvent e) {
	    disc.setContinuousRange(0, 0, getWidth(), getHeight());
	    Discretizer.Point m =
		disc.Point((double) e.getX(), (double) e.getY());

	    if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
//  		if (!planner.getActionOK(world.stateIndex(pos.x, pos.y)))
// 		    planmore();
	    }
        }
    }

    public PathPanel(int xbins, int ybins) {
        super();

	// Initialize the MDP and start state
	world = new MDP(xbins, ybins, 1);
	pos = new Point(xbins/2, ybins/2);

	// get a planner
	planner = new PathPlanner();
	planner.setStart(world.stateIndex(pos.x, pos.y));
	// planner = new RandPathPlanner();

	// get a coordinate transform
	disc = new Discretizer();
	disc.setDiscreteRange(xbins, ybins);
	disc.setContinuousRange(0, 0, getWidth(), getHeight());

	// Connect mouse events
        this.addMouseListener(new mouser());

	// Set up key actions
	AbstractAction step = new AbstractAction("Step forward") {
		public void actionPerformed(ActionEvent e) {
		    stepForward();
		}
	    };
	AbstractAction quit = new AbstractAction("Quit") {
		public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		}
	    };
	AbstractAction clear = new AbstractAction("Clear") {
		public void actionPerformed(ActionEvent e) {
		    world.clear();
		    repaint();
		}
	    };
	AbstractAction planstep = new AbstractAction("Step the planner") {
		public void actionPerformed(ActionEvent e) {
		    planmore();
		    repaint();
		}
	    };
	AbstractAction showplan = new 
	    AbstractAction("Toggle showing the planning process") {
		public void actionPerformed(ActionEvent e) {
		    if (planStepping == 0) {
			planStepping = 50;
		    } else {
			planStepping = 0;
		    }
		    if (planStepping > 0) {
			System.out.print("Showing planning process\n");
			replan();
		    } else {
			System.out.print("Hiding planning process\n");
			if (!planner.getActionOK
			    (world.stateIndex(pos.x, pos.y)))
			    planmore();
		    }
		    repaint();
		}
	    };
	AbstractAction morecost = new AbstractAction("Raise per-step cost") {
		public void actionPerformed(ActionEvent e) {
		    world.stepCost *= 2;
		    System.out.print("Step cost is " + world.stepCost + "\n");
		    replan();
		    repaint();
		}
	    };
	AbstractAction lesscost = new AbstractAction("Reduce per-step cost") {
		public void actionPerformed(ActionEvent e) {
		    world.stepCost /= 2;
		    System.out.print("Step cost is " + world.stepCost + "\n");
		    replan();
		    repaint();
		}
	    };


	// attach key actions to keys
	this.getInputMap(WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke(' '), "step");
	this.getActionMap().put("step", step);
	this.getInputMap(WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke('q'), "quit");
	this.getActionMap().put("quit", quit);
	this.getInputMap(WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke("DELETE"), "clear");
	this.getActionMap().put("clear", clear);
	this.getInputMap(WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke("ENTER"), "planstep");
	this.getActionMap().put("planstep", planstep);
	this.getInputMap(WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke('s'), "showplan");
	this.getActionMap().put("showplan", showplan);
	this.getInputMap(WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke('+'), "morecost");
	this.getInputMap(WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke('='), "morecost");
	this.getActionMap().put("morecost", morecost);
	this.getInputMap(WHEN_IN_FOCUSED_WINDOW).
	    put(KeyStroke.getKeyStroke('-'), "lesscost");
	this.getActionMap().put("lesscost", lesscost);
    }

    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Path planner");
        PathPanel content;
	double w, h;

	// process args
	if (args.length == 0) {
	    content = new PathPanel(150, 150);
	    w = 150;
	    h = 150;
	} else if (args.length == 1) {
	    MapFile map = new MapFile(args[0]);
	    content = new PathPanel(map.width, map.height);
	    content.background = map.mapImage;
	    float[][] costmap = map.convertImage(1);
	    double[] costs = map.stack(costmap, 200);
	    content.world.setCosts(costs, 1);
	    w = map.width;
	    h = map.height;
	} else {
	    throw new RuntimeException("too many args (at most 1 image)");
	}

        // determine a reasonable size while maintaining aspect ratio
        double scalex = 1000 / w;
        double scaley = 700 / h;
        double scale = scalex;
        if (scaley < scalex) scale = scaley;
        frame.setSize((int) (scale * w), (int) (scale * h) + 25);

	// make visible
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setContentPane(content);
        frame.setVisible(true);
    }
}
