package by.it.group310971.stankevich.lesson13;

import java.util.*;

public class GraphA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter graph structure:");
        String input = scanner.nextLine();

        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();

        // Parse the input
        String[] edges = input.split(",\\s*");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            String from = parts[0].trim();
            String to = parts[1].trim();

            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            inDegree.putIfAbsent(from, 0);
        }

        // Perform topological sort
        List<String> result = topologicalSort(graph, inDegree);

        // Output the result
        if (result.isEmpty()) {
            System.out.println("The graph contains a cycle and cannot be topologically sorted.");
        } else {
            result.forEach(node -> System.out.print(node + " "));
        }
    }

    private static List<String> topologicalSort(Map<String, List<String>> graph, Map<String, Integer> inDegree) {
        PriorityQueue<String> zeroInDegree = new PriorityQueue<>(); // For lexicographical order
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegree.add(entry.getKey());
            }
        }

        List<String> sortedOrder = new ArrayList<>();
        while (!zeroInDegree.isEmpty()) {
            String node = zeroInDegree.poll();
            sortedOrder.add(node);

            if (graph.containsKey(node)) {
                for (String neighbor : graph.get(node)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        zeroInDegree.add(neighbor);
                    }
                }
            }
        }

        // Check for a cycle
        if (sortedOrder.size() != inDegree.size()) {
            return Collections.emptyList(); // Cycle detected
        }

        return sortedOrder;
    }
}
