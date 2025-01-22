package by.it.group310971.ivankov.lesson13;

import java.util.*;

public class GraphA {
    protected Map<String, List<String>> elements = new HashMap<>();

    // Constructor that takes a Scanner to read the graph's input
    public GraphA(Scanner scanner) {
        this(scanner, " -> ");
    }

    // Constructor that takes a Scanner and custom separator
    public GraphA(Scanner scanner, String separator) {
        String input = scanner.nextLine();
        for (String connection : input.split(", ")) {
            String[] nodes = connection.split(separator);
            elements.computeIfAbsent(nodes[0], k -> new ArrayList<>()).add(nodes[1]);
        }
        scanner.close();
    }

    // Sorts the adjacency list for each node in descending order
    public GraphA sort() {
        elements.values().forEach(list -> list.sort(Comparator.reverseOrder()));
        return this;
    }

    // Converts the graph to a string using topological sorting (DFS)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        for (String node : elements.keySet()) {
            if (!visited.contains(node)) {
                traverse(node, visited, stack);
            }
        }

        sb.append(stack.pop());
        while (!stack.isEmpty()) {
            sb.append(' ').append(stack.pop());
        }
        return sb.toString();
    }

    // Helper method to traverse the graph using DFS and push nodes to stack
    private void traverse(String node, Set<String> visited, Stack<String> stack) {
        visited.add(node);
        List<String> neighbors = elements.get(node);

        if (neighbors != null) {
            for (String next : neighbors) {
                if (!visited.contains(next)) {
                    traverse(next, visited, stack);
                }
            }
        }
        stack.push(node);
    }

    // Main method to run the GraphA program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(new GraphA(scanner).sort());
    }
}
