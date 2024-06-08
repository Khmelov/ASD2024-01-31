package by.it.group310971.Isaichikova.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_QSortOptimized {

    private class Segment  implements Comparable{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Object o) {
            Segment other = (Segment) o;
            return Integer.compare(this.start, other.start);
        }
    }

    private void quickSort3(Segment[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivot = a[low].start;
        int i = low + 1;
        int j = high;

        while (i <= j) {
            while (i <= j && a[i].start <= pivot) {
                i++;
            }
            while (i <= j && a[j].start > pivot) {
                j--;
            }
            if (i < j) {
                Segment temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;
                j--;
            }
        }
        Segment temp = a[low];
        a[low] = a[j];
        a[j] = temp;

        quickSort3(a, low, j - 1);
        quickSort3(a, j + 1, high);
    }

    private int binarySearch(Segment[] segments, int point, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (segments[mid].start <= point && segments[mid].stop >= point) {
                return mid;
            } else if (segments[mid].start > point) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }


    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
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
        quickSort3(segments, 0, n - 1);

        for (int i = 0; i < m; i++) {
            int point = points[i];
            int index = binarySearch(segments, point, 0, n - 1);
            if (index != -1) {
                result[i] = 1;
                for (int j = index + 1; j < n; j++) {
                    if (segments[j].start <= point && segments[j].stop >= point) {
                        result[i]++;
                    } else {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Isaichikova/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
