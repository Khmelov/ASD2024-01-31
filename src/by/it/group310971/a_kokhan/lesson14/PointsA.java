package by.it.group310971.a_kokhan.lesson14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class PointsA {

    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            var D = scanner.nextDouble();
            var N = scanner.nextInt();

            var points = new int[N][3];
            for (var i = 0; i < N; i++) {
                points[i][0] = scanner.nextInt();
                points[i][1] = scanner.nextInt();
                points[i][2] = scanner.nextInt();
            }

            var dsu = new DSU(N);

            for (var i = 0; i < N; i++) {
                for (var j = i + 1; j < N; j++) {
                    if (distance(points[i], points[j]) < D) {
                        dsu.union(i, j);
                    }
                }
            }

            var clusterSizes = new HashMap<Integer, Integer>();
            for (var i = 0; i < N; i++) {
                var root = dsu.find(i);
                clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
            }

            var sortedSizes = new ArrayList<Integer>(clusterSizes.values());
            sortedSizes.sort(Collections.reverseOrder());

            for (var size : sortedSizes) {
                System.out.print(size + " ");
            }
        }
    }
    
    static class DSU {
        private final int[] parent;
        private final int[] size;

        public DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            var rootX = find(x);
            var rootY = find(y);

            if (rootX != rootY) {

                if (size[rootX] < size[rootY]) {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                } else {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                }
            }
        }

        public int getSize(int x) {
            return size[find(x)];
        }
    }

    private static double distance(int[] point1, int[] point2) {
        var dx = point1[0] - point2[0];
        var dy = point1[1] - point2[1];
        var dz = point1[2] - point2[2];
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}