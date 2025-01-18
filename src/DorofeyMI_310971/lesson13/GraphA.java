package DorofeyMI_310971.lesson13;

import java.util.*;

public class GraphA {
    private Map<String, List<String>> adjList = new HashMap<>();
    private Set<String> vertices = new TreeSet<>();

    public void addEdge(String from, String to) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        vertices.add(from);
        vertices.add(to);
    }

    public List<String> topologicalSort() {
        Map<String, Integer> inDegree = new HashMap<>();
        for (String vertex : vertices) {
            inDegree.put(vertex, 0);
        }
        for (String from : adjList.keySet()) {
            for (String to : adjList.get(from)) {
                inDegree.put(to, inDegree.get(to) + 1);
            }
        }

        PriorityQueue<String> queue = new PriorityQueue<>();
        for (String vertex : inDegree.keySet()) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        List<String> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            sortedList.add(vertex);
            if (adjList.containsKey(vertex)) {
                for (String neighbor : adjList.get(vertex)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        return sortedList;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите структуру графа:");
        String input = scanner.nextLine();
        GraphA graph = new GraphA();

        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            String from = nodes[0];
            String to = nodes[1];
            graph.addEdge(from, to);
        }

        List<String> sortedList = graph.topologicalSort();
        for (String vertex : sortedList) {
            System.out.print(vertex + " ");
        }
    }
}
