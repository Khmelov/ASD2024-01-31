package DorofeyMI_310971.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment other) {
            if (this.start != other.start) {
                return this.start - other.start;
            }
            return this.stop - other.stop;
        }
    }

    // Метод для быстрой сортировки с 3-разбиением
    private void quickSort(Segment[] segments, int low, int high) {
        if (low < high) {
            // Деление на 3 части
            Segment pivot = segments[low];
            int lt = low, gt = high;
            int i = low + 1;
            while (i <= gt) {
                int cmp = segments[i].compareTo(pivot);
                if (cmp < 0) swap(segments, lt++, i++);
                else if (cmp > 0) swap(segments, i, gt--);
                else i++;
            }
            // Рекурсивные вызовы для частей
            quickSort(segments, low, lt - 1);
            quickSort(segments, gt + 1, high);
        }
    }

    // Метод для обмена элементов
    private void swap(Segment[] segments, int i, int j) {
        Segment temp = segments[i];
        segments[i] = segments[j];
        segments[j] = temp;
    }

    // Метод бинарного поиска
    private int binarySearch(Segment[] segments, int point) {
        int low = 0, high = segments.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (segments[mid].start <= point && point <= segments[mid].stop) {
                // Найден подходящий отрезок
                while (mid > low && segments[mid - 1].stop >= point) {
                    // Проверяем, не является ли предыдущий отрезок также подходящим
                    mid--;
                }
                return mid;
            } else if (segments[mid].start > point) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1; // Если подходящий отрезок не найден
    }

    // Основной метод для решения задачи
    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортировка отрезков
        quickSort(segments, 0, n - 1);

        // Поиск подходящих отрезков для каждой точки
        for (int i = 0; i < m; i++) {
            int index = binarySearch(segments, points[i]);
            while (index >= 0 && index < n && segments[index].start <= points[i]) {
                if (segments[index].stop >= points[i]) {
                    result[i]++;
                }
                index++;
            }
        }

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "DorofeyMI_310971/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
