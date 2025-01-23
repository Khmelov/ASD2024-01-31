package by.it.group310971.rogach.lesson04;

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
        mergeSort(a, 0, a.length - 1); // Call the mergeSort function

        return a;
    }

        // тут ваше решение (реализуйте сортировку слиянием)
        private static void mergeSort(int[] arr, int left, int right) {
            if (left < right) {
                int mid = left + (right - left) / 2;

                mergeSort(arr, left, mid);
                mergeSort(arr, mid + 1, right);

                merge(arr, left, mid, right);
            }
        }

    private static void merge(int[] arr, int left, int mid, int right) {
        int leftArrayLength = mid - left + 1;
        int rightArrayLength = right - mid;

        int[] leftArray = new int[leftArrayLength];
        int[] rightArray = new int[rightArrayLength];

        for (int i = 0; i < leftArrayLength; i++) {
            leftArray[i] = arr[left + i];
        }

        for (int i = 0; i < rightArrayLength; i++) {
            rightArray[i] = arr[mid + 1 + i];
        }

        int leftIndex = 0, rightIndex = 0, mergedIndex = left;

        while (leftIndex < leftArrayLength && rightIndex < rightArrayLength) {
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                arr[mergedIndex++] = leftArray[leftIndex++];
            } else {
                arr[mergedIndex++] = rightArray[rightIndex++];
            }
        }

        while (leftIndex < leftArrayLength) {
            arr[mergedIndex++] = leftArray[leftIndex++];
        }

        while (rightIndex < rightArrayLength) {
            arr[mergedIndex++] = rightArray[rightIndex++];
        }
    }





        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/rogach/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
