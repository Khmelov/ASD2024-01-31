package by.it.a_khmelev.lesson14;

import java.util.*;

public class PointsA {

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
                parent[x] = find(parent[x]); // Сжатие пути
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                // Эвристика по рангу (или размеру)
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Читаем расстояние и количество точек
        System.out.println("Введите расстояние D и количество точек N:");
        double D = scanner.nextDouble();
        int N = scanner.nextInt();

        // Читаем точки
        double[][] points = new double[N][3];
        for (int i = 0; i < N; i++) {
            points[i][0] = scanner.nextDouble();
            points[i][1] = scanner.nextDouble();
            points[i][2] = scanner.nextDouble();
        }

        // Создаем DSU
        DSU dsu = new DSU(N);

        // Проверяем расстояния между точками
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (distance(points[i], points[j]) < D) {
                    dsu.union(i, j);
                }
            }
        }

        // Считаем размеры кластеров
        Map<Integer, Integer> clusterSizes = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int root = dsu.find(i);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        // Сортируем и выводим размеры кластеров
        List<Integer> sortedSizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sortedSizes);
        for (int size : sortedSizes) {
            System.out.print(size + " ");
        }
    }

    // Метод для вычисления расстояния между двумя точками
    private static double distance(double[] point1, double[] point2) {
        return Math.sqrt(Math.pow(point1[0] - point2[0], 2) +
                Math.pow(point1[1] - point2[1], 2) +
                Math.pow(point1[2] - point2[2], 2));
    }
}
