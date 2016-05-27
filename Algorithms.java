/**
 *  Algorithms.java
 *
 *  File that contains the different algorithms for finding the
 *  maximum flow in a network flow graph.
 *
 *  @author Chris Smith
 *  @version 8.25.2015
 */

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class Algorithms
{
    /*
     * Method that finds an augmenting path using a depth-first search.
     * @param g is the graph to find an augmenting path in
     * @param start is the vertex to start from
     * @param l is the list that holds the vertices used in the augmenting path
     * @return a list of the vertices on the augmenting path
     */
    private List<Integer> dfsAugmentingPath(Graph g, int start, List<Integer> l)
    {
        ArrayList<Integer> s = new ArrayList<Integer>();
        Boolean[] visited = new Boolean[g.order()];
        int[] parents = new int[g.order()];
        for(int i = 0; i < visited.length; i++){
            visited[i] = false;
            parents[i] = -1;
        }
        int[][] edgeMatrix = g.getMatrix();
        ArrayList<LinkedList<Integer>> list = g.getList();
        int endVertex = 0;
        visited[start] = true;
        s.add(endVertex);
        do
        {
            endVertex = (int) s.remove(s.size()-1);
            visited[endVertex] = true;
            for(int i = 0; i < list.get(endVertex).size(); i++)
            {
                if(!visited[list.get(endVertex).get(i)])
                {
                    parents[list.get(endVertex).get(i)] = endVertex;
                    s.add(list.get(endVertex).get(i));
                    if(list.get(endVertex).get(i) == g.order() - 1)
                    {
                        int temp = g.order() -1;
                        while(temp != -1)
                        {
                            l.add(temp);
                            temp = parents[temp];
                        }
                        Collections.reverse(l);
                        return l;
                    }
                }
            }
        }
        while(!s.isEmpty());
        return l;
    }

    /*
     * Method that finds an augmenting path using a breadth-first search.
     * @param g is the graph to find an augmenting path in
     * @param start is the vertex to start from
     * @param l is the list that holds the vertices used in the augmenting path
     * @return the vertices on the augmenting path
     */
    private List<Integer> bfsAugmentingPath(Graph g, int start, List<Integer> l)
    {
        LinkedList<Integer> q = new LinkedList<Integer>();
        Boolean[] visited = new Boolean[g.order()];
        int[] parents = new int[g.order()];
        for(int i = 0; i < visited.length; i++){
            visited[i] = false;
            parents[i] = -1;
        }
        int[][] edgeMatrix = g.getMatrix();
        ArrayList<LinkedList<Integer>> list = g.getList();
        int endVertex = 0;
        visited[start] = true;
        q.add(endVertex);
        while(q.size() != 0)
        {
            endVertex = (int) q.removeFirst();
            for(int i = 0; i < list.get(endVertex).size(); i++)
            {
                if(!visited[list.get(endVertex).get(i)])
                {
                    visited[list.get(endVertex).get(i)] = true;
                    parents[list.get(endVertex).get(i)] = endVertex;
                    q.add(list.get(endVertex).get(i));
                    if(list.get(endVertex).get(i) == g.order() - 1)
                    {
                        int temp = g.order() -1;
                        while(temp != -1)
                        {
                            l.add(temp);
                            temp = parents[temp];
                        }
                        Collections.reverse(l);
                        return l;
                    }
                }
            }
        }
        return l;
    }

    /*
     * Method that uses an augmenting path to find the quickest path to each vertex in the graph.
     * @param g is the graph to find an augmenting path in
     * @param start is the vertex to start from
     * @return an array of what layer that the vertices are on
     */
    private int[] bfsLayers(Graph g, int start)
    {
        LinkedList<Integer> q = new LinkedList<Integer>();
        Boolean[] visited = new Boolean[g.order()];
        int[] layers = new int[g.order()];
        for(int i = 0; i < visited.length; i++){
            visited[i] = false;
            layers[i] = -1;
        }
        int[][] edgeMatrix = g.getMatrix();
        ArrayList<LinkedList<Integer>> list = g.getList();
        int endVertex = 0;
        visited[start] = true;
        q.add(endVertex);
        layers[0] = 0;
        int level = 1;
        while(q.size() != 0)
        {
            endVertex = (int) q.removeFirst();
            for(int i = 0; i < list.get(endVertex).size(); i++)
            {
                if(!visited[list.get(endVertex).get(i)])
                {
                    visited[list.get(endVertex).get(i)] = true;
                    q.add(list.get(endVertex).get(i));
                    layers[list.get(endVertex).get(i)] = layers[endVertex] + 1;
                    if(list.get(endVertex).get(i) == g.order() - 1)
                    {
                        return layers;
                    }
                }
            }
        }
        return layers;
    }

    /*
     * Method that represents the Ford-Fulkerson algorithm.
     * @param g is the graph to find the max flow on
     * @return the graph after Ford-Fulkerson has been completed
     */
    public Graph fordFulkerson(Graph g, boolean t)
    {
        int flow = 0;
        Graph newG = g;
        int[][] newMatrix = newG.getMatrix();
        ArrayList<LinkedList<Integer>> list = g.getList();
        List<Integer> verticesVisited = dfsAugmentingPath(newG, 0, new ArrayList<Integer>(1));
        int maxFlow = 0;
        while(verticesVisited.size() > 1)
        {
            if(!t)
            {
                System.out.println("---Current Adjacency Matrix:---");
                g.printMatrix();
                System.out.println();
            }
            int min = newMatrix[verticesVisited.get(0)][verticesVisited.get(1)];
            for(int i = 2; i < verticesVisited.size(); i++)
            {
                int edgeWeight = newMatrix[verticesVisited.get(i-1)][verticesVisited.get(i)];
                if (edgeWeight < min)
                {
                    min = edgeWeight;
                }
            }
            if(verticesVisited.contains(g.order() - 1))
            {
                maxFlow += min;
                for(int i = 1; i < verticesVisited.size(); i++)
                {
                    int first = verticesVisited.get(i-1);
                    int second = verticesVisited.get(i);
                    newMatrix[first][second] -= min;
                    newG.addToTaken(first, second, min);
                    if (newMatrix[first][second] == 0)
                    {
                        list.get(first).remove((Integer) second);
                    }
                    if(newMatrix[second][first] == 0)
                    {
                        list.get(second).add((Integer) first);
                    }
                    newMatrix[second][first] += min;
                }
                if(!t)
                {
                    System.out.println("Augmenting Path: " + verticesVisited + " ==> " + min);
                }
            }
            newG.setMatrix(newMatrix);
            if(!t)
            {
                System.out.println("---Generated Residual Matrix:---");
                g.printMatrix();
                System.out.println();
            }
            newG.setList(list);
            verticesVisited = dfsAugmentingPath(newG, 0, new ArrayList<Integer>(1));
        }
        System.out.println("Optimal Flow: " + maxFlow);
        newG.printTaken();
        return g;
    }

    /*
     * Method that simulates the Edmonds-Karp algorithm.
     * @param g is the graph to find the max flow on
     * @return the graph after the Edmonds-Karp algorithm has been completed
     */
    public Graph edmondsKarp(Graph g, boolean t)
    {
        int flow = 0;
        Graph newG = g;
        int[][] newMatrix = newG.getMatrix();
        ArrayList<LinkedList<Integer>> list = g.getList();
        List<Integer> verticesVisited = bfsAugmentingPath(newG, 0, new ArrayList<Integer>(1));
        int maxFlow = 0;
        while(verticesVisited.size() > 1)
        {
            if(!t)
            {
                System.out.println("---Current Adjacency Matrix:---");
                g.printMatrix();
                System.out.println();
            }
            int min = newMatrix[verticesVisited.get(0)][verticesVisited.get(1)];
            for(int i = 2; i < verticesVisited.size(); i++)
            {
                int edgeWeight = newMatrix[verticesVisited.get(i-1)][verticesVisited.get(i)];
                if (edgeWeight < min)
                {
                    min = edgeWeight;
                }
            }
            if(verticesVisited.contains(g.order() - 1))
            {
                maxFlow += min;
                for(int i = 1; i < verticesVisited.size(); i++)
                {
                    int first = verticesVisited.get(i-1);
                    int second = verticesVisited.get(i);
                    newMatrix[first][second] -= min;
                    newG.addToTaken(first, second, min);
                    if (newMatrix[first][second] == 0)
                    {
                        list.get(first).remove((Integer) second);
                    }
                    if(newMatrix[second][first] == 0)
                    {
                        list.get(second).add((Integer) first);
                    }
                    newMatrix[second][first] += min;
                }
                if(!t)
                {
                    System.out.println("Augmenting Path: " + verticesVisited + " ==> " + min);
                }
            }
            newG.setMatrix(newMatrix);
            if(!t)
            {
                System.out.println("---Generated Residual Matrix:---");
                g.printMatrix();
                System.out.println();
            }
            newG.setList(list);
            verticesVisited = bfsAugmentingPath(newG, 0, new ArrayList<Integer>(1));
        }
        System.out.println("Optimal Flow: " + maxFlow);
        newG.printTaken();
        return g;
    }

    /*
     * Method that runs Dinic's algorithm on a graph.
     * @param g is the graph to find the max flow on
     * @return the graph after Dinic's algorithm has been completed
     */
    public Graph dinic(Graph g, boolean t)
    {
        int maxFlow = 0;
        Graph newG = g;
        int end = g.order() - 1;
        int[][] newMatrix = newG.getMatrix();
        ArrayList<LinkedList<Integer>> list = g.getList();
        List<Integer> augPath;
        int[] layers = bfsLayers(g, 0);
        int min;
        while(layers[end] != -1)
        {
            int distance = layers[end];
            int currentDis = distance;
            if(!t)
            {
                System.out.println("Length from Source to Terminal: " + currentDis);
                System.out.println("---Current Adjacency Matrix:---");
                g.printMatrix();
                System.out.println();
            }
            while(distance == currentDis)
            {
                augPath = bfsAugmentingPath(newG, 0, new ArrayList<Integer>());
                if(layers[augPath.get(0)] == layers[augPath.get(1)] - 1)
                {
                    min = newMatrix[augPath.get(0)][augPath.get(1)];
                }
                else
                {
                    min = 0;
                }
                for(int i = 2; i < augPath.size(); i++)
                {
                    int edgeWeight = newMatrix[augPath.get(i-1)][augPath.get(i)];
                    if (edgeWeight < min && layers[augPath.get(i-1)] == layers[augPath.get(i)] - 1)
                    {
                        min = edgeWeight;
                    }
                }
                maxFlow += min;
                for(int i = 1; i < augPath.size(); i++)
                {
                    int first = augPath.get(i-1);
                    int second = augPath.get(i);
                    newMatrix[first][second] -= min;
                    newG.addToTaken(first, second, min);
                    if (newMatrix[first][second] == 0)
                    {
                        list.get(first).remove((Integer) second);
                    }
                    if(newMatrix[second][first] == 0)
                    {
                        list.get(second).add((Integer) first);
                    }
                    newMatrix[second][first] += min;
                }
                if(!t)
                {
                    System.out.println("Augmenting Path: " + augPath + " ==> " + min);
                }
                layers = bfsLayers(g, 0);
                currentDis = layers[g.order() - 1];
            }
            newG.setMatrix(newMatrix);
            if(!t)
            {
                System.out.println("---Generated Residual Matrix:---");
                g.printMatrix();
                System.out.println();
            }
            newG.setList(list);
            layers = bfsLayers(newG, 0);
        }
        System.out.println("Optimal Flow: " + maxFlow);
        newG.printTaken();
        return g;
    }

    /*
     * Method that executes Karger's Min-Cut algorithm on a graph.
     * @param g is the graph to find the max flow on
     * @return the graph after Karger's Min-Cut algorithm is done
     */
    public Graph kargerMinCut(Graph g, boolean t)
    {
        int numEdges = g.size();
        ArrayList<LinkedList<Integer>> adjList = g.getList();
        ArrayList<Integer> groupA = new ArrayList<Integer>();
        ArrayList<Integer> groupB = new ArrayList<Integer>();
        int[] verts = new int[g.order()];
        ArrayList<Integer> remVerts = new ArrayList<Integer>(g.order());
        for(int i = 0; i < g.order(); i++)
        {
            verts[i] = i;
            remVerts.add((Integer) i);
        }
        int[][] adjMatrix = g.getMatrix();
        Random random = new Random();
        int firstVertex = 0;
        int secondVertex = 0;
        while(remVerts.size() > 2)
        {
            int chosenEdge = random.nextInt(numEdges - 1) + 1;
            int counter = 0;
            for(int j = 0; j < g.order(); j++)
            {
                if(adjList.get(j).size() + counter >= chosenEdge)
                {
                     firstVertex = j;
                     secondVertex = adjList.get(j).get(chosenEdge - counter - 1);
                     break;
                }
                else
                {
                     counter += adjList.get(j).size();
                }
            }
            int newCombo = verts[secondVertex];
            remVerts.clear();
            for(int i = 0; i < g.order(); i++)
            {
                if(verts[i] == newCombo)
                {
                    verts[i] = verts[firstVertex];
                }
                if(!remVerts.contains((Integer) verts[i]))
                {
                    remVerts.add((Integer) verts[i]);
                }
            }
            for(int i = 0; i < adjList.get(firstVertex).size(); i++)
            {
                if(verts[firstVertex] == verts[adjList.get(firstVertex).get(i)])
                {
                    int rm = adjList.get(firstVertex).get(i);
                    adjList.get(firstVertex).remove(i);
                    boolean und = adjList.get(rm).remove((Integer) firstVertex);
                    if(und)
                    {
                        numEdges--;
                    }
                    numEdges--;
                }
            }
            for(int i = 0; i < adjList.get(secondVertex).size(); i++)
            {
                if(verts[secondVertex] == verts[adjList.get(secondVertex).get(i)])
                {
                    int rm = adjList.get(secondVertex).get(i);
                    adjList.get(secondVertex).remove(i);
                    boolean und = adjList.get(rm).remove((Integer) secondVertex);
                    if(und)
                    {
                        numEdges--;
                    }
                    numEdges--;
                }
            }
            if(!t)
            {
                System.out.println("Combined vertices " + firstVertex + " and " + secondVertex);
            }
        }
        int maxFlow = 0;
        groupA.add(0);
        for(int i = 1; i < verts.length; i++)
            {
                if(verts[i] == verts[0])
                {
                    groupA.add(i);
                }
                else
                {
                    groupB.add(i);
                }
            }
        if(!t)
        {
            System.out.println("\nFinal Two Groups of Vertices: ");
            System.out.println("Group A: " + groupA);
            System.out.println("Group B: " + groupB);
        }
        System.out.println("\nEdges between the two groups of vertices: ");
        for(int i = 0; i < g.order(); i++)
        {
            for(int j = 0; j < g.order(); j++)
            {
                if(verts[i] != verts[j] && adjMatrix[i][j] != 0)
                {
                    System.out.println("(" + i + ", " + j + ") : " + adjMatrix[i][j]);
                    maxFlow += adjMatrix[i][j];
                }
            }
        }
        if(g instanceof UndirectedGraph)
        {
            maxFlow /= 2;
        }
        System.out.println("Optimal Flow: " + maxFlow);
        return g;
    }
}

