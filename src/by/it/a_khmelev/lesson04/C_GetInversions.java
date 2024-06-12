package by.it.a_khmelev.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        return mergeSortAndCountInversions(a, 0, a.length - 1);
    }

    int mergeSortAndCountInversions(int[] array, int left, int right) {
        int inversions = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;
            inversions += mergeSortAndCountInversions(array, left, mid);
            inversions += mergeSortAndCountInversions(array, mid + 1, right);
            inversions += mergeAndCountSplitInversions(array, left, mid, right);
        }
        return inversions;
    }

    int mergeAndCountSplitInversions(int[] array, int left, int mid, int right) {
        int[] temp = new int[array.length];
        int inversions = 0;

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
                inversions += (mid - i + 1);
            }
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        for (int l = left; l <= right; l++) {
            array[l] = temp[l];
        }

        return inversions;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}
