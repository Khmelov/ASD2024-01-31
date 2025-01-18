package DorofeyMI_310971.lesson13;

import java.util.*;

public class GraphC {
    private Map<String, List<String>> adjList = new HashMap<>();
    private Set<String> vertices = new HashSet<>();

    public void addEdge(String from, String to) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        vertices.add(from);
        vertices.add(to);
    }

    public List<List<String>> findSCCs() {
        Map<String, Boolean> visited = new HashMap<>();
        Stack<String> stack = new Stack<>();

        for (String vertex : vertices) {
            if (!visited.containsKey(vertex)) {
                fillOrder(vertex, visited, stack);
            }
        }

        Map<String, List<String>> transposedGraph = getTranspose();

        visited.clear();
        List<List<String>> sccs = new ArrayList<>();

        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            if (!visited.containsKey(vertex)) {
                List<String> scc = new ArrayList<>();
                dfs(vertex, visited, transposedGraph, scc);
                Collections.sort(scc);
                sccs.add(scc);
            }
        }

        return sccs;
    }

    private void fillOrder(String vertex, Map<String, Boolean> visited, Stack<String> stack) {
        visited.put(vertex, true);
        List<String> neighbors = adjList.get(vertex);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (!visited.containsKey(neighbor)) {
                    fillOrder(neighbor, visited, stack);
                }
            }
        }
        stack.push(vertex);
    }

    private Map<String, List<String>> getTranspose() {
        Map<String, List<String>> transposedGraph = new HashMap<>();
        for (String vertex : adjList.keySet()) {
            for (String neighbor : adjList.get(vertex)) {
                transposedGraph.computeIfAbsent(neighbor, k -> new ArrayList<>()).add(vertex);
            }
        }
        return transposedGraph;
    }

    private void dfs(String vertex, Map<String, Boolean> visited, Map<String, List<String>> graph, List<String> scc) {
        visited.put(vertex, true);
        scc.add(vertex);
        List<String> neighbors = graph.get(vertex);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (!visited.containsKey(neighbor)) {
                    dfs(neighbor, visited, graph, scc);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите структуру графа:");
        String input = scanner.nextLine();
        GraphC graph = new GraphC();

        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split("->");
            String from = nodes[0];
            String to = nodes[1];
            graph.addEdge(from, to);
        }

        List<List<String>> sccs = graph.findSCCs();
        for (List<String> scc : sccs) {
            for (String vertex : scc) {
                System.out.print(vertex);
            }
            System.out.println();
        }
    }
}
