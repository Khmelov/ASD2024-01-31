package by.it.group310971.stankevich.lesson13;

import java.util.*;

public class GraphB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter graph structure:");
        String input = scanner.nextLine();

        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Parse the input
        String[] edges = input.split(",\s*");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            int from = Integer.parseInt(parts[0].trim());
            int to = Integer.parseInt(parts[1].trim());

            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        }

        // Check for cycles
        boolean hasCycle = detectCycle(graph);

        // Output the result
        System.out.println(hasCycle ? "yes" : "no");
    }

    private static boolean detectCycle(Map<Integer, List<Integer>> graph) {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recursionStack = new HashSet<>();

        for (Integer node : graph.keySet()) {
            if (!visited.contains(node)) {
                if (dfs(node, graph, visited, recursionStack)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean dfs(Integer node, Map<Integer, List<Integer>> graph, Set<Integer> visited, Set<Integer> recursionStack) {
        visited.add(node);
        recursionStack.add(node);

        if (graph.containsKey(node)) {
            for (Integer neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    if (dfs(neighbor, graph, visited, recursionStack)) {
                        return true;
                    }
                } else if (recursionStack.contains(neighbor)) {
                    return true;
                }
            }
        }

        recursionStack.remove(node);
        return false;
    }
}

