package by.it.group310971.m_nesterik.lesson05;

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

    // отрезок
    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            if (this.start != o.start) {
                return Integer.compare(this.start, o.start);
            } else {
                return Integer.compare(this.stop, o.stop);
            }
        }
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        // подготовка к чтению данных
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

        // для каждой точки найдем количество отрезков, которые её покрывают
        for (int i = 0; i < m; i++) {
            result[i] = countSegmentsCoveringPoint(segments, points[i]);
        }

        return result;
    }

    private void quickSort(Segment[] array, int left, int right) {
        while (left < right) {
            int[] pivot = partition(array, left, right);
            if (pivot[0] - left < right - pivot[1]) {
                quickSort(array, left, pivot[0] - 1);
                left = pivot[1] + 1;
            } else {
                quickSort(array, pivot[1] + 1, right);
                right = pivot[0] - 1;
            }
        }
    }

    private int[] partition(Segment[] array, int left, int right) {
        Segment pivot = array[left];
        int i = left;
        int j = right;
        int p = left;

        while (i <= j) {
            while (i <= right && array[i].compareTo(pivot) <= 0) {
                if (array[i].compareTo(pivot) == 0) {
                    swap(array, i, p);
                    p++;
                }
                i++;
            }
            while (j >= left && array[j].compareTo(pivot) > 0) {
                j--;
            }
            if (i < j) {
                swap(array, i, j);
            }
        }

        for (int k = left; k < p; k++) {
            swap(array, k, j--);
        }

        return new int[]{j + 1, i - 1};
    }

    private void swap(Segment[] array, int i, int j) {
        Segment temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private int countSegmentsCoveringPoint(Segment[] segments, int point) {
        int left = 0;
        int right = segments.length - 1;
        int count = 0;

        // Бинарный поиск для нахождения первого отрезка, который может покрывать точку
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (segments[mid].start <= point && segments[mid].stop >= point) {
                count++;
                // Пойдем влево и вправо от найденного элемента
                int l = mid - 1;
                int r = mid + 1;
                while (l >= 0 && segments[l].start <= point && segments[l].stop >= point) {
                    count++;
                    l--;
                }
                while (r < segments.length && segments[r].start <= point && segments[r].stop >= point) {
                    count++;
                    r++;
                }
                break;
            } else if (segments[mid].start > point) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/m_nesterik/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result = instance.getAccessory2(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}