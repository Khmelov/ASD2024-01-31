package by.it.a_khmelev.lesson14;

import java.util.*;

public class StatesHanoiTowerC {

    static class DSU {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> size = new HashMap<>();

        // Найти представителя множества (сжатие пути)
        public String find(String x) {
            if (!parent.containsKey(x)) {
                parent.put(x, x);
                size.put(x, 1);
            }
            if (!x.equals(parent.get(x))) {
                parent.put(x, find(parent.get(x))); // Сжатие пути
            }
            return parent.get(x);
        }

        // Объединить два множества (эвристика по размеру)
        public void union(String x, String y) {
            String rootX = find(x);
            String rootY = find(y);

            if (!rootX.equals(rootY)) {
                int sizeX = size.get(rootX);
                int sizeY = size.get(rootY);

                if (sizeX < sizeY) {
                    parent.put(rootX, rootY);
                    size.put(rootY, sizeX + sizeY);
                } else {
                    parent.put(rootY, rootX);
                    size.put(rootX, sizeX + sizeY);
                }
            }
        }

        // Получить размер множества
        public int getSize(String x) {
            return size.get(find(x));
        }
    }

    static class TowerState {
        int[] a, b, c;

        TowerState(int[] a, int[] b, int[] c) {
            this.a = a.clone();
            this.b = b.clone();
            this.c = c.clone();
        }

        String getKey() {
            return Arrays.toString(a) + "|" + Arrays.toString(b) + "|" + Arrays.toString(c);
        }
    }

    // Решение задачи Ханойских башен
    private static void solveHanoi(int n, char from, char to, char aux, int[] a, int[] b, int[] c, List<TowerState> states) {
        if (n == 0) return;

        // Перенос n-1 дисков на вспомогательный стержень
        solveHanoi(n - 1, from, aux, to, a, b, c, states);

        // Перемещение n-го диска
        moveDisk(n, from, to, a, b, c);
        states.add(new TowerState(a, b, c));

        // Перенос n-1 дисков со вспомогательного стержня на целевой
        solveHanoi(n - 1, aux, to, from, a, b, c, states);
    }

    private static void moveDisk(int n, char from, char to, int[] a, int[] b, int[] c) {
        int[] fromTower = getTower(from, a, b, c);
        int[] toTower = getTower(to, a, b, c);

        for (int i = 0; i < fromTower.length; i++) {
            if (fromTower[i] == n) {
                fromTower[i] = 0; // Убираем диск с исходного стержня
                break;
            }
        }
        for (int i = 0; i < toTower.length; i++) {
            if (toTower[i] == 0) {
                toTower[i] = n; // Добавляем диск на целевой стержень
                break;
            }
        }
    }

    private static int[] getTower(char name, int[] a, int[] b, int[] c) {
        return switch (name) {
            case 'A' -> a;
            case 'B' -> b;
            case 'C' -> c;
            default -> throw new IllegalArgumentException("Invalid tower name: " + name);
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[] a = new int[n];
        int[] b = new int[n];
        int[] c = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = n - i;
        }

        List<TowerState> states = new ArrayList<>();
        solveHanoi(n, 'A', 'B', 'C', a, b, c, states);

        DSU dsu = new DSU();
        for (TowerState state : states) {
            String key = state.getKey();
            dsu.find(key);
            for (TowerState other : states) {
                if (other != state && isSameMaxHeight(state, other)) {
                    dsu.union(key, other.getKey());
                }
            }
        }

        Map<String, Integer> clusterSizes = new HashMap<>();
        for (TowerState state : states) {
            String root = dsu.find(state.getKey());
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        List<Integer> sortedSizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sortedSizes);
        for (int size : sortedSizes) {
            System.out.print(size + " ");
        }
    }

    private static boolean isSameMaxHeight(TowerState state1, TowerState state2) {
        return Math.max(Math.max(maxHeight(state1.a), maxHeight(state1.b)), maxHeight(state1.c)) ==
                Math.max(Math.max(maxHeight(state2.a), maxHeight(state2.b)), maxHeight(state2.c));
    }

    private static int maxHeight(int[] tower) {
        int height = 0;
        for (int disk : tower) {
            if (disk != 0) height++;
        }
        return height;
    }
}