package by.it._310971_hrakovich.lesson04;

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

        //вызов рекурсивной функции ля сортироваки массива
        mergeSort(a, 0, n-1);
        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }
    private void mergeSort(int[] arr, int left, int right){
        if (left < right){
            int mid = left + (right-left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    public void merge(int[] arr, int left, int mid, int right){
        //временные массивы для леевой и правой частей
        int[] leftArr = new int[mid - left+1];
        int[] rightArr = new int[right-mid];

        //копирование даннных во временные массивы
        for (int i = 0; i < leftArr.length; i++){
            leftArr[i] = arr[left+i];
        }
        for (int i = 0; i < rightArr.length; i++){
            rightArr[i] = arr[mid + 1 + i];
        }

        //слияние временных массивов обратоно в исходный массив
        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length){
            if (leftArr[i] <= rightArr[j]){
                arr[k++] = leftArr[i++];
            }
            else arr[k++] = rightArr[j++];
        }

        //добавление осставшихся элементов, если они есть
        while(i<leftArr.length) arr[k++]=leftArr[i++];
        while(j<rightArr.length) arr[k++]=rightArr[j++];
    }
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
