package by.it.a_khmelev.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {

    long calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        return mergeSortAndCountInversions(a, 0, a.length - 1);
    }

    long mergeSortAndCountInversions(int[] a, int left, int right) {
        long count = 0;
        if (left < right) {
            int middle = (left + right) / 2;
            count += mergeSortAndCountInversions(a, left, middle);
            count += mergeSortAndCountInversions(a, middle + 1, right);
            count += mergeAndCountSplitInversions(a, left, middle, right);
        }
        return count;
    }

    long mergeAndCountSplitInversions(int[] a, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; ++i) {
            L[i] = a[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = a[middle + 1 + j];
        }
        int i = 0, j = 0, k = left;
        long count = 0;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                a[k] = L[i];
                i++;
            } else {
                a[k] = R[j];
                j++;
                count += (middle - i + 1); // Это и есть инверсия
            }
            k++;
        }
        while (i < n1) {
            a[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            a[k] = R[j];
            j++;
            k++;
        }
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        long result = instance.calc(stream);
        System.out.print(result);
    }
}
