package by.it.FCP310971.a_kokhan.lesson04;

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
    int[] findIndex(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер отсортированного массива
        int sortedArraySize = scanner.nextInt();

        //сам отсортированный массива
        int[] array = new int[sortedArraySize];
        for (int i = 1; i <= sortedArraySize; i++)
            array[i - 1] = scanner.nextInt();

        //размер массива индексов
        int indexArraySize = scanner.nextInt(), value, left, right, mid;
        int[] result = new int[indexArraySize];
        for (int i = 0; i < indexArraySize; i++) {
            value = scanner.nextInt();
            //тут реализуйте бинарный поиск индекса

            left = 0;
            right = sortedArraySize - 1;
            result[i] = -1;

            while (left <= right) {
                mid = left + (right - left) / 2;
                if (array[mid] == value) {
                    result[i] = mid + 1;
                    break;
                } else if (array[mid] < value)
                    left = mid + 1;
                else
                    right = mid - 1;
            }
        }
        scanner.close();
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataA.txt");
        A_BinaryFind instance = new A_BinaryFind();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.findIndex(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result)
            System.out.print(index+" ");
    }

}
