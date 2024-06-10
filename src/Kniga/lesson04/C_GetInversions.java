package Kniga.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Размер массива
        int n = scanner.nextInt();

        // Сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Подсчет числа инверсий
        return mergeSortAndCount(a, 0, n - 1);
    }

    private int mergeSortAndCount(int[] array, int left, int right) {
        int count = 0;
        if (left < right) {
            int mid = (left + right) / 2;

            count += mergeSortAndCount(array, left, mid);
            count += mergeSortAndCount(array, mid + 1, right);
            count += mergeAndCount(array, left, mid, right);
        }
        return count;
    }

    private int mergeAndCount(int[] array, int left, int mid, int right) {
        // Размеры двух подмассивов для слияния
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Временные массивы
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Копирование данных во временные массивы
        for (int i = 0; i < n1; ++i) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left, swaps = 0;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
                swaps += (n1 - i);  // Количество оставшихся элементов в левом массиве
            }
        }

        // Копирование оставшихся элементов leftArray[], если таковые есть
        while (i < n1) {
            array[k++] = leftArray[i++];
        }

        // Копирование оставшихся элементов rightArray[], если таковые есть
        while (j < n2) {
            array[k++] = rightArray[j++];
        }

        return swaps;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/ASD2024-01-31/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}
