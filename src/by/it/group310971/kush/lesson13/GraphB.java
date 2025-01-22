package by.it.group310971.kush.lesson13;

import java.util.*;

public class GraphB {

    private Map<String, ArrayList<String>> elements = new HashMap<>();
    public GraphB(Scanner input) {
        for (var connections : input.nextLine().split(", ")) {
            var nodes = connections.split(" -> ");
            if (!elements.containsKey(nodes[0]))
                elements.put(nodes[0], new ArrayList<>());
            elements.get(nodes[0]).add(nodes[1]);
        }
        input.close();
    }

    public boolean isCyclic() {
        for (var i : elements.keySet())
            if (dfs(i, new Stack<String>()))
                return true;
        return false;
    }

    public boolean dfs(String nodeGraphB, Stack<String> visited) {
        visited.add(nodeGraphB);
        if (elements.get(nodeGraphB) != null)
            for (var next : elements.get(nodeGraphB))
                if (visited.contains(next) || dfs(next, visited))
                    return true;
        visited.remove(nodeGraphB);
        return false;
    }

    public static void main(String[] args) {
        System.out.print(new GraphB(new Scanner(System.in)).isCyclic() ? "yes" : "no");
    }
}