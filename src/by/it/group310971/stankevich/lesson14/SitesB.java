package by.it.group310971.stankevich.lesson14;

import java.util.*;

public class SitesB {

    static class DSU {
        private final Map<String, String> parent;
        private final Map<String, Integer> rank;

        // Конструктор DSU
        public DSU() {
            parent = new HashMap<>();
            rank = new HashMap<>();
        }

        public void add(String site) {
            if (!parent.containsKey(site)) {
                parent.put(site, site);
                rank.put(site, 1);
            }
        }

        public String find(String site) {
            if (!parent.get(site).equals(site)) {
                parent.put(site, find(parent.get(site))); // Path compression
            }
            return parent.get(site);
        }

        public void union(String site1, String site2) {
            String root1 = find(site1);
            String root2 = find(site2);

            if (!root1.equals(root2)) {
                if (rank.get(root1) < rank.get(root2)) {
                    parent.put(root1, root2);
                } else if (rank.get(root1) > rank.get(root2)) {
                    parent.put(root2, root1);
                } else {
                    parent.put(root2, root1);
                    rank.put(root1, rank.get(root1) + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DSU dsu = new DSU();

        System.out.println("Enter site connections (end to finish):");
        String input;
        while (!(input = scanner.nextLine()).equals("end")) {
            String[] sites = input.split("\\+");
            String site1 = sites[0].trim();
            String site2 = sites[1].trim();

            dsu.add(site1);
            dsu.add(site2);
            dsu.union(site1, site2);
        }

        // Count the sizes of connected components
        Map<String, Integer> clusterSizes = new HashMap<>();
        for (String site : dsu.parent.keySet()) {
            String root = dsu.find(site);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        // Sort sizes in DESCENDING order
        List<Integer> sortedClusterSizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sortedClusterSizes);
        Collections.reverse(sortedClusterSizes);

        // Print them in one line
        for (int size : sortedClusterSizes) {
            System.out.print(size + " ");
        }
        System.out.println(); // optional new line
    }
}
