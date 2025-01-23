package by.it.group310971.katsuba.lesson05;

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

    // Отрезок
    private static class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            // Компаратор должен сравнивать отрезки по их начальной точке
            return Integer.compare(this.start, o.start);
        }
    }

    // Метод для поиска количества точек, принадлежащих отрезкам
    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        // подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!
        // число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        // число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        // читаем сами отрезки
        for (int i = 0; i < n; i++) {
            // читаем начало и конец каждого отрезка
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        // читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортируем отрезки
        Arrays.sort(segments);

        // Для каждой точки считаем количество перекрытий
        for (int i = 0; i < m; i++) {
            int point = points[i];
            result[i] = countSegmentsContainingPoint(segments, point);
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    private int countSegmentsContainingPoint(Segment[] segments, int point) {
        int count = 0;
        int leftIndex = binarySearch(segments, point);
        if (leftIndex == -1) {
            return count;
        }
        for (int i = leftIndex; i < segments.length && segments[i].start <= point; i++) {
            if (segments[i].stop >= point) {
                count++;
            }
        }
        return count;
    }

    private int binarySearch(Segment[] segments, int point) {
        int left = 0, right = segments.length - 1;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (segments[mid].start <= point) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
