import java.util.*;
public class DirectedGraph {
    //creat a hashmap with linked lsit//task 2
    private Map<Integer, List<Integer>> adjacency_List;

//convert the hashmap in to the string 
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<Integer, List<Integer>> entry : adjacency_List.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(", ");
        }
        if (!adjacency_List.isEmpty()) {

            sb.setLength(sb.length() - 2);
        }
        sb.append("}");


        System.out.println(sb.toString());


        return null;
    }
    //creat a directed graph method//task 2
    public DirectedGraph() {
        adjacency_List = new HashMap<>();
    }
//add vertexes to adjacency List
    public void addVertex(int vertex) {
        adjacency_List.put(vertex, new ArrayList<>());
    }
//add edges to the adjacency List
    public void addEdge(int SVertex, int EVertex) {
        if (!adjacency_List.containsKey(SVertex)) { //check start Vertexes in the list
            addVertex(SVertex);
        }
        if (!adjacency_List.containsKey(EVertex)) {   //check end Vertexes in the list
            addVertex(EVertex);
        }
        adjacency_List.get(SVertex).add(EVertex);  //add the start or end Vertexes to adjacency List
    }

    public boolean hasVertex(int vertex) {  //check given Vertexes is presenting the adjacency List
        return adjacency_List.containsKey(vertex);
    }

    public boolean hasEdge(int SVertex, int EVertex) {   //check given edges is presenting the adjacency List
        if (!hasVertex(SVertex)) {
            return false;
        }
        List<Integer> neighbors = adjacency_List.get(SVertex);
        return neighbors.contains(EVertex);
    }

    public List<Integer> getNeighbors(int vertex) { //this method check is there any neighbors in the adjacency List
        return adjacency_List.get(vertex);
    }

    public boolean isEmpty() {
        return adjacency_List.isEmpty();
    }

    public int size() {
        return adjacency_List.size();
    }

    public int findSink() {  //find the sink node //task 2
        int sink = -1;
        for (int vertex : adjacency_List.keySet()) {
            if (adjacency_List.get(vertex).isEmpty()) {
                if (sink == -1) {
                    sink = vertex;
                } else {
                    return -1; // multiple sinks found
                }
            }
        }
        return sink;
    }

    public void removeVertex(int vertex) {
        adjacency_List.remove(vertex);

        List<Integer> sinksToRemove = new ArrayList<>(); // Track sink vertices

        for (int v : adjacency_List.keySet()) {
            List<Integer> neighbors = adjacency_List.get(v);
            if (neighbors.contains(vertex)) {
                neighbors.remove((Integer) vertex);
            }
            // Check if the current vertex has become a sink after removing the vertex
            if (neighbors.isEmpty()) {
                sinksToRemove.add(v);
            }
        }

        // Remove the sink vertices
        for (int sink : sinksToRemove) {
            adjacency_List.remove(sink);
        }
    }


    public void removeEdge(int SVertex, int EVertex) {

        if (hasEdge(SVertex, EVertex)) {
            adjacency_List.get(SVertex).remove((Integer) EVertex);
        }
    }
    public boolean isAcyclic() {
        // Set to keep track of visited vertices
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();
        // Iterate over all vertices in the graph
        for (int vertex : getVertices()) {
            // Check if the vertex has not been visited
            if (!visited.contains(vertex)) {
                // Call the helper method to check for cycles starting from this vertex
                if (isAcyclicHelper(vertex, visited, visiting)) {
                    // If a cycle is found, return true
                    return true;
                }
            }
        }

        return false;
    }
    public Set<Integer> getVertices() {
        Set<Integer> vertices = new HashSet<>();
// Iterate over each entry in the adjacency list
        for (Map.Entry<Integer, List<Integer>> entry : adjacency_List.entrySet()) {
            vertices.add(entry.getKey());
            vertices.addAll(entry.getValue());
        }
        return vertices;
    }


    private boolean isAcyclicHelper(int vertex, Set<Integer> visited, Set<Integer> visiting) {
        // Mark the current vertex as visited and add it to the visiting set
        visited.add(vertex);
        visiting.add(vertex);
// Get the neighbors of the current vertex
        List<Integer> neighbors = adjacency_List.get(vertex);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    if (isAcyclicHelper(neighbor, visited, visiting)) {
                        return true;
                    }
                } else if (visiting.contains(neighbor)) {
                    System.out.println("Cycle detected: " + neighbor + " is already being visited.");
                    return true;
                }
            }
        }
        // Remove the current vertex from the visiting set
        visiting.remove(vertex);
        return false; // No cycle found for the current vertex, return false
}

}