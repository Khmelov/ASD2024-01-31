package GulyutaMI_310971.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: наибольшая возрастающая подпоследовательность
см.     https://ru.wikipedia.org/wiki/Задача_поиска_наибольшей_увеличивающейся_подпоследовательности
        https://en.wikipedia.org/wiki/Longest_increasing_subsequence

Дано:
    целое число 1≤n≤1000
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]]больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]<A[i[j+1]].

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    1 3 3 2 6

    Sample Output:
    3
*/

public class A_LIS {


    int getSeqSize(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }

        // Массив для хранения длины НВП для каждого элемента
        int[] d = new int[n];
        for (int i = 0; i < n; i++) {
            d[i] = 1; // Каждый элемент сам по себе является НВП длины 1
            for (int j = 0; j < i; j++) {
                if (A[j] < A[i] && d[j] + 1 > d[i]) {
                    d[i] = d[j] + 1; // Обновляем длину НВП для A[i]
                }
            }
        }

        // Поиск максимального значения в массиве d
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (d[i] > result) {
                result = d[i];
            }
        }

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "GulyutaMI_310971/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}
