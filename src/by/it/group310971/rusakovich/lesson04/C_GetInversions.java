package by.it.group310971.rusakovich.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {

    // Метод для подсчета числа инверсий и сортировки массива
    private int mergeSortAndCount(int[] arr, int[] tempArr, int left, int right) {
        int mid, invCount = 0;
        if (left < right) {
            mid = (left + right) / 2;

            invCount += mergeSortAndCount(arr, tempArr, left, mid);
            invCount += mergeSortAndCount(arr, tempArr, mid + 1, right);
            invCount += mergeAndCount(arr, tempArr, left, mid, right);
        }
        return invCount;
    }

    // Метод для слияния двух подмассивов и подсчета инверсий
    private int mergeAndCount(int[] arr, int[] tempArr, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;
        int invCount = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                tempArr[k++] = arr[i++];
            } else {
                tempArr[k++] = arr[j++];
                invCount += (mid + 1) - i;
            }
        }

        while (i <= mid) {
            tempArr[k++] = arr[i++];
        }

        while (j <= right) {
            tempArr[k++] = arr[j++];
        }

        for (i = left; i <= right; i++) {
            arr[i] = tempArr[i];
        }

        return invCount;
    }

    int calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        int[] tempArr = new int[n];
        return mergeSortAndCount(arr, tempArr, 0, n - 1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/rusakovich/lesson02/greedyKnapsack.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}
