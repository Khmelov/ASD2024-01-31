package by.it._310971_hrakovich.lesson14;

import java.util.*;

public class SitesB {
    // Класс для структуры данных DSU
    static class DSU {
        private Map<String, String> parent = new HashMap<>();
        private Map<String, Integer> size = new HashMap<>();

        // Метод для нахождения корня с сокращением пути
        public String find(String x) {
            if (!parent.containsKey(x)) {
                parent.put(x, x);
                size.put(x, 1);
            }
            if (!x.equals(parent.get(x))) {
                parent.put(x, find(parent.get(x))); // сокращение пути
            }
            return parent.get(x);
        }

        // Метод для объединения двух сайтов
        public void union(String x, String y) {
            String rootX = find(x);
            String rootY = find(y);
            if (!rootX.equals(rootY)) {
                // Объединение по размеру
                if (size.get(rootX) < size.get(rootY)) {
                    parent.put(rootX, rootY);
                    size.put(rootY, size.get(rootY) + size.get(rootX));
                } else {
                    parent.put(rootY, rootX);
                    size.put(rootX, size.get(rootX) + size.get(rootY));
                }
            }
        }

        // Метод для получения размеров кластеров
        public List<Integer> getClusterSizes() {
            Map<String, Integer> clusterSizeMap = new HashMap<>();
            for (String site : parent.keySet()) {
                String root = find(site);
                clusterSizeMap.put(root, size.get(root));
            }
            List<Integer> sizes = new ArrayList<>(clusterSizeMap.values());
            Collections.sort(sizes, Collections.reverseOrder()); // Сортировка по убыванию
            return sizes;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DSU dsu = new DSU();

        // Чтение входных данных
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("end")) break; // Завершение ввода
            String[] sites = line.split("\\+");
            if (sites.length == 2) { // Убедимся, что введены два сайта
                dsu.union(sites[0], sites[1]); // Объединение сайтов
            }
        }

        // Получение и вывод размеров кластеров
        List<Integer> clusterSizes = dsu.getClusterSizes();
        for (int size : clusterSizes) {
            System.out.print(size + " ");
        }
    }
}
