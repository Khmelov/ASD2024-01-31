package by.it.group310971.stankevich.lesson13;

import java.util.*;

public class GraphC {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Map<String, List<String>> graph = new HashMap<>();
        Map<String, List<String>> reverseGraph = new HashMap<>();

        // Parse the input
        String[] edges = input.split(",\\s*");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            String from = parts[0].trim();
            String to = parts[1].trim();

            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            reverseGraph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
            graph.putIfAbsent(to, new ArrayList<>()); // Ensure all nodes are present
            reverseGraph.putIfAbsent(from, new ArrayList<>());
        }

        // Perform Kosaraju's algorithm
        List<String> components = findStronglyConnectedComponents(graph, reverseGraph);

        // Output the result
        components.forEach(System.out::println);
    }

    private static List<String> findStronglyConnectedComponents(Map<String, List<String>> graph, Map<String, List<String>> reverseGraph) {
        // Step 1: Perform DFS on the original graph to determine the finishing times
        Set<String> visited = new HashSet<>();
        Stack<String> finishStack = new Stack<>();

        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                dfs(node, graph, visited, finishStack);
            }
        }

        // Step 2: Perform DFS on the reverse graph in the order of decreasing finishing times
        visited.clear();
        List<String> result = new ArrayList<>();
        List<List<String>> rawComponents = new ArrayList<>();

        while (!finishStack.isEmpty()) {
            String node = finishStack.pop();
            if (!visited.contains(node)) {
                List<String> component = new ArrayList<>();
                dfsCollect(node, reverseGraph, visited, component);
                Collections.sort(component); // Sort lexicographically within the component
                rawComponents.add(component);
            }
        }

        // Step 3: Add components to the result in order of appearance, but as concatenated strings
        for (List<String> component : rawComponents) {
            result.add(String.join("", component));
        }

        return result;
    }

    private static void dfs(String node, Map<String, List<String>> graph, Set<String> visited, Stack<String> finishStack) {
        visited.add(node);
        for (String neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, graph, visited, finishStack);
            }
        }
        finishStack.push(node);
    }

    private static void dfsCollect(String node, Map<String, List<String>> graph, Set<String> visited, List<String> component) {
        visited.add(node);
        component.add(node);
        for (String neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                dfsCollect(neighbor, graph, visited, component);
            }
        }
    }
}
