package by.it.group310971.kush.lesson13;

import java.util.*;

public class GraphA {

    static void topologicalSortUnit(String nodeGraphA, Map<String, ArrayList<String>> graph, Set<String> visited, Stack<String> stack) {
        visited.add(nodeGraphA);

        if (graph.get(nodeGraphA)!=null){
            for (String nextNode: graph.get(nodeGraphA)
            ) {
                if (!visited.contains(nextNode)){
                    topologicalSortUnit(nextNode, graph, visited, stack);
                }
            }
        }
        stack.push(nodeGraphA);
    }

    static void topologicalSort(Map<String, ArrayList<String>> graph) {
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        for (ArrayList<String> array : graph.values()) {
            array.sort(Comparator.reverseOrder());
        }

        for (String nodeGraphA : graph.keySet()
        ) {
            if (!visited.contains(nodeGraphA)){
                topologicalSortUnit(nodeGraphA, graph, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    private static void getGraph(Map<String, ArrayList<String>> graph) {
        Scanner in = new Scanner(System.in);

        boolean isEnd = false;
        while (!isEnd) {
            String vertexOut = in.next();
            if (!graph.containsKey(vertexOut)) {
                graph.put(vertexOut, new ArrayList<>());
            }
            String edge = in.next();
            String vertexIn = in.next();
            if (vertexIn.charAt(vertexIn.length() - 1) == ',') {
                vertexIn = vertexIn.substring(0, vertexIn.length() - 1);
            } else {
                isEnd = true;
            }
            graph.get(vertexOut).add(vertexIn);
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>();
        getGraph(graph);
        topologicalSort(graph);
    }
}

