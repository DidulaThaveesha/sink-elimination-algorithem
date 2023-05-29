import java.io.*;
import java.util.*;
public class GraphParser {
    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph(); // Create a DirectedGraph object
        try (Scanner scanner = new Scanner(new File("Cyclic.txt"))) {
            while (scanner.hasNextLine()) {
                // Read each line from the file and split it into tokens
                String[] tokens = scanner.nextLine().split("\\s+");
                // Parse the tokens to get the source and destination vertices
                int fromVertex = Integer.parseInt(tokens[0]);
                int toVertex = Integer.parseInt(tokens[1]);
                // Add an edge to the graph between the source and destination vertices
                graph.addEdge(fromVertex, toVertex);
            }
        } catch (FileNotFoundException e) {
            // Handle the case when the input file is not found
            System.err.println("Error: input file not found");
            return;
        } catch (NumberFormatException e) {
            // Handle the case when the input format is invalid (cannot parse integers)
            System.err.println("Error: invalid input format");
            return;
        }
        // Print the graph
        System.out.println("Graph:");
        System.out.println(graph.toString());
        // Find the sink vertex in the graph
        System.out.println("Sink vertex: " + graph.findSink());
        graph.removeVertex(graph.findSink());
        System.out.println("Graph after removing vertex:");
        System.out.println(graph.toString());
        // Check if the graph is acyclic
        boolean isAcyclic = graph.isAcyclic();

        if (isAcyclic==true){
            System.out.println("yes");
        }else {
            System.out.println("No");
        }


    }
}