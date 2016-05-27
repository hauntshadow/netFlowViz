/*
 * Graph.java
 * This class is representative of a general graph.
 * @author Chris Smith
 * @version 8.25.2015
 *
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;


public abstract class Graph
{
    private int[][] matrix;
    private int edges;
    private ArrayList<LinkedList<Integer>> list;
    abstract int[][] getMatrix();
    abstract void setMatrix(int[][] matrix);
    abstract ArrayList<LinkedList<Integer>> getList();
    abstract void setList(ArrayList<LinkedList<Integer>> list);
    abstract void resize(int numVertices);
    abstract int order();
    abstract int size();
    abstract boolean isEdge(int i, int j);
    abstract void addEdge(Integer i, Integer j, Integer k);
    abstract void printGraph();
    abstract Graph reader(String fileName) throws FileNotFoundException;
    abstract void printMatrix();
    abstract void addToTaken(int i, int j, int value);
    abstract void printTaken();
}

