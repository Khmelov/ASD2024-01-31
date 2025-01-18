package DorofeyMI_310971.lesson14;

import java.util.Arrays;
import java.util.Scanner;

public class StatesHanoiTowerC {
    private static int[] parent;
    private static int[] size;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.close();

        parent = new int[(1 << N) - 1];
        size = new int[(1 << N) - 1];

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        solveHanoi(N, 'A', 'B', 'C');
        printSubtreeSizes();
    }

    private static void solveHanoi(int n, char from, char to, char aux) {
        if (n == 0) return;
        solveHanoi(n - 1, from, aux, to);
        union(find(from), find(to));
        solveHanoi(n - 1, aux, to, from);
    }

    private static int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    private static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
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

    private static void printSubtreeSizes() {
        int[] subtreeSizes = new int[parent.length];
        for (int i = 0; i < parent.length; i++) {
            subtreeSizes[find(i)]++;
        }

        Arrays.sort(subtreeSizes);
        for (int size : subtreeSizes) {
            if (size > 0) {
                System.out.print(size + " ");
            }
        }
    }
}
