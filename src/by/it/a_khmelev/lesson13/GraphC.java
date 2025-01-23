package by.it.a_khmelev.lesson13;

import java.util.*;

public class GraphC {

    static class Graph {
        private final Map<String, List<String>> adjacencyList = new TreeMap<>();

        // Метод добавления ребра в граф
        public void addEdge(String from, String to) {
            adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            adjacencyList.putIfAbsent(to, new ArrayList<>()); // Убедимся, что вершина "to" существует
        }

        // Метод для нахождения компонентов сильной связности (КСС)
        public List<List<String>> findStronglyConnectedComponents() {
            Map<String, Integer> ids = new HashMap<>();
            Map<String, Integer> low = new HashMap<>();
            Stack<String> stack = new Stack<>();
            Set<String> onStack = new HashSet<>();
            List<List<String>> scc = new ArrayList<>();
            int id = 0;

            for (String node : adjacencyList.keySet()) {
                if (!ids.containsKey(node)) {
                    dfs(node, ids, low, stack, onStack, scc, id);
                }
            }

            // Сортируем каждую компоненту и компоненты по лексикографическому порядку
            for (List<String> component : scc) {
                Collections.sort(component);
            }
            scc.sort(Comparator.comparing(c -> c.get(0)));
            return scc;
        }

        // Вспомогательный метод DFS для алгоритма Тарьяна
        private void dfs(String current, Map<String, Integer> ids, Map<String, Integer> low,
                         Stack<String> stack, Set<String> onStack, List<List<String>> scc, int id) {
            ids.put(current, id);
            low.put(current, id);
            id++;
            stack.push(current);
            onStack.add(current);

            for (String neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!ids.containsKey(neighbor)) {
                    dfs(neighbor, ids, low, stack, onStack, scc, id);
                    low.put(current, Math.min(low.get(current), low.get(neighbor)));
                } else if (onStack.contains(neighbor)) {
                    low.put(current, Math.min(low.get(current), ids.get(neighbor)));
                }
            }

            if (ids.get(current).equals(low.get(current))) {
                List<String> component = new ArrayList<>();
                String node;
                do {
                    node = stack.pop();
                    onStack.remove(node);
                    component.add(node);
                } while (!node.equals(current));
                scc.add(component);
            }
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
            String[] parts = edge.split("->");
            String from = parts[0];
            String to = parts[1];
            graph.addEdge(from, to);
        }

        // Нахождение КСС и вывод результата
        List<List<String>> scc = graph.findStronglyConnectedComponents();
        for (List<String> component : scc) {
            System.out.println(String.join("", component));
        }
    }
}