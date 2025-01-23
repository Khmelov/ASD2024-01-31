package by.it.group310971.katsuba.lesson13;

import java.util.*;

public class GraphB {
    private Map<Integer, List<Integer>> adjList;

    public GraphB() {
        adjList = new HashMap<>();
    }

    // Метод для добавления ребра в граф
    public void addEdge(int from, int to) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.get(from).add(to);
    }

    // Метод для проверки наличия циклов в графе
    public boolean hasCycle() {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recStack = new HashSet<>();

        for (Integer node : adjList.keySet()) {
            if (hasCycleUtil(node, visited, recStack)) {
                return true;
            }
        }
        return false;
    }

    // Вспомогательный метод для проверки циклов
    private boolean hasCycleUtil(int node, Set<Integer> visited, Set<Integer> recStack) {
        if (recStack.contains(node)) {
            return true; // Цикл найден
        }
        if (visited.contains(node)) {
            return false; // Узел уже обработан
        }

        visited.add(node);
        recStack.add(node);

        for (Integer neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
            if (hasCycleUtil(neighbor, visited, recStack)) {
                return true;
            }
        }

        recStack.remove(node);
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GraphB graph = new GraphB();

        // Считывание строки орграфа
        String input = scanner.nextLine();
        String[] edges = input.split(", ");

        // Добавление ребер в граф
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            int from = Integer.parseInt(nodes[0]);
            int to = Integer.parseInt(nodes[1]);
            graph.addEdge(from, to);
        }

        // Проверка наличия циклов и вывод результата
        if (graph.hasCycle()) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }

        scanner.close();
    }
}