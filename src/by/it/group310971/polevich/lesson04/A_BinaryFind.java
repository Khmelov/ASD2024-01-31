package by.it.group310971.polevich.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
В первой строке источника данных даны:
        - целое число 1<=n<=100000 (размер массива)
        - сам массив A[1...n] из n различных натуральных чисел,
          не превышающих 10E9, в порядке возрастания,
Во второй строке
        - целое число 1<=k<=10000 (сколько чисел нужно найти)
        - k натуральных чисел b1,...,bk не превышающих 10E9 (сами числа)
Для каждого i от 1 до kk необходимо вывести индекс 1<=j<=n,
для которого A[j]=bi, или -1, если такого j нет.
        Sample Input:
        5 1 5 8 12 13
        5 8 1 23 1 11
        Sample Output:
        3 1 -1 1 -1
(!) Обратите внимание на смещение начала индекса массивов JAVA относительно условий задачи
*/

public class A_BinaryFind {
    // алгоритм бинарного поиска для нахождения индекса элемента
    private int binarySearch(int[] arr, int value) {
        int left = 0; // левая граница диапазона поиска
        int right = arr.length - 1; // правая граница диапазона поиска

        while (left <= right) {
            // вычисление индекса середины текущего диапазона поиска
            int mid = (left + right) / 2;

            if (arr[mid] == value) {
                // возвращаем ту же позицию, т.к. индексация начинается с 0.
                return mid + 1;
            }
            else if (arr[mid] < value) {
                // искомое значение находится в правой половине диапазона, поэтому левую границу диапазона, перемещаем вправо на один эл-т.
                left = mid + 1;
            } else
                // искомое значение находится в правой половине диапазона, поэтому левую границу диапазона, перемещаем влево на один эл-т
                right = mid - 1;
        }

        return -1; // значение в массиве не найдено
    }
    int[] findIndex(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер отсортированного массива
        int n = scanner.nextInt();
        //сам отсортированный массива
        int[] a = new int[n]; // массив, к. будет хранить рез-ты поиска
        for (int i = 1; i <= n; i++) {
            a[i - 1] = scanner.nextInt();
        }

        //размер массива индексов
        int k = scanner.nextInt();
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            int value = scanner.nextInt();
            int index = binarySearch(a, value);
            result[i] = index; // сохраняем рез-т поиска
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/polevich/lesson04/dataA.txt");
        A_BinaryFind instance = new A_BinaryFind();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.findIndex(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}