package by.it.group310971.Isaichikova.lesson04;

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
        int result = 0;
        result = mergeSortAndCount(a, 0, n - 1);

        return result;
    }

    private int mergeSortAndCount(int[] a, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            int inversionsLeft = mergeSortAndCount(a, left, middle);
            int inversionsRight = mergeSortAndCount(a, middle + 1, right);
            int mergeInversions = mergeAndCount(a, left, middle, right);
            return inversionsLeft + inversionsRight + mergeInversions;
        }
        return 0;
    }

    private int mergeAndCount(int[] a, int left, int middle, int right) {
        int[] leftArray = new int[middle - left + 1];
        int[] rightArray = new int[right - middle];
        int inversions = 0; // счетчик инверсий

        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = a[left + i];
        }
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = a[middle + 1 + i];
        }

        int i = 0, j = 0, k = left;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                a[k] = leftArray[i];
                i++;
            } else {
                a[k] = rightArray[j];
                j++;
                inversions += leftArray.length - i;
            }
            k++;
        }

        while (i < leftArray.length) {
            a[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < rightArray.length) {
            a[k] = rightArray[j];
            j++;
            k++;
        }
        return inversions;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Isaichikova/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}
