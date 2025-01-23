package by.it.group310971.katsuba.lesson14;

import java.io.IOException;
import java.util.*;

public class PointsA {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Ввод расстояния D и числа точек N
        double D = scanner.nextDouble();
        int N = scanner.nextInt();
        scanner.nextLine(); // Переход на новую строку

        // Считывание точек в трехмерном пространстве
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            String[] coords = scanner.nextLine().split(" ");
            points[i] = new Point(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
        }

        // Создаем DSU для отслеживания кластеров
        DSU dsu = new DSU(N);

        // Сопоставляем точки и объединяем их в кластеры при необходимости
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double distance = points[i].distance(points[j]);
                if (distance < D) {
                    dsu.union(i, j);
                }
            }
        }

        // Подсчет размеров кластеров
        Map<Integer, Integer> clusterSizes = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int root = dsu.find(i);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        // Подготовка размеров кластеров для вывода
        List<Integer> sizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sizes, Collections.reverseOrder()); // Сортируем по убыванию

        // Выводим размеры кластеров в порядке убывания
        for (int size : sizes) {
            System.out.print(size + " ");
        }
    }

    static class Point {
        double x, y, z;

        Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        double distance(Point other) {
            return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
        }
    }

    static class DSU {
        private int[] parent;
        private int[] size;

        public DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int a) {
            if (parent[a] != a) {
                parent[a] = find(parent[a]);
            }
            return parent[a];
        }

        public void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA != rootB) {
                if (size[rootA] < size[rootB]) {
                    parent[rootA] = rootB;
                    size[rootB] += size[rootA];
                } else {
                    parent[rootB] = rootA;
                    size[rootA] += size[rootB];
                }
            }
        }
    }
}