/*
 *  Edge.java
 *
 *  This class represents an edge in a network flow graph.
 *
 *  Each edge must have the following:
 *
 *  -A starting vertex
 *
 *  -An ending vertex
 *
 *  -A weight, otherwise known as the maximum flow that the edge can hold.
 *
 *  @author Chris Smith
 *  @version 7.22.2015
 *
 */
public class Edge
{
    private int start;
    private int end;
    private int capacity;

    /*
     *  Constructor that generates the edge with invalid start and
     *  end points, as well as an invalid capacity.
     */
    public Edge()
    {
        start = -1;
        end = -1;
        capacity = -1;
    }

    /*
     *  Constructor that generates the edge with start and end points,
     *  as well as a capacity.
     */
    public Edge(int start, int end, int capacity)
    {
        this.start = start;
        this.end = end;
        this.capacity = capacity;
    }
    /*
     *  Getter for the starting vertex.
     *  Returns the label for the starting vertex
     */
    public int getStart()
    {
        return start;
    }
    /*
     *  Getter for the ending vertex.
     *  Returns the label for the ending vertex
     */
    public int getEnd()
    {
        return end;
    }
    /*
     *  Getter for the capacity.
     *  Returns the label for the capacity
     */
    public int getCapacity()
    {
        return capacity;
    }
    /*
     *  Setter for the starting vertex.
     *  Sets the label for the starting vertex with the parameter
     */
    public void setStart(int start)
    {
        if(start >= 0)
        {
            this.start = start;
        }
        else
        {
            System.out.println("Cannot be negative.");
        }
    }
    /*
     *  Setter for the ending vertex.
     *  Sets the label for the ending vertex with the parameter
     */
    public void setEnd(int end)
    {
        if(end >= 0)
        {
            this.end = end;
        }
        else
        {
            System.out.println("Cannot be negative.");
        }
    }
    /*
     *  Setter for the capacity.
     *  Sets the capacity for the edge with the parameter
     */
    public void setCapacity(int capacity)
    {
        if(capacity >= 0)
        {
            this.capacity = capacity;
        }
        else
        {
            System.out.println("Cannot be negative.");
        }
    }
}

