package by.it.a_khmelev.lesson14;

import java.util.*;

public class PointsA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Reading input data
        double distanceRequired = scanner.nextDouble();  // Maximum distance
        int pointCount = scanner.nextInt();  // Number of points
        DSU dsu = new DSU(pointCount);
        int[][] points = new int[pointCount][3];

        // Reading coordinates of points
        for (int i = 0; i < pointCount; i++) {
            points[i][0] = scanner.nextInt();
            points[i][1] = scanner.nextInt();
            points[i][2] = scanner.nextInt();
        }

        // Union points into clusters
        for (int i = 0; i < pointCount; i++) {
            for (int j = i + 1; j < pointCount; j++) {
                if (calculateDistance(points[i], points[j]) < distanceRequired) {
                    dsu.union(i, j);
                }
            }
        }

        // Counting cluster sizes
        Map<Integer, Integer> clusterSizes = new HashMap<>();
        for (int i = 0; i < pointCount; i++) {
            int root = dsu.find(i);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        // Sorting and printing cluster sizes
        List<Integer> sizes = new ArrayList<>(clusterSizes.values());
        sizes.sort(Collections.reverseOrder());  // Sort in descending order
        sizes.forEach(size -> System.out.print(size + " "));
    }

    // Method to calculate Euclidean distance between two points
    private static double calculateDistance(int[] point1, int[] point2) {
        return Math.sqrt(
                Math.pow(point1[0] - point2[0], 2) +
                        Math.pow(point1[1] - point2[1], 2) +
                        Math.pow(point1[2] - point2[2], 2)
        );
    }

    // Disjoint Set Union (DSU) implementation
    static class DSU {
        private final int[] parent;
        private final int[] rank;

        public DSU(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
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
                // Union by rank
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
}
