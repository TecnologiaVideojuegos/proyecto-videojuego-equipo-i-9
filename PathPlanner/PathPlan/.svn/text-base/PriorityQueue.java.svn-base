package PathPlan;

import java.util.*;

/**
 * A priority queue.  Keeps a list of entries (small nonnegative
 * integers) with real-valued priorities, and supports
 * finding/removing the first entry (the one with numerically smallest
 * priority) efficiently.
 */

public class PriorityQueue {

    // flag for empty entries
    final int emptyID = -1;

    // Maximum size of queue (in nodes), current usage
    int maxsize, size;

    // Most recent priority
    double lastpri = 0;
    
    // Data for each node
    int [] ids;			// map from heap position to ID
    int [] directory;		// map from ID to heap position
    double [] priorities;	// map from heap position to priority

    /**
     * A class to do iteration.  Allows us to loop through all
     * elements of the queue (not necessarily in priority order).
     * Similar to a standard Iterator interface, but returns ints
     * rather than Objects for efficiency.
     */
    class Iterator {
	int i = 0;

	public boolean hasNext() {
	    return (i < size);
	}

	public int next() {
	    if (i < size) {
		return ids[i++];
	    } else {
		throw new Error("out of elements");
	    }
	}
    }

    /**
     * Get an Iterator for this queue.
     */
    Iterator getIterator() {
	return new Iterator();
    }

    /**
     * Test for the presence of a known ID in the queue.
     * @param id Is this ID present?
     */
    public boolean isPresent(int id) {
	return (directory[id] != emptyID);
    }

    /**
     * Insert an element at the given priority.  If the queue is full,
     * throw an exception.
     * @param id A number between 0 and getSize()-1.  We can use the
     * ID later to reset the element's priority.  It is an error to
     * attempt to put two distinct elements with the same ID in the
     * queue simultaneously.
     * @param priority The numerical priority.  (Lower numbers mean the
     * element will be accessed sooner, which some might say is
     * reversed from the usual definition of priority.)
     */
    public void insert(int id, double priority) {
	if (size >= maxsize)
	    throw new RuntimeException("priority queue full");
	priorities[size] = priority;
	ids[size] = id;
	directory[id] = size;
	{
	    int i = size++;
	    percolateUp(i);
	}
    }

    /**
     * Remove an element from the queue if it's there.  If it wasn't
     * there, throw an exception.
     * @param id The element to remove.
     * @return The element's priority.
     */
    public double remove(int id) {
	
	// Find the element and remove it
	int slot = directory[id];
	if (slot == emptyID)
	    throw new RuntimeException("tried to remove nonexistent node");
	lastpri = priorities[slot];

	// Remove it, rearranging heap if necessary
	directory[id] = emptyID;
	size--;
	if (size != slot) {
	    swap(slot, size);
	    percolateDown(slot);
	    percolateUp(slot);
	}
	return lastpri;
    }

    /**
     * Remove and return the first element.  (The first element is the
     * one with smallest numerical priority value, which some might
     * say is reversed from the usual definition of priority.)  If the
     * queue was empty, throw an exception.
     */
    public int pop() {

	// Get the top element
	if (size == 0)
	    throw new RuntimeException("popped empty queue");
	int res = ids[0];
	lastpri = priorities[0];

	// Remove it, rearrange heap if necessary
	directory[ids[0]] = emptyID;
	size--;
	if (size > 0) {
	    swap(0, size);
	    percolateDown(0);
	}
	return res;
    }

    /**
     * If an element is in the priority queue at a later priority,
     * raise it; if it is not there, insert it.  
     * @param id The element ID.
     * @param priority The new priority.
     * @return Returns true iff we changed anything.
     */
    public boolean raise(int id, double priority) {
	int elemID = directory[id];
	if (elemID == emptyID) {
	    insert(id, priority);
	    return true;
	} else if (priorities[elemID] > priority) {
	    priorities[elemID] = priority;
	    percolateUp(elemID);
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Reset the priority of an element, or insert it if it's not
     * already there.
     * @param id The element ID.
     * @param priority The element priority.
     */
    public void set(int id, double priority) {
	int elemID = directory[id];
	if (elemID == emptyID) {
	    insert(id, priority);
	} else if (priorities[elemID] < priority) {
	    priorities[elemID] = priority;
	    percolateDown(elemID);
	} else {
	    priorities[elemID] = priority;
	    percolateUp(elemID);
	}
    }

    /**
     * Find the priority of the last node that was removed from
     * the queue (by remove or pop).
     */
    public double getLastPriority() {
	return lastpri;
    }

    /**
     * Find the size of the queue (number of pops that we can do
     * before emptying it).
     */
    public int getSize() {
	return size;
    }

    /**
     * Find the largest size that we can make the queue.
     */
    public int getMaxSize() {
	return maxsize;
    }

    /**
     * Get the ids of all nodes in the queue.
     */
    public int [] getIDs() {
	int [] res = new int[size];
	for (int i = 0; i < size; i++) {
	    res[i] = ids[i];
	}
	return res;
    }

    /**
     * Get the priority of a given id.
     * @param id The node whose priority we need.
     */
    public double getPriority(int id) {
	return priorities[directory[id]];
    }

    /**
     * Empty out the queue.
     */
    public void empty() {
	size = 0;
	for (int i = 0; i < maxsize; i++) {
	    directory[i] = emptyID;
	}
    }

    /**
     * Make a new queue.
     * @param limit The queue can contain at most limit nodes.
     */
    public PriorityQueue(int limit) {
	maxsize = limit;
	ids = new int[limit];
	directory = new int[limit];
	priorities = new double[limit];
	empty();
    }


    ////////////////////////////////////////////////////////////////
    // Internal methods
    ////////////////////////////////////////////////////////////////


    // Sanity check: is our heap in order?
    // If not, throw an exception.
    void checkHeap() {
	int i, par;
	for (i = 1; i < size; i++) {
	    par = (i-1) / 2;
	    if (priorities[par] > priorities[i]) {
		throw new RuntimeException("heap violation detected");
	    }
	}
    }	

    // swap two nodes in the heap
    void swap(int i, int j) {
	int swapi;
	double swapd;

	swapi = ids[j];
	ids[j] = ids[i];
	ids[i] = swapi;
	swapd = priorities[j];
	priorities[j] = priorities[i];
	priorities[i] = swapd;

	directory[ids[i]] = i;
	directory[ids[j]] = j;
    }

    // We have just made the priority at the given heap index
    // numerically smaller.  Swap nodes to restore the heap property.
    void percolateUp(int i) {
	int par = (i-1) / 2;

	while (i != 0 && priorities[par] > priorities[i]) {
	    swap(i, par);
	    i = par;
	    par = (i-1) / 2;
	}
    }

    // We have just made the priority at the given heap index
    // numerically larger.  Swap nodes to restore the heap property.
    void percolateDown(int i) {
	int child;
	int swapi;
	double swapd;

	while ((child = i*2 + 1) < size) {
	    if (child < size-1 && priorities[child] > priorities[child+1])
		child += 1;
	    if (priorities[i] <= priorities[child])
		break;

	    swap(i, child);
	    i = child;
	}
    }


    ////////////////////////////////////////////////////////////////
    // Main method for testing
    ////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
	double data[] = { 4, 1, 2, 3, 6, 1, 2, 8, 9, 3 };
	int ids[]     = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 5 };
	int inq[];

	PriorityQueue pq = new PriorityQueue(100);
	for (int i = 0; i < 10; i++)
	    pq.set(ids[i], data[i]);
	pq.checkHeap();
	inq = pq.getIDs();
	for (int i = 0; i < inq.length; i++) {
	    System.out.print("(" + inq[i] + ", " + pq.getPriority(inq[i])
			     + ") ");
	}
	System.out.print("\n");
	for (int i = 0; i < 9; i++) {
	    int id = pq.pop();
	    System.out.print("popped " + pq.getLastPriority() + " -- id " +
			     id + "\n");
	}
	System.out.print("Queue size is now " + pq.getSize() + "\n");
    }
}
