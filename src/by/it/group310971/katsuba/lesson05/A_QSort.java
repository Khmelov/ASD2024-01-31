package by.it.group310971.katsuba.lesson05;

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
            return Integer.compare(this.start, o.start);
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt(); // Число отрезков
        Segment[] segments = new Segment[n];
        int m = scanner.nextInt(); // Число точек
        int[] points = new int[m];
        int[] result = new int[m]; // Результат

        // Считываем отрезки
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }
        // Считываем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        Arrays.sort(segments); // Сортируем отрезки по начальным точкам

        // Для каждой точки определяем, сколько отрезков ей принадлежит
        for (int i = 0; i < m; i++) {
            int count = 0;
            for (Segment segment : segments) {
                if (segment.start > points[i]) break; // Прерываем цикл, если начало следующего отрезка больше текущей точки
                if (segment.start <= points[i] && points[i] <= segment.stop) {
                    count++;
                }
            }
            result[i] = count;
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
