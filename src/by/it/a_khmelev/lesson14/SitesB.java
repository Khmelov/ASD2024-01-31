package by.it.a_khmelev.lesson14;

import java.util.*;

public class SitesB {

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DSU dsu = new DSU();

        System.out.println("Введите пары связанных сайтов (end для завершения ввода):");

        // Считываем ввод
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("end")) {
                break;
            }
            String[] sites = line.split("\\+");
            String siteA = sites[0];
            String siteB = sites[1];
            dsu.union(siteA, siteB);
        }

        // Считаем размеры кластеров
        Map<String, Integer> clusterSizes = new HashMap<>();
        for (String site : dsu.parent.keySet()) {
            String root = dsu.find(site);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        // Сортируем размеры кластеров
        List<Integer> sortedSizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sortedSizes);

        // Выводим результат
        for (int size : sortedSizes) {
            System.out.print(size + " ");
        }
    }
}