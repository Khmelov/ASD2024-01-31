package by.it.group310971.a_kokhan.lesson13;

import java.util.*;

public class GraphB {
    private Map<Integer, List<Integer>> graph; 
    private Set<Integer> visited; 
    private Set<Integer> recursionStack; 

    public GraphB() {
        graph = new HashMap<>();
        visited = new HashSet<>();
        recursionStack = new HashSet<>();
    }

    
    public void addEdge(int from, int to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    
    public boolean hasCycle() {
        for (Integer node : graph.keySet()) {
            if (!visited.contains(node)) {
                if (dfs(node)) {
                    return true; 
                }
            }
        }
        return false; 
    }

    
    private boolean dfs(int node) {
        visited.add(node);
        recursionStack.add(node);

        if (graph.containsKey(node)) {
            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    if (dfs(neighbor)) {
                        return true; 
                    }
                } else if (recursionStack.contains(neighbor)) {
                    return true; 
                }
            }
        }

        recursionStack.remove(node); 
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        String input = scanner.nextLine();

        
        GraphB graph = new GraphB();

        
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            int from = Integer.parseInt(nodes[0]);
            int to = Integer.parseInt(nodes[1]);
            graph.addEdge(from, to);
        }

        
        boolean hasCycle = graph.hasCycle();

        
        System.out.println(hasCycle ? "yes" : "no");
    }
}
