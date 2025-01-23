package by.it.group310971.katsuba.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_MergeSort {

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // Чтение размера массива и самого массива
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Вызов функции сортировки слиянием
        mergeSort(a, 0, n - 1);

        return a;
    }

    // Функция сортировки слиянием
    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid); // Сортировка левой половины
            mergeSort(array, mid + 1, right); // Сортировка правой половины
            merge(array, left, mid, right); // Объединение отсортированных половин
        }
    }

    // Функция объединения отсортированных половин
    private void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Создание временных массивов для хранения половин
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Копирование данных во временные массивы
        for (int i = 0; i < n1; ++i) {
            L[i] = array[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = array[mid + 1 + j];
        }

        // Объединение временных массивов
        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }

        // Добавление оставшихся элементов из L (если есть)
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        // Добавление оставшихся элементов из R (если есть)
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        int[] result = instance.getMergeSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
