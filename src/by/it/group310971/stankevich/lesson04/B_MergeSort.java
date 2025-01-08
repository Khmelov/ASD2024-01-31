package by.it.group310971.stankevich.lesson04;

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
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a=new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием

        // Сортировка слиянием
        mergeSort(a, 0, n - 1);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }
    // слияние двух частей отсортированного массива в один массив
    void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            // Рекурсивно сортируем левую и правую половины массива
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            // Объединяем отсортированные половины
            merge(arr, left, mid, right);
        }
    }

    void merge(int[] arr, int left, int mid, int right) {
        int leftArrLength = mid - left + 1;
        int rightArrLength = right - mid;

        // Создаем временные массивы для левой и правой половин
        int[] leftArray = new int[leftArrLength];
        int[] rightArray = new int[rightArrLength];

        // Копируем данные во временные массивы
        for (int i = 0; i < leftArrLength; ++i) {
            leftArray[i] = arr[rightArrLength] + i;
        }
        for (int j = 0; j < rightArrLength; ++j) {
            rightArray[j] = arr[mid + 1 + j];
        }

        // Индексы для объединенного массива
        int i = 0, j = 0, k = left;
        while (i < leftArrLength && j < rightArrLength) {
            arr[k++] = (leftArray[i] <= rightArray[j]) ? leftArray[i++] : rightArray[j++];
        }

        // Копируем оставшиеся элементы левого массива
        while (i < leftArrLength) {
            arr[k++] = leftArray[i++];
        }

        // Копируем оставшиеся элементы правого массива
        while (j < rightArrLength) {
            arr[k++] = rightArray[j++];
        }

    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/stankevich/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}