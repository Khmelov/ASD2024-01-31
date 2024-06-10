package katsuba.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {

    private int inversionsCount; // переменная для хранения числа инверсий

    int calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        // Чтение размера массива и самого массива
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        // Вызов функции сортировки слиянием
        mergeSort(a, 0, n - 1);
        return inversionsCount; // возвращаем число инверсий
    }

    // Функция сортировки слиянием
    private int[] mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            int[] leftArray = mergeSort(array, left, mid); // Сортировка левой половины
            int[] rightArray = mergeSort(array, mid + 1, right); // Сортировка правой половины
            return merge(leftArray, rightArray); // Объединение отсортированных половин
        }
        return new int[]{array[left]}; // Возвращаем отсортированный массив из одного элемента
    }

    // Функция объединения отсортированных половин и подсчета числа инверсий
    private int[] merge(int[] leftArray, int[] rightArray) {
        int[] mergedArray = new int[leftArray.length + rightArray.length];
        int i = 0, j = 0, k = 0;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                mergedArray[k++] = leftArray[i++];
            } else {
                mergedArray[k++] = rightArray[j++];
                // В случае, если элемент из правого массива меньше, чем из левого,
                // это означает наличие инверсии, поэтому увеличиваем счетчик инверсий
                inversionsCount += leftArray.length - i;
            }
        }
        while (i < leftArray.length) {
            mergedArray[k++] = leftArray[i++];
        }
        while (j < rightArray.length) {
            mergedArray[k++] = rightArray[j++];
        }
        return mergedArray;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}
