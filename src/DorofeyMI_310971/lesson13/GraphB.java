package DorofeyMI_310971.lesson13;

import java.util.*;

public class GraphB {
    private Map<String, List<String>> adjList = new HashMap<>();
    private Set<String> vertices = new HashSet<>();

    public void addEdge(String from, String to) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        vertices.add(from);
        vertices.add(to);
    }

    public boolean hasCycle() {
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();

        for (String vertex : vertices) {
            if (hasCycleUtil(vertex, visited, recStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCycleUtil(String vertex, Set<String> visited, Set<String> recStack) {
        if (recStack.contains(vertex)) {
            return true;
        }
        if (visited.contains(vertex)) {
            return false;
        }

        visited.add(vertex);
        recStack.add(vertex);

        List<String> neighbors = adjList.get(vertex);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (hasCycleUtil(neighbor, visited, recStack)) {
                    return true;
                }
            }
        }

        recStack.remove(vertex);
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите структуру графа:");
        String input = scanner.nextLine();
        GraphB graph = new GraphB();

        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            String from = nodes[0];
            String to = nodes[1];
            graph.addEdge(from, to);
        }

        if (graph.hasCycle()) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }
}
