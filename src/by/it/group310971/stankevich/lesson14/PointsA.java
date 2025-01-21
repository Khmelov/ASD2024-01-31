package by.it.group310971.stankevich.lesson14;


import java.util.Scanner;

public class PointsA {

    private static int[] parent;
    private static int[] rank;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Считываем D и N
        double D = sc.nextDouble();
        int N = sc.nextInt();

        // Массив для хранения координат (x,y,z)
        double[][] points = new double[N][3];
        for (int i = 0; i < N; i++) {
            points[i][0] = sc.nextDouble();
            points[i][1] = sc.nextDouble();
            points[i][2] = sc.nextDouble();
        }

        // Инициализация DSU
        parent = new int[N];
        rank   = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i]   = 0;
        }

        double D2 = D*D;
        // Объединяем пары, у которых расстояние < D
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                double dx = points[i][0] - points[j][0];
                double dy = points[i][1] - points[j][1];
                double dz = points[i][2] - points[j][2];
                double dist2 = dx*dx + dy*dy + dz*dz;
                if (dist2 < D2) {
                    union(i, j);
                }
            }
        }

        // Считаем размер каждого кластера
        int[] count = new int[N];
        for (int i = 0; i < N; i++) {
            int r = find(i);
            count[r]++;
        }

        // Считаем, сколько непустых
        int nonEmpty = 0;
        for (int i = 0; i < N; i++) {
            if (count[i] > 0) {
                nonEmpty++;
            }
        }

        // Собираем в массив
        int[] clusterSizes = new int[nonEmpty];
        int idx = 0;
        for (int i = 0; i < N; i++) {
            if (count[i] > 0) {
                clusterSizes[idx++] = count[i];
            }
        }

        // Сортируем по убыванию — замените bubbleSort(...) на bubbleSortDescending(...)
        bubbleSortDescending(clusterSizes);

        // Выводим
        for (int i = 0; i < clusterSizes.length; i++) {
            System.out.print(clusterSizes[i]);
            if (i < clusterSizes.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    // Реализация DSU
    private static int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    private static void union(int x, int y) {
        int rx = find(x);
        int ry = find(y);
        if (rx != ry) {
            if (rank[rx] < rank[ry]) {
                parent[rx] = ry;
            } else if (rank[rx] > rank[ry]) {
                parent[ry] = rx;
            } else {
                parent[ry] = rx;
                rank[rx]++;
            }
        }
    }

    // Пузырьковая сортировка по убыванию
    private static void bubbleSortDescending(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }
}
