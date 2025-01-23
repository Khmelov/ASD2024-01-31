package by.it.group310971.katsuba.lesson13;


import java.util.*;

public class GraphA {
    private final Map<String, List<String>> adjList = new HashMap<>();
    private final Set<String> visited = new HashSet<>();
    private final List<String> result = new ArrayList<>(); // Изменено на List

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите структуру орграфа (например, 0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1): ");
        String input = scanner.nextLine().trim();

        GraphA graph = new GraphA();
        graph.buildGraph(input);
        graph.topologicalSort();

        System.out.println("Топологическая сортировка: " + String.join(" ", graph.result)); // Сначала мы присоединяем все элементы
    }

    private void buildGraph(String input) {
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            String vertex = parts[0].trim();
            String neighbor = parts[1].trim();

            adjList.putIfAbsent(vertex, new ArrayList<>());
            adjList.putIfAbsent(neighbor, new ArrayList<>());
            adjList.get(vertex).add(neighbor);
        }

        // Сортируем соседей для лексикографического порядка
        for (List<String> neighbors : adjList.values()) {
            Collections.sort(neighbors);
        }
    }

    private void topologicalSort() {
        PriorityQueue<String> minHeap = new PriorityQueue<>(); // Используем PriorityQueue для сортировки
        for (String vertex : adjList.keySet()) {
            if (!visited.contains(vertex)) {
                dfs(vertex, minHeap);
            }
        }
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll()); // Извлекаем элементы в порядке возрастания
        }
    }

    private void dfs(String vertex, PriorityQueue<String> minHeap) {
        if (visited.contains(vertex)) {
            return; // Если уже посещена, выходим
        }

        visited.add(vertex); // Добавляем в окончательно посещенные
        for (String neighbor : adjList.getOrDefault(vertex, Collections.emptyList())) {
            dfs(neighbor, minHeap);
        }
        minHeap.add(vertex); // Добавляем после обработки всех соседей
    }
}