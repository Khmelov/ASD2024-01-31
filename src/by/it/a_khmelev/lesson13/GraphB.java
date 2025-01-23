package by.it.a_khmelev.lesson13;

import java.util.*;

public class GraphB  {

    static class Graph {
        private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

        // ����� ���������� ����� � ����
        public void addEdge(int from, int to) {
            adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            adjacencyList.putIfAbsent(to, new ArrayList<>()); // ��������, ��� ������� "to" ����������
        }

        // �������� ������� ����� � �������������� ��������� DFS
        public boolean hasCycle() {
            Set<Integer> visited = new HashSet<>();
            Set<Integer> recursionStack = new HashSet<>();

            for (int node : adjacencyList.keySet()) {
                if (hasCycleUtil(node, visited, recursionStack)) {
                    return true; // ������ ����
                }
            }
            return false; // ������ ���
        }

        // ��������������� ����� DFS ��� �������� �����
        private boolean hasCycleUtil(int current, Set<Integer> visited, Set<Integer> recursionStack) {
            if (recursionStack.contains(current)) {
                return true; // ������ ����
            }
            if (visited.contains(current)) {
                return false; // ��� ���������� ��� �������
            }

            // ��������� ������� ������� � ���������
            visited.add(current);
            recursionStack.add(current);

            // ������������ ������� ������� �������
            for (int neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (hasCycleUtil(neighbor, visited, recursionStack)) {
                    return true;
                }
            }

            // ������� ������� ������� �� ����� ��������
            recursionStack.remove(current);
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("������� ��������� �����: ");
        String input = scanner.nextLine();

        Graph graph = new Graph();

        // ������ ������ � ��������� �����
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            int from = Integer.parseInt(parts[0]);
            String[] toNodes = parts[1].split(", ");
            for (String to : toNodes) {
                graph.addEdge(from, Integer.parseInt(to));
            }
        }

        // �������� ������� ����� � ����� ����������
        boolean hasCycle = graph.hasCycle();
        System.out.println(hasCycle ? "yes" : "no");
    }
}