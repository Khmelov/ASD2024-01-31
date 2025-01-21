package by.it.group310971.a_kokhan.lesson13;

import java.util.*;

public class GraphB {
    private Map<Integer, List<Integer>> graph; // Граф в виде списка смежности
    private Set<Integer> visited; // Посещенные вершины
    private Set<Integer> recursionStack; // Вершины в текущем пути обхода

    public GraphB() {
        graph = new HashMap<>();
        visited = new HashSet<>();
        recursionStack = new HashSet<>();
    }

    // Добавление ребра в граф
    public void addEdge(int from, int to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    // Проверка на наличие циклов
    public boolean hasCycle() {
        for (Integer node : graph.keySet()) {
            if (!visited.contains(node)) {
                if (dfs(node)) {
                    return true; // Найден цикл
                }
            }
        }
        return false; // Циклов нет
    }

    // Поиск в глубину (DFS) с проверкой на циклы
    private boolean dfs(int node) {
        visited.add(node);
        recursionStack.add(node);

        if (graph.containsKey(node)) {
            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    if (dfs(neighbor)) {
                        return true; // Найден цикл
                    }
                } else if (recursionStack.contains(neighbor)) {
                    return true; // Обратное ребро -> цикл
                }
            }
        }

        recursionStack.remove(node); // Убираем вершину из текущего пути
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Считываем строку с описанием графа
        String input = scanner.nextLine();

        // Создаем объект графа
        GraphB graph = new GraphB();

        // Разбираем строку на ребра
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            int from = Integer.parseInt(nodes[0]);
            int to = Integer.parseInt(nodes[1]);
            graph.addEdge(from, to);
        }

        // Проверяем наличие циклов
        boolean hasCycle = graph.hasCycle();

        // Выводим результат
        System.out.println(hasCycle ? "yes" : "no");
    }
}
