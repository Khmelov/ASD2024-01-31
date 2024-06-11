package by.it.a_khmelev.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class A_QSort {

    private class Segment implements Comparable<Segment> {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Segment o) {
            // Компаратор должен возвращать отрицательное число, если этот объект "меньше" объекта o,
            // положительное число, если этот объект "больше" объекта o, и ноль, если они "равны".
            return this.start - o.start;
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt(); // число отрезков
        Segment[] segments = new Segment[n];
        int m = scanner.nextInt(); // число точек
        int[] points = new int[m];
        int[] result = new int[m];

        // Чтение отрезков
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        // Чтение точек
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортировка отрезков по начальным точкам
        Arrays.sort(segments);

        // Обработка точек и подсчёт количества пересечений
        for (int i = 0; i < m; i++) {
            int point = points[i];
            for (int j = 0; j < n; j++) {
                if (point >= segments[j].start && point <= segments[j].stop) {
                    result[i]++;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
