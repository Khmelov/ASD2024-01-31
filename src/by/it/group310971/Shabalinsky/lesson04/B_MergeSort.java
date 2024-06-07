package by.it.group310971.Shabalinsky.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Реализуйте сортировку слиянием для одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо отсортировать полученный массив.

Sample Input:
5
2 3 9 2 9
Sample Output:
2 2 3 9 9
*/
public class B_MergeSort {

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        // подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // размер массива
        int n = scanner.nextInt();
        // сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // вызов функции сортировки
        mergeSort(a, 0, a.length - 1);

        return a;
    }

    // Реализация сортировки слиянием
    void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            // рекурсивно сортируем каждую половину
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);

            // слияние отсортированных половин
            merge(array, left, middle, right);
        }
    }

    void merge(int[] array, int left, int middle, int right) {
        // вычисляем размеры двух подмассивов для слияния
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // создаем временные массивы
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // копируем данные во временные массивы
        for (int i = 0; i < n1; ++i) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = array[middle + 1 + j];
        }

        // сливаем временные массивы обратно в основной массив
        int i = 0, j = 0;
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

        // копируем оставшиеся элементы leftArray
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // копируем оставшиеся элементы rightArray
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Shabalinsky/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        int[] result = instance.getMergeSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
