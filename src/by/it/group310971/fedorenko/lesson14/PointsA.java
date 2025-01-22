package by.it.group310971.fedorenko.lesson14;
import java.util.*;

public class PointsA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Чтение входных данных
        double distanceRequired = scanner.nextDouble(); // Максимальное расстояние
        int pointCount = scanner.nextInt(); // Количество точек

        DSU dsu = new DSU(pointCount);
        int[][] points = new int[pointCount][3];

        // Чтение координат точек
        for (int i = 0; i < pointCount; i++) {
            points[i][0] = scanner.nextInt();
            points[i][1] = scanner.nextInt();
            points[i][2] = scanner.nextInt();
        }

        // Объединение точек в кластеры
        for (int i = 0; i < pointCount; i++) {
            for (int j = i + 1; j < pointCount; j++) {
                if (calculateDistance(points[i], points[j]) < distanceRequired) {
                    dsu.union(i, j);
                }
            }
        }

        // Подсчет размеров кластеров
        Map<Integer, Integer> clusterSizes = new HashMap<>();
        for (int i = 0; i < pointCount; i++) {
            int root = dsu.find(i);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        // Получение и сортировка размеров кластеров
        List<Integer> sizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sizes, Collections.reverseOrder()); // Сортировка по убыванию

        // Вывод размеров кластеров
        for (int size : sizes) {
            System.out.print(size + " ");
        }
    }

    // Метод для вычисления евклидова расстояния между двумя точками
    private static double calculateDistance(int[] point1, int[] point2) {
        return Math.sqrt(
                Math.pow(point1[0] - point2[0], 2) +
                        Math.pow(point1[1] - point2[1], 2) +
                        Math.pow(point1[2] - point2[2], 2)
        );
    }

    // Реализация DSU (Disjoint Set Union)
    static class DSU {
        private int[] parent;
        private int[] rank;

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
                parent[x] = find(parent[x]); // Сжатие пути
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
}