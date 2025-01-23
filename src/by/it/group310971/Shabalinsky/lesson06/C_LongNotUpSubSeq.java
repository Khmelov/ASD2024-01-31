package by.it.group310971.Shabalinsky.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Задача на программирование: наибольшая невозростающая подпоследовательность

Дано:
    целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] не больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]>=A[i[j+1]].

    В первой строке выведите её длину k,
    во второй - её индексы i[1]<i[2]<…<i[k]
    соблюдая A[i[1]]>=A[i[2]]>= ... >=A[i[n]].

    (индекс начинается с 1)

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    5 3 4 4 2

    Sample Output:
    4
    1 3 4 5
*/


public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        // подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        // общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        // читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        // массив для хранения длины Longest Non-Increasing Subsequence заканчивающейся на каждом элементе
        int[] dp = new int[n];
        // массив для хранения предыдущих элементов в подпоследовательности
        int[] prev = new int[n];

        // инициализируем все dp значениями 1 и prev значениями -1
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1;
        }

        // заполняем dp и prev массивы
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (m[i] <= m[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
        }

        // находим максимальное значение в dp массиве и его индекс
        int maxLength = 0;
        int index = -1;
        for (int i = 0; i < n; i++) {
            if (dp[i] > maxLength) {
                maxLength = dp[i];
                index = i;
            }
        }

        // восстанавливаем саму подпоследовательность
        List<Integer> sequence = new ArrayList<>();
        while (index != -1) {
            sequence.add(0, index + 1); // добавляем 1, чтобы индексы начинались с 1
            index = prev[index];
        }

        // выводим результат
        System.out.println(maxLength);
        for (int i : sequence) {
            System.out.print(i + " ");
        }
        System.out.println();

        return maxLength;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Shabalinsky/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        instance.getNotUpSeqSize(stream);
    }
}