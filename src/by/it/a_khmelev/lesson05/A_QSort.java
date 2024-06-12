package by.it.a_khmelev.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class A_QSort {

    // Класс, представляющий отрезок
    private static class Segment {
        int start;
        int stop;

        Segment(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        // Компаратор для сравнения отрезков по началу
        static class StartComparator implements Comparator<Segment> {
            @Override
            public int compare(Segment o1, Segment o2) {
                return Integer.compare(o1.start, o2.start);
            }
        }

        // Компаратор для сравнения отрезков по концу
        static class StopComparator implements Comparator<Segment> {
            @Override
            public int compare(Segment o1, Segment o2) {
                return Integer.compare(o1.stop, o2.stop);
            }
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt(); // количество отрезков
        int m = scanner.nextInt(); // количество точек

        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        int[] points = new int[m];
        int[] result = new int[m];

        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        Arrays.sort(segments, new Segment.StartComparator());

        for (int i = 0; i < m; i++) {
            int count = 0;
            for (Segment segment : segments) {
                if (segment.start <= points[i] && segment.stop >= points[i]) {
                    count++;
                }
            }
            result[i] = count;
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
