package by.it.group310971.rusakovich.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии,
            - за сам массив отрезков - сортировка на месте
            - рекурсивные вызовы должны проводиться на основе 3-разбиения

        2) при поиске подходящих отрезков для точки реализуйте метод бинарного поиска
        для первого отрезка решения, а затем найдите оставшуюся часть решения
        (т.е. отрезков, подходящих для точки, может быть много)

    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/

public class C_QSortOptimized {

    // отрезок
    private static class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
            if (this.start > this.stop) {
                // Если концы отрезков пришли в обратном порядке, меняем их местами
                int temp = this.start;
                this.start = this.stop;
                this.stop = temp;
            }
        }

        @Override
        public int compareTo(Segment o) {
            // Сравнение сначала по старту, затем по стопу
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            }
            return Integer.compare(this.stop, o.stop);
        }
    }

    private static void quickSort(Segment[] array, int low, int high) {
        while (low < high) {
            int[] pivot = partition(array, low, high);
            if (pivot[0] - low < high - pivot[1]) {
                quickSort(array, low, pivot[0] - 1);
                low = pivot[1] + 1;
            } else {
                quickSort(array, pivot[1] + 1, high);
                high = pivot[0] - 1;
            }
        }
    }

    private static int[] partition(Segment[] array, int low, int high) {
        int pivot = array[low].start;
        int lt = low;
        int gt = high;
        int i = low + 1;
        while (i <= gt) {
            if (array[i].start < pivot) {
                swap(array, lt++, i++);
            } else if (array[i].start > pivot) {
                swap(array, i, gt--);
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private static void swap(Segment[] array, int i, int j) {
        Segment temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static int binarySearch(Segment[] segments, int point) {
        int left = 0;
        int right = segments.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (segments[mid].start <= point && point <= segments[mid].stop) {
                return mid;
            } else if (segments[mid].start > point) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        // число отрезков
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        // число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        // читаем сами отрезки
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        // читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // сортируем отрезки
        quickSort(segments, 0, segments.length - 1);

        // обрабатываем каждую точку
        for (int i = 0; i < m; i++) {
            int count = 0;
            int index = binarySearch(segments, points[i]);
            if (index != -1) {
                // проверяем все отрезки, включающие данную точку
                int left = index;
                while (left >= 0 && segments[left].start <= points[i] && points[i] <= segments[left].stop) {
                    count++;
                    left--;
                }
                int right = index + 1;
                while (right < n && segments[right].start <= points[i] && points[i] <= segments[right].stop) {
                    count++;
                    right++;
                }
            }
            result[i] = count;
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/rusakovich/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

}
