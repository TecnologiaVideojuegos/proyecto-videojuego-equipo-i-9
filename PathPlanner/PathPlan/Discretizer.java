package PathPlan;


/**
 * Handles 2D coordinate transforms and discretization.
 */

public class Discretizer {

    int width, height;
    double xmin, xmax, ymin, ymax;

    /**
     * A point which has corresponding coordinates in the discrete and
     * continuous worlds.
     */
    public class Point {

	/**
	 * Discrete coordinates.
	 */
	public int ix, iy;

	/**
	 * Continuous coordinates.
	 */
	public double dx, dy;

	/**
	 * Set the discrete coordinates.
	 */
	public void setDisc(int x, int y) {
	    if (x < 0 || x >= width || y < 0 || y >= height)
		throw new RuntimeException("Point out of bounds");
	    ix = x; 
	    iy = y;
	    dx = getContX(x);
	    dy = getContY(y);
	}

	/**
	 * Set the continuous coordinates.
	 */
	public void setCont(double x, double y) {
	    if (x < xmin || x > xmax || y < ymin || y > ymax)
		throw new RuntimeException("Point out of bounds");
	    dx = x;
	    dy = y;
	    ix = getDiscX(x);
	    iy = getDiscY(y);
	}

	/**
	 * Make a point from its discrete coordinates.
	 */
	public Point(int x, int y) {
	    setDisc(x, y);
	}

	/**
	 * Make a point from its continuous coordinates.
	 */
	public Point(double x, double y) {
	    setCont(x, y);
	}
    }


    /**
     * Make a point from its discrete coordinates.
     */
    public Point Point(int x, int y) {
	return new Point(x, y);
    }


    /**
     * Make a point from its continuous coordinates.
     */
    public Point Point(double x, double y) {
	return new Point(x, y);
    }


    /**
     * A rectangle which has corresponding coordinates in the discrete
     * and continuous worlds.
     */
    public class Rectangle {

	/**
	 * Integer coordinates.
	 */
	public int ixmin, ixmax, iymin, iymax;

	/**
	 * Continuous coordinates.
	 */
	public double dxmin, dxmax, dymin, dymax;

	/**
	 * Check to make sure coordinates are in correct order
	 */
	public void checkOrder() {
	    if (ixmin > ixmax) {
		int swap = ixmin;
		ixmin = ixmax;
		ixmax = swap;
	    }
	    if (iymin > iymax) {
		int swap = iymin;
		iymin = iymax;
		iymax = swap;
	    }
	    if (dxmin > dxmax) {
		double swap = dxmin;
		dxmin = dxmax;
		dxmax = swap;
	    }
	    if (dymin > dymax) {
		double swap = dymin;
		dymin = dymax;
		dymax = swap;
	    }
	}

	/**
	 * Printable representation
	 */
	public String toString() {
	    return "<Rect [" + ixmin + " " + ixmax + "]x[" +
		iymin + " " + iymax + "] or [" + dxmin + " " +
		dxmax + "]x[" + dymin + " " + dymax + "]>";
	}

	/**
	 * Make a Rectangle from its discrete coordinates.
	 */
	public Rectangle(int xmin, int ymin, int xmax, int ymax) {
	    ixmin = xmin;
	    ixmax = xmax;
	    iymin = ymin;
	    iymax = ymax;
	    dxmin = getContX(xmin);
	    dxmax = getContX(xmax);
	    dymin = getContY(ymin);
	    dymax = getContY(ymax);
	    checkOrder();
	}

	/**
	 * Make a Rectangle from its continuous coordinates.
	 */
	public Rectangle(double xmin, double ymin, double xmax, double ymax) {
	    dxmin = xmin;
	    dxmax = xmax;
	    dymin = ymin;
	    dymax = ymax;
	    ixmin = getDiscX(xmin);
	    ixmax = getDiscX(xmax);
	    iymin = getDiscY(ymin);
	    iymax = getDiscY(ymax);
	    checkOrder();
	}
    }


    
    /**
     * Make a Rectangle from its discrete coordinates.
     */
    public Rectangle Rectangle(int xmin, int ymin, int xmax, int ymax) {
	return new Rectangle(xmin, ymin, xmax, ymax);
    }


    /**
     * Make a Rectangle from its continuous coordinates.
     */
    public Rectangle Rectangle
	(double xmin, double ymin, double xmax, double ymax) {
	return new Rectangle(xmin, ymin, xmax, ymax);
    }


    /**
     * Find all of the discrete coordinates within an axis-oriented
     * elliptical region.
     * @param rect  The bounding rectangle of the ellipse.
     * @param result An int[2][].  The x coordinates will be returned
     * in result[0] while the y coordinates will be returned in
     * result[1].  If result[0] and result[1] are not big enough to
     * hold all of the coordinates, they will be reallocated.
     * @return The number of points computed.  Elements of result[][]
     * past this number of entries are invalid.
     */
    public int discretizeOval(Rectangle rect, int[][] result) {

	// find a bounding box
	int xmin = rect.ixmin-1;
	int xmax = rect.ixmax-1;
	int ymin = rect.iymin+1;
	int ymax = rect.iymax+1;
	if (xmin < 0) xmin = 0;
	if (xmax >= width) xmax = width-1;
	if (ymin < 0) ymin = 0;
	if (ymax >= height) ymax = height-1;

	// check that we have positive size
	if ((xmax <= xmin) || (ymax <= ymin))
	    return 0;

	// find center and radii in discrete coordinates without rounding
	double ixmin = getDiscXFraction(rect.dxmin);
	double iymin = getDiscYFraction(rect.dymin);
	double ixmax = getDiscXFraction(rect.dxmax);
	double iymax = getDiscYFraction(rect.dymax);
	double x = (ixmax + ixmin) / 2;
	double y = (iymax + iymin) / 2;
	double rx = (ixmax - ixmin) / 2;
	double ry = (iymax - iymin) / 2;

	// check for enough space
	int size = (xmax-xmin+1)*(ymax-ymin+1);
	if (result[0] == null || result[0].length < size)
	    result[0] = new int[size];
	if (result[1] == null || result[1].length < size)
	    result[1] = new int[size];

	// iterate over bounding box and test inclusion
	int index = 0;
	for (int i = xmin; i <= xmax; i++) {
	    for (int j = ymin; j <= ymax; j++) {
		double dx = (i + .5 - x) / rx;
		double dy = (j + .5 - y) / ry;
		if (dx * dx + dy * dy <= 1) {
		    result[0][index] = i;
		    result[1][index] = j;
		    index++;
		}
	    }
	}

	return index;
    }


    /**
     * Set the range of the discrete coordinates
     */
    public void setDiscreteRange(int w, int h) {
	width = w;
	height = h;
    }


    /**
     * Set the range of the continuous coordinates
     */
    public void setContinuousRange(double xmin, double ymin, 
				   double xmax, double ymax) {
	this.xmin = xmin;
	this.xmax = xmax;
	this.ymin = ymin;
	this.ymax = ymax;
    }


    /**
     * Translate discrete to continuous X.
     */
    final public double getContX(int x) {
	return xmin + (xmax-xmin) * (x+.5) / width;
    }


    /**
     * Translate discrete to continuous Y.
     */
    final public double getContY(int y) {
	return ymin + (ymax-ymin) * (y+.5) / height;
    }


    /**
     * Translate continuous to discrete X.
     */
    final public int getDiscX(double x) {
	int ix = (int) Math.floor(getDiscXFraction(x));
	if (ix == width) ix = width-1; // allow passing in xmax
	return ix;
    }


    /**
     * Translate continuous to discrete X without rounding (useful
     * only as an intermediate result).
     */
    final public double getDiscXFraction(double x) {
	return (x - xmin) * width / (xmax - xmin);
    }


    /**
     * Translate continuous to discrete Y.
     */
    final public int getDiscY(double y) {
	int iy = (int) Math.floor(getDiscYFraction(y));
	if (iy == height) iy = height-1; // allow passing in ymax
	return iy;
    }


    /**
     * Translate continuous to discrete Y without rounding (useful
     * only as an intermediate result).
     */
    final public double getDiscYFraction(double y) {
	return (y - ymin) * height / (ymax - ymin);
    }


    /**
     * Get discrete x range.
     */
    final public int getWidth() {
	return width;
    }

    /**
     * Get discrete y range.
     */
    final public int getHeight() {
	return height;
    }

    /**
     * Get continuous lowest x.
     */
    final public double getXmin() {
	return xmin;
    }

    /**
     * Get continuous largest x.
     */
    final public double getXmax() {
	return xmax;
    }

    /**
     * Get continuous smallest y.
     */
    final public double getYmin() {
	return ymin;
    }

    /**
     * Get continuous largest y.
     */
    final public double getYmax() {
	return ymax;
    }
}



//     /**
//      * Find all of the discrete coordinates within an axis-oriented
//      * elliptical region.
//      * @param x X coordinate of center of ellipse.
//      * @param y Y coordinate of center of ellipse.
//      * @param rx X radius of ellipse.
//      * @param ry Y radius of ellipse.
//      * @param result An int[2][].  The x coordinates will be returned
//      * in result[0] while the y coordinates will be returned in
//      * result[1].  If result[0] and result[1] are not big enough to
//      * hold all of the coordinates, they will be reallocated.
//      * @return The number of points computed.  Elements of result[][]
//      * past this number of entries are invalid.
//      */
//     public int discretizeOval(double x, double y, double rx, double ry, 
// 			      int[][] result) {

// 	// find a bounding box
// 	int xmin = (int) Math.floor(x - rx);
// 	int xmax = (int) Math.ceil(x + rx);
// 	int ymin = (int) Math.floor(y - ry);
// 	int ymax = (int) Math.ceil(y + ry);
// 	if (xmin < 0) xmin = 0;
// 	if (xmax >= width) xmax = width-1;
// 	if (ymin < 0) ymin = 0;
// 	if (ymax >= height) ymax = height-1;

// 	// check for enough space
// 	int size = (xmax-xmin)*(ymax-ymin);
// 	if (result[0] == null || result[0].length < size)
// 	    result[0] = new int[size];
// 	if (result[1] == null || result[1].length < size)
// 	    result[1] = new int[size];

// 	// iterate over bounding box and test inclusion
// 	int index = 0;
// 	for (int i = xmin; i <= xmax; i++) {
// 	    for (int j = ymin; j <= ymax; j++) {
// 		double dx = (i - x) / rx;
// 		double dy = (j - y) / ry;
// 		if (dx * dx + dy * dy <= 1) {
// 		    result[0][index] = i;
// 		    result[1][index] = j;
// 		    index++;
// 		}
// 	    }
// 	}

// 	return index;
//     }
