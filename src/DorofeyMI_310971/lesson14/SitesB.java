package DorofeyMI_310971.lesson14;

import java.util.*;

public class SitesB {
    static class DSU {
        private int[] parent;
        private int[] rank;

        public DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> siteIndex = new HashMap<>();
        List<String[]> pairs = new ArrayList<>();
        int index = 0;

        while (true) {
            String line = scanner.nextLine();
            if (line.equals("end")) break;
            String[] sites = line.split("\\+");
            pairs.add(sites);
            for (String site : sites) {
                if (!siteIndex.containsKey(site)) {
                    siteIndex.put(site, index++);
                }
            }
        }

        DSU dsu = new DSU(siteIndex.size());

        for (String[] pair : pairs) {
            int site1 = siteIndex.get(pair[0]);
            int site2 = siteIndex.get(pair[1]);
            dsu.union(site1, site2);
        }

        Map<Integer, Integer> clusterSizes = new HashMap<>();
        for (int i = 0; i < siteIndex.size(); i++) {
            int root = dsu.find(i);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        List<Integer> sizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sizes);

        for (int size : sizes) {
            System.out.print(size + " ");
        }
    }
}
