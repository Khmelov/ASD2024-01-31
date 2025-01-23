package by.it.a_khmelev.lesson13;

import java.util.*;

public class GraphA {

    static class Graph {
        private final Map<Integer, List<Integer>> adjacencyList = new TreeMap<>();

        // ����� ���������� ����� � ����
        public void addEdge(int from, int to) {
            adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            adjacencyList.putIfAbsent(to, new ArrayList<>()); // ��������, ��� ������� to ����������
        }

        // �������������� ����������
        public List<Integer> topologicalSort() {
            Map<Integer, Integer> inDegree = new TreeMap<>(); // ������ ������� ����� ��� ������
            for (int node : adjacencyList.keySet()) {
                inDegree.putIfAbsent(node, 0);
                for (int neighbor : adjacencyList.get(node)) {
                    inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
                }
            }

            Queue<Integer> zeroInDegree = new PriorityQueue<>(); // ������� ��� ������ � ������� �������� �����
            for (int node : inDegree.keySet()) {
                if (inDegree.get(node) == 0) {
                    zeroInDegree.add(node);
                }
            }

            List<Integer> result = new ArrayList<>();
            while (!zeroInDegree.isEmpty()) {
                int current = zeroInDegree.poll();
                result.add(current);
                for (int neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        zeroInDegree.add(neighbor);
                    }
                }
            }

            // ���� ��������� �� �������� ���� ������, ������ � ����� ���� ����
            if (result.size() != adjacencyList.size()) {
                throw new IllegalStateException("Graph has a cycle and cannot be topologically sorted");
            }

            return result;
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

        // ���������� �������������� ���������� � ����� ����������
        try {
            List<Integer> sorted = graph.topologicalSort();
            System.out.println("�������������� ����������: " + String.join(" ", sorted.stream().map(String::valueOf).toArray(String[]::new)));
        } catch (IllegalStateException e) {
            System.out.println("������: " + e.getMessage());
        }
    }
}