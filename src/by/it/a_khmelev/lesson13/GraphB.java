package by.it.a_khmelev.lesson13;

import java.util.*;

public class GraphB  {

    static class Graph {
        private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

        // Метод добавления ребра в граф
        public void addEdge(int from, int to) {
            adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            adjacencyList.putIfAbsent(to, new ArrayList<>()); // Убедимся, что вершина "to" существует
        }

        // Проверка наличия цикла с использованием алгоритма DFS
        public boolean hasCycle() {
            Set<Integer> visited = new HashSet<>();
            Set<Integer> recursionStack = new HashSet<>();

            for (int node : adjacencyList.keySet()) {
                if (hasCycleUtil(node, visited, recursionStack)) {
                    return true; // Найден цикл
                }
            }
            return false; // Циклов нет
        }

        // Вспомогательный метод DFS для проверки цикла
        private boolean hasCycleUtil(int current, Set<Integer> visited, Set<Integer> recursionStack) {
            if (recursionStack.contains(current)) {
                return true; // Найден цикл
            }
            if (visited.contains(current)) {
                return false; // Уже обработали эту вершину
            }

            // Добавляем текущую вершину в множества
            visited.add(current);
            recursionStack.add(current);

            // Обрабатываем соседей текущей вершины
            for (int neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (hasCycleUtil(neighbor, visited, recursionStack)) {
                    return true;
                }
            }

            // Убираем текущую вершину из стека рекурсии
            recursionStack.remove(current);
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите структуру графа: ");
        String input = scanner.nextLine();

        Graph graph = new Graph();

        // Разбор строки с описанием графа
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            int from = Integer.parseInt(parts[0]);
            String[] toNodes = parts[1].split(", ");
            for (String to : toNodes) {
                graph.addEdge(from, Integer.parseInt(to));
            }
        }

        // Проверка наличия цикла и вывод результата
        boolean hasCycle = graph.hasCycle();
        System.out.println(hasCycle ? "yes" : "no");
    }
}