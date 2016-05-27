/*
 * UndirectedGraph.java
 * This class is representative of an undirected graph.
 * @author Chris Smith
 * @version 7.27.2015
 *
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class UndirectedGraph extends Graph
{
    private int[][] matrix;
    private int[][] takenMatrix;
    private int[][] capacities;
    private ArrayList<LinkedList<Integer>> list = new ArrayList<LinkedList<Integer>>();
    private int edges;
    /*
     * No-arg constructor for UndirectedGraph.
     */
    public UndirectedGraph()
    {
        matrix = new int[1][1];
        takenMatrix = new int[1][1];
        capacities = new int[1][1];
        list.add(new LinkedList<Integer>());
        edges = 0;
    }
    /*
     * One-arg constructor for UndirectedGraph.
     * @param n is the number of vertices for the graph
     */
    public UndirectedGraph(int n)
    {
        matrix = new int[n][n];
        takenMatrix = new int[n][n];
        capacities = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            list.add(new LinkedList<Integer>());
            for (int j = 0; j < n; j++)
            {
                matrix[i][j] = 0;
                capacities[i][j] = 0;
                takenMatrix[i][j] = 0;
            }
        }
        edges = 0;
    }
    /*
     * Getter for the graph's matrix.
     * @return returns the matrix as a 2D array of ints
     */
    public int[][] getMatrix()
    {
        return matrix;
    }

    /*
     * Setter for the graph's matrix.
     * @param matrix is the new matrix.
     */
    public void setMatrix(int[][] matrix)
    {
       matrix = this.matrix;
    }

    /*
     * Getter for the graph's takenMatrix.
     * @return returns the matrix as a 2D array of ints
     */
    public int[][] getTakenMatrix()
    {
        return takenMatrix;
    }

    /*
     * Setter for the graph's taken matrix.
     * @param matrix is the new taken matrix.
     */
    public void setTakenMatrix(int[][] takenMatrix)
    {
        takenMatrix = this.takenMatrix;
    }

    /*
     * Getter for the graph's list.
     * @return returns the adjacency list
     */
    public ArrayList<LinkedList<Integer>> getList()
    {
        return list;
    }

    /*
     * Setter for the graph's list.
     * @param matrix is the new adjacency list.
     */
    public void setList(ArrayList<LinkedList<Integer>> list)
    {
        list = this.list;
    }

    /*
     * Method that resizes the graph.
     * @param numVertices is the number of vertices for the graph
     */
    public void resize(int numVertices)
    {
        int[][] newMatrix = new int[numVertices][numVertices];
        int[][] newTaken = new int[numVertices][numVertices];
        int[][] newCap = new int[numVertices][numVertices];
        if (numVertices > matrix.length)
        {
            for (int i = 0; i < matrix.length; i++)
            {
                for (int j = 0; j < matrix.length; j++)
                {
                    newMatrix[i][j] = matrix[i][j];
                    newTaken[i][j] = takenMatrix[i][j];
                    newCap[i][j] = capacities[i][j];
                }
                if(list.size() <= i)
                {
                    list.add(new LinkedList<Integer>());
                }
            }
        }
        matrix = newMatrix;
        takenMatrix = newTaken;
        capacities = newCap;
    }
    /*
     * Returns the number of vertices in the graph.
     * @return returns the number of vertices
     */
    public int order()
    {
        return matrix.length;
    }
    /*
     * Returns the number of edges in the graph.
     * @return returns the number of edges
     */
    public int size()
    {
        return edges;
    }
    /*
     * Returns whether or not vertices i and j are connected.
     * @param i is the first vertex
     * @param j is the second vertex
     * @return returns whether or not they are connected
     */
    public boolean isEdge(int i, int j)
    {
        return matrix[i][j] > 0;
    }
    /*
     * Makes the two vertices(i and j) neighbors with capacity k.
     * @param i is the first vertex
     * @param j is the second vertex
     * @param k is the capacity of the edge
     */
    public void addEdge(Integer i, Integer j, Integer k)
    {
        matrix[i][j] = matrix[j][i] = k;
        capacities[i][j] = capacities[j][i] = k;
        list.get(i).add(j);
        list.get(j).add(i);
        edges += 2;
    }
    /*
     * Prints the graph info.
     */
    public void printGraph()
    {
        System.out.println("Edges and their capacity: ");
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                if (isEdge(i, j))
                {
                    System.out.println("(" + i + ", " +  j + "): " + matrix[i][j]);
                }
            }
        }
    }
    /*
     * Reads the graph from a file.
     * @param fileName is the file to read in
     * @throws IOException if file not found
     * @return returns a graph representative of the data
     */
    public UndirectedGraph reader(String fileName) throws FileNotFoundException
    {
        UndirectedGraph graph = new UndirectedGraph();
        try
        {
            File f = new File(fileName);
            Scanner scanner = new Scanner(f);
            int count = 0;
            String line = scanner.nextLine();
            String[] splitLine = line.split("\\s+");
            do
            {
                if (count == 0)
                {
                    graph = new UndirectedGraph(Integer.parseInt(splitLine[0]));
                }
                else
                {
                    graph.addEdge(Integer.parseInt(splitLine[0]),
                            Integer.parseInt(splitLine[1]),
                            Integer.parseInt(splitLine[2]));
                }
                count++;
                line = scanner.nextLine();
                splitLine = line.split("\\s+");
            }
            while (Integer.parseInt(splitLine[0]) != -1);
            scanner.close();
            return graph;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
        }
        return new UndirectedGraph();
    }

    /*
     *  Prints the adjacency matrix.
     */
    public void printMatrix()
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix.length; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /*
     * Adds to the taken matrix.  Meant to keep track of the number of items
     * going over each edge.
     * @param i is the row
     * @param j is the column
     * @param value is the value to add to the taken matrix
     */
    public void addToTaken(int i, int j, int value)
    {
        takenMatrix[i][j] += value;
    }

    /*
     * Prints the taken edges and their weights.
     */
    public void printTaken()
    {
        System.out.println("Taken edges and their capacity: ");
        for (int i = 0; i < takenMatrix.length; i++)
        {
            for (int j = 0; j < takenMatrix.length; j++)
            {
                if (takenMatrix[i][j] != 0 && capacities[i][j] != 0)
                {
                    System.out.println("(" + i + ", " +  j + "): " + takenMatrix[i][j] + "/" + (capacities[i][j]));
                }
            }
        }
    }
}

