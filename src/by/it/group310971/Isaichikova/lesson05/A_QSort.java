package by.it.group310971.Isaichikova.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_QSort {

    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
            if (start > stop) {
                int temp = start;
                start = stop;
                stop = temp;
            }
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


    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        Segment[] segments=new Segment[n];
        int m = scanner.nextInt();
        int[] points=new int[m];
        int[] result=new int[m];

        for (int i = 0; i < n; i++) {
            segments[i]=new Segment(scanner.nextInt(),scanner.nextInt());
        }
        for (int i = 0; i < m; i++) {
            points[i]=scanner.nextInt();
        }
        quickSort(segments, 0, n - 1);

        for (int i = 0; i < m; i++) {
            int count = 0;
            int j = 0;
            while (j < n && segments[j].stop < points[i]) {
                j++;
            }
            while (j < n && segments[j].start <= points[i]) {
                count++;
                j++;
            }
            result[i] = count;
        }

        return result;
    }


    private void quickSort(Segment[] segments, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(segments, left, right);
            quickSort(segments, left, pivotIndex - 1);
            quickSort(segments, pivotIndex + 1, right);
        }
    }

    private int partition(Segment[] segments, int left, int right) {
        Segment pivot = segments[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (segments[j].compareTo(pivot) <= 0) {
                i++;
                swap(segments, i, j);
            }
        }
        swap(segments, i + 1, right);
        return i + 1;
    }

    private void swap(Segment[] segments, int i, int j) {
        Segment temp = segments[i];
        segments[i] = segments[j];
        segments[j] = temp;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Isaichikova/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result=instance.getAccessory(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
