/*
 * Visualizer.java
 * This class visualizes a graph, and four algorithms that find the maximum flow.
 * @author: Chris Smith
 * @version: 8.24.2015
 *
 */

import java.util.Scanner;
import java.io.FileNotFoundException;
public class Visualizer
{
    boolean timing_mode = false;
    /*
     * Runs the Ford-Fulkerson algorithm on a graph g with filename s.
     * @param a is the Algorithms class with the algorithm in it
     * @param g is the graph to run the algorithm on
     * @param s is the name of the file that contains edge information for the class
     * @throws FileNotFoundException if the file cannot be found/loaded
     */
    public void runFF(Algorithms a, Graph g, String s) throws FileNotFoundException
    {
        g = g.reader(s);
        if(g.size() != 0)
        {
            if(timing_mode)
            {
                long st_time = System.currentTimeMillis();
                a.fordFulkerson(g, timing_mode);
                System.out.println("Time: " + (System.currentTimeMillis() - st_time) + " ms.");
            }
            else
            {
                a.fordFulkerson(g, timing_mode);
            }
        }
        else
        {
            System.out.println("Empty graph. Load valid graph first.");
        }
    }

    /*
     * Runs the Edmonds-Karp algorithm on a graph g with filename s.
     * @param a is the Algorithms class with the algorithm in it
     * @param g is the graph to run the algorithm on
     * @param s is the name of the file that contains edge information for the class
     * @throws FileNotFoundException if the file cannot be found/loaded
     */
    public void runEK(Algorithms a, Graph g, String s) throws FileNotFoundException
    {
        g = g.reader(s);
        if(g.size() != 0)
        {
            if(timing_mode)
            {
                long st_time = System.currentTimeMillis();
                a.edmondsKarp(g, timing_mode);
                System.out.println("Time: " + (System.currentTimeMillis() - st_time) + " ms.");
            }
            else
            {
                a.edmondsKarp(g, timing_mode);
            }
        }
        else
        {
            System.out.println("Empty graph. Load valid graph first.");
        }
    }

    /*
     * Runs Dinic's algorithm on a graph g with filename s.
     * @param a is the Algorithms class with the algorithm in it
     * @param g is the graph to run the algorithm on
     * @param s is the name of the file that contains edge information for the class
     * @throws FileNotFoundException if the file cannot be found/loaded
     */
    public void runD(Algorithms a, Graph g, String s) throws FileNotFoundException
    {
        g = g.reader(s);
        if(g.size() != 0)
        {
            if(timing_mode)
            {
                long st_time = System.currentTimeMillis();
                a.dinic(g, timing_mode);
                System.out.println("Time: " + (System.currentTimeMillis() - st_time) + " ms.");
            }
            else
            {
                a.dinic(g, timing_mode);
            }
        }
        else
        {
            System.out.println("Empty graph. Load valid graph first.");
        }
    }

    /*
     * Runs Karger's Min-Cut algorithm on a graph g with filename s.
     * @param a is the Algorithms class with the algorithm in it
     * @param g is the graph to run the algorithm on
     * @param s is the name of the file that contains edge information for the class
     * @throws FileNotFoundException if the file cannot be found/loaded
     */
    public void runK(Algorithms a, Graph g, String s) throws FileNotFoundException
    {
        g = g.reader(s);
        if(g.size() != 0)
        {
            if(timing_mode)
            {
                long st_time = System.currentTimeMillis();
                a.kargerMinCut(g, timing_mode);
                System.out.println("Time: " + (System.currentTimeMillis() - st_time) + " ms.");
            }
            else
            {
                a.kargerMinCut(g, timing_mode);
            }
        }
        else
        {
            System.out.println("Empty graph. Load valid graph first.");
        }
    }

    /*
     * Loads the graph from a file.
     * @param g is the graph to run the algorithm on
     * @param s is the name of the file that contains edge information for the class
     * @throws FileNotFoundException if the file cannot be found/loaded
     */
    public void load(Graph g, String s) throws FileNotFoundException
    {
        g = g.reader(s);
    }

    /*
     * Runs the visualizer of network flow algorithms.
     * @throws FileNotFoundException if the file cannot be loaded
     */
    public void run() throws FileNotFoundException
    {
        //Have a graph and an Algorithms object for later use
        Graph g = new DirectedGraph();
        Algorithms a = new Algorithms();
        boolean ended = false;
        Scanner scan = new Scanner(System.in);
        String filename = "";
        while(!ended)
        {
            //MENU
            System.out.println("--Network Flow Visualizer V 1.0--");
            System.out.println("|  1.    Load from file         |");
            System.out.println("|  2.    Ford-Fulkerson         |");
            System.out.println("|  3.     Edmonds-Karp          |");
            System.out.println("|  4.   Karger's Min Cut        |");
            System.out.println("|  5.        Dinic's            |");
            System.out.println("|  6.      Timing Mode          |");
            System.out.println("|  7.         Quit              |");
            System.out.println("---------------------------------");
            //OPTION CHOICE
            System.out.println("Which option do you want to do?");
            String choice = scan.next();
            switch(choice)
            {
                //LOAD
                case "1":
                    //DIRECTED GRAPH?
                    System.out.println("Is the graph a directed graph?");
                    String answer = scan.next();
                    switch(answer)
                    {
                        case "n":
                        case "N":
                        case "no":
                        case "No":
                            g = new UndirectedGraph();
                            break;
                        default:
                            System.out.println("WARNING: Invalid input. Using directed graph as default.");
                        case "y":
                        case "Y":
                        case "yes":
                        case "Yes":
                            g = new DirectedGraph();
                            break;
                    }
                    //LOAD THE FILE
                    System.out.println("Type the name of the file to load:");
                    filename = scan.next();
                    load(g, filename);
                    break;
                //FORD-FULKERSON
                case "2":
                    runFF(a, g, filename);
                    break;
                //EDMONDS-KARP
                case "3":
                    runEK(a, g, filename);
                    break;
                //KARGER'S MIN CUT
                case "4":
                    runK(a, g, filename);
                    break;
                //DINIC'S
                case "5":
                    runD(a, g, filename);
                    break;
                //ACTIVATE/DEACTIVATE TIMING MODE
                case "6":
                    timing_mode = !timing_mode;
                    System.out.println("Timing mode is now " + timing_mode + ".");
                    break;
                //EXIT VISUALIZER
                case "7":
                    ended = true;
                    break;
                //INVALID
                default:
                    System.out.println("INVALID. Please choose a number 1-7.");
                    break;
            }
        }
    }

    /*
     * Runs the arguments.
     * @param args is the arguments to run
     * @throws FileNotFoundException if a file is not available
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        Visualizer v = new Visualizer();
        v.run();
    }
}

