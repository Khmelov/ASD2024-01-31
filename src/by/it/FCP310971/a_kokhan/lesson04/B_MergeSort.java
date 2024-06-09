package by.it.FCP310971.a_kokhan.lesson04;

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
        int entryNumber = scanner.nextInt();
        //сам массив
        int[] array = new int[entryNumber];
        for (int i = 0; i < entryNumber; i++)
            array[i] = scanner.nextInt();
        scanner.close();

        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием

        int mid, right, leftBuffer, middleBuffer, index;
        int[] temp;

        for (int subSize = 1; subSize < entryNumber; subSize *= 2)
            for (int left = 0; left < entryNumber - subSize; left += 2 * subSize) {
                mid = left + subSize;
                right = Math.min(left + 2 * subSize, entryNumber);
                leftBuffer = left;
                middleBuffer = mid;
                temp = new int[right - left];
                index = 0;
            
                while (leftBuffer < mid && middleBuffer < right)
                    if (array[leftBuffer] <= array[middleBuffer])
                        temp[index++] = array[leftBuffer++];
                    else
                        temp[index++] = array[middleBuffer++];
                while (leftBuffer < mid)
                    temp[index++] = array[leftBuffer++];
                while (middleBuffer < right)
                    temp[index++] = array[middleBuffer++];
            
                for (leftBuffer = left; leftBuffer < right; leftBuffer++)
                    array[leftBuffer] = temp[leftBuffer - left];
            }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return array;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result)
            System.out.print(index+" ");
    }


}
