package by.it.group310971.ivankov.lesson13;

import java.util.*;

public class GraphC extends GraphB {

    // Constructor calling the parent class constructor with custom separator
    public GraphC(Scanner scanner) {
        super(scanner, "->");
    }

    // Main method to execute the program and print the strongly connected components
    public static void main(String[] args) {
        System.out.print(String.join("\n",
                new GraphC(new Scanner(System.in)).getStronglyConnected()));
    }

    // Method to get the strongly connected components (SCCs)
    public List<String> getStronglyConnected() {
        Stack<String> visited = new Stack<>();
        Map<String, Integer> time = new HashMap<>();
        int times = 0;

        // Traverse all nodes and record their finish times
        for (String node : elements.keySet()) {
            if (!visited.contains(node)) {
                times = traverse(node, visited, time, times);
            }
        }

        // Sort vertices based on their finish times
        List<String> sortedVertices = sortedVertices(time);

        // Get strongly connected components by performing DFS on reversed graph
        return getPaths(sortedVertices.toArray(new String[0]));
    }

    // Traverse the graph and record the finish times for each node
    private int traverse(String node, Stack<String> visited, Map<String, Integer> time, int times) {
        visited.add(node);

        // Traverse through the neighbors
        if (elements.get(node) != null) {
            for (String nextNode : elements.get(node)) {
                if (!visited.contains(nextNode)) {
                    times = traverse(nextNode, visited, time, ++times);
                }
            }
        }

        // Record the finish time for the node
        time.put(node, times++);
        return times;
    }

    // Sort the vertices based on their finish times in descending order
    private List<String> sortedVertices(Map<String, Integer> time) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(time.entrySet());
        entryList.sort((a, b) -> {
            int valueComparison = a.getValue().compareTo(b.getValue());
            return valueComparison == 0 ? a.getKey().compareTo(b.getKey()) : valueComparison;
        });

        // Collect sorted vertices
        List<String> vertices = new ArrayList<>();
        for (int i = entryList.size() - 1; i >= 0; i--) {
            vertices.add(entryList.get(i).getKey());
        }
        return vertices;
    }

    // Get the paths corresponding to strongly connected components
    private List<String> getPaths(String[] vertices) {
        Stack<String> visited = new Stack<>();
        Map<String, ArrayList<String>> reversed = getReversedGraph();
        List<String> result = new ArrayList<>();

        for (String node : vertices) {
            if (!visited.contains(node)) {
                List<String> path = new ArrayList<>();
                dfs(node, reversed, visited, path);
                path.sort(String::compareTo); // Sort each component lexicographically
                result.add(String.join("", path));
            }
        }
        return result;
    }

    // Get the reversed graph
    public Map<String, ArrayList<String>> getReversedGraph() {
        Map<String, ArrayList<String>> reversed = new HashMap<>();
        elements.forEach((node, neighbors) -> {
            neighbors.forEach(next -> {
                reversed.computeIfAbsent(next, k -> new ArrayList<>()).add(node);
            });
        });
        return reversed;
    }

    // Depth-first search (DFS) to find a path in the reversed graph
    private void dfs(String node, Map<String, ArrayList<String>> graph, Stack<String> visited, List<String> path) {
        visited.add(node);
        path.add(node);

        // Visit all the neighbors
        if (graph.get(node) != null) {
            for (String nextNode : graph.get(node)) {
                if (!visited.contains(nextNode)) {
                    dfs(nextNode, graph, visited, path);
                }
            }
        }
    }
}
