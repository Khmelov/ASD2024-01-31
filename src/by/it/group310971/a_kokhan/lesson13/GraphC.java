package by.it.group310971.a_kokhan.lesson13;

import java.util.*;

public class GraphC {
    private Map<Character, List<Character>> graph; // Граф в виде списка смежности
    private Map<Character, List<Character>> reversedGraph; // Транспонированный граф
    private Set<Character> visited; // Посещенные вершины
    private List<Character> order; // Порядок вершин по времени выхода
    private List<List<Character>> sccs; // Компоненты сильной связности

    public GraphC() {
        graph = new HashMap<>();
        reversedGraph = new HashMap<>();
        visited = new HashSet<>();
        order = new ArrayList<>();
        sccs = new ArrayList<>();
    }

    // Добавление ребра в граф и транспонированный граф
    public void addEdge(char from, char to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        reversedGraph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
    }

    // Первый этап: топологическая сортировка (по времени выхода)
    private void dfs1(char node) {
        visited.add(node);
        if (graph.containsKey(node)) {
            for (char neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    dfs1(neighbor);
                }
            }
        }
        order.add(node); // Добавляем вершину в порядке выхода
    }

    // Второй этап: обход транспонированного графа
    private void dfs2(char node, List<Character> component) {
        visited.add(node);
        component.add(node);
        if (reversedGraph.containsKey(node)) {
            for (char neighbor : reversedGraph.get(node)) {
                if (!visited.contains(neighbor)) {
                    dfs2(neighbor, component);
                }
            }
        }
    }

    // Нахождение компонент сильной связности
    public void findSCCs() {
        // Первый этап: топологическая сортировка
        for (char node : graph.keySet()) {
            if (!visited.contains(node)) {
                dfs1(node);
            }
        }

        // Второй этап: обход транспонированного графа
        visited.clear();
        Collections.reverse(order); // Обратный порядок для обхода
        for (char node : order) {
            if (!visited.contains(node)) {
                List<Character> component = new ArrayList<>();
                dfs2(node, component);
                Collections.sort(component); // Лексикографический порядок
                sccs.add(component);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Считываем строку с описанием графа
        String input = scanner.nextLine();

        // Создаем объект графа
        GraphC graph = new GraphC();

        // Разбираем строку на ребра
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split("->");
            char from = nodes[0].charAt(0);
            char to = nodes[1].charAt(0);
            graph.addEdge(from, to);
        }

        // Находим компоненты сильной связности
        graph.findSCCs();

        // Выводим результат
        for (List<Character> component : graph.sccs) {
            for (char node : component) {
                System.out.print(node);
            }
            System.out.println();
        }
    }
}
