package by.it._310971_hrakovich.lesson13;

import java.util.*;

public class GraphC {
    private final Map<String, List<String>> graph = new HashMap<>();
    private final Map<String, List<String>> reverseGraph = new HashMap<>();
    private final Set<String> visited = new HashSet<>();
    private final List<List<String>> components = new ArrayList<>();
    private final Deque<String> stack = new ArrayDeque<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        GraphC graphC = new GraphC();
        graphC.buildGraph(input);
        graphC.findStronglyConnectedComponents();
        graphC.printComponents();
    }

    // Метод для построения графа из входной строки
    private void buildGraph(String input) {
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] vertices = edge.split("->");
            graph.computeIfAbsent(vertices[0], k -> new ArrayList<>()).add(vertices[1]);
            reverseGraph.computeIfAbsent(vertices[1], k -> new ArrayList<>()).add(vertices[0]);
            graph.computeIfAbsent(vertices[1], k -> new ArrayList<>()); // Добавляем вершину, если она отсутствует
        }
    }

    // Метод для поиска компонент сильной связности
    private void findStronglyConnectedComponents() {
        // Первый проход - заполняем стек
        for (String vertex : graph.keySet()) {
            if (!visited.contains(vertex)) {
                dfsFillStack(vertex);
            }
        }

        // Второй проход - ищем сильно связанные компоненты
        visited.clear();
        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
                List<String> component = new ArrayList<>();
                dfsCollectComponent(vertex, component);
                Collections.sort(component); // Сортируем компоненты по алфавиту
                components.add(component);
            }
        }
    }

    // Метод обхода в глубину и заполняем стек
    private void dfsFillStack(String vertex) {
        visited.add(vertex);
        for (String neighbor : graph.getOrDefault(vertex, Collections.emptyList())) { // Изменено для обработки отсутствующих соседей
            if (!visited.contains(neighbor)) {
                dfsFillStack(neighbor);
            }
        }
        stack.push(vertex);
    }

    // Метод для сбора компонента
    private void dfsCollectComponent(String vertex, List<String> component) {
        visited.add(vertex);
        component.add(vertex);
        for (String neighbor : reverseGraph.getOrDefault(vertex, Collections.emptyList())) { // Изменено для обработки отсутствующих соседей
            if (!visited.contains(neighbor)) {
                dfsCollectComponent(neighbor, component);
            }
        }
    }

    // Метод для вывода компонент
    private void printComponents() {
        for (List<String> component : components) {
            System.out.println(String.join("", component)); // используем пробел для разделения
        }
    }
}
