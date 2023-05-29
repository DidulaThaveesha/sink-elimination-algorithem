import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Ascynicgraph {
    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph();
        // Read input from file and build the graph
        try (Scanner scanner = new Scanner(new File("Cyclic.txt"))) {
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split("\\s+");
                int fromVertex = Integer.parseInt(tokens[0]);
                int toVertex = Integer.parseInt(tokens[1]);
                graph.addEdge(fromVertex, toVertex);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: input file not found");
            return;
        } catch (NumberFormatException e) {
            System.err.println("Error: invalid input format");
            return;
        }
// Find and print the cycle, if present
        List<Integer> cycle = findCycle(graph);
        if (cycle == null) {
            System.out.println("The graph is acyclic.");
        } else {
            System.out.println("The graph contains a cycle.");
            System.out.print("The cycle is: ");
            for (int i = 0; i < cycle.size(); i++) {
                System.out.print(cycle.get(i));
                if (i != cycle.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }
    // Function to find a cycle in the directed graph using DFS
    private static List<Integer> findCycle(DirectedGraph graph) {
        // Perform DFS to find a cycle
        Set<Integer> visited = new HashSet<>(); // Set to track visited vertices
        Set<Integer> recursionStack = new HashSet<>();// Set to track vertices in the current recursion stack

        Map<Integer, Integer> parent = new HashMap<>();// Map to track the parent vertex during DFS traversal
        List<Integer> cycle = new ArrayList<>();

        for (int v : graph.getVertices()) {
            // Start DFS from each unvisited vertex
            if (!visited.contains(v) && findCycleHelper(graph, v, visited, recursionStack, parent, cycle)) {
                return cycle; // If a cycle is found, return it
            }
        }

        return null;// If no cycle is found, return null
    }

    private static boolean findCycleHelper(DirectedGraph graph, int v, Set<Integer> visited, Set<Integer> recursionStack, Map<Integer, Integer> parent, List<Integer> cycle) {
        visited.add(v);// Mark current vertex as visited
        recursionStack.add(v);// Add current vertex to recursion stack
        for (int neighbor : graph.getNeighbors(v)) {
            if (!visited.contains(neighbor)) {
                parent.put(neighbor, v);// Set parent of the neighbor vertex

                if (findCycleHelper(graph, neighbor, visited, recursionStack, parent, cycle)) {
                    cycle.add(v);
                    return true;
                }
            } else if (recursionStack.contains(neighbor)) {
                parent.put(neighbor, v); // Set parent of the neighbor vertex
                cycle.add(neighbor);
                cycle.add(v);
                return true; // Cycle found
            }
        }
        recursionStack.remove(v);// Remove current vertex from recursion stack
        return false;
    }
}
