package by.it.group310971.ivankov.lesson13;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GraphB extends GraphA {

    // Constructor calling parent class constructor
    public GraphB(Scanner scanner) {
        super(scanner);
    }

    // Constructor with custom separator calling parent class constructor
    public GraphB(Scanner scanner, String separator) {
        super(scanner, separator);
    }

    // Checks if the graph contains a cycle
    public boolean hasCycle() {
        for (String node : elements.keySet()) {
            if (cycleTraverse(node, new HashSet<>())) {
                return true;
            }
        }
        return false;
    }

    // Helper method to traverse the graph and check for cycles
    protected boolean cycleTraverse(String node, Set<String> visited) {
        if (visited.contains(node)) {
            return true; // Cycle detected
        }

        visited.add(node);
        List<String> neighbors = elements.get(node);

        if (neighbors != null) {
            for (String next : neighbors) {
                if (cycleTraverse(next, visited)) {
                    return true; // Cycle detected in DFS
                }
            }
        }

        visited.remove(node); // Remove node after traversal
        return false;
    }

    // Main method to run the program and check if the graph has a cycle
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GraphB graph = new GraphB(scanner);
        System.out.print(graph.hasCycle() ? "yes" : "no");
    }
}
