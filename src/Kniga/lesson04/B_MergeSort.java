package Kniga.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_MergeSort {

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Размер массива
        int n = scanner.nextInt();
        // Сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Реализация сортировки слиянием
        mergeSort(a, 0, a.length - 1);

        return a;
    }

    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            // Сортировка первой и второй половины
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);
            // Слияние отсортированных половин
            merge(array, left, middle, right);
        }
    }

    private void merge(int[] array, int left, int middle, int right) {
        // Размеры двух подмассивов для слияния
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Временные массивы
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Копирование данных во временные массивы
        for (int i = 0; i < n1; ++i) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = array[middle + 1 + j];
        }

        // Слияние временных массивов

        // Инициализация индексов первого и второго подмассивов
        int i = 0, j = 0;

        // Инициализация индекса слияния подмассива
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Копирование оставшихся элементов leftArray[], если таковые есть
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // Копирование оставшихся элементов rightArray[], если таковые есть
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/ASD2024-01-31/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        int[] result = instance.getMergeSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
