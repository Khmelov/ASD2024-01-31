package by.it.a_khmelev.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

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

        // массив для хранения длин наибольших невозрастающих подпоследовательностей
        int[] dp = new int[n];
        // массив для хранения предшествующих индексов для восстановления пути
        int[] prev = new int[n];

        // инициализация dp массива с 1
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1;
        }

        // основной алгоритм динамического программирования
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (m[i] <= m[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
        }

        // находим максимальную длину среди всех возможных невозрастающих подпоследовательностей
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (dp[i] > dp[maxIndex]) {
                maxIndex = i;
            }
        }

        // восстановление пути
        int[] result = new int[dp[maxIndex]];
        int k = dp[maxIndex];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxIndex + 1;
            maxIndex = prev[maxIndex];
        }

        // вывод результата
        System.out.println(k);
        for (int i = 0; i < k; i++) {
            System.out.print(result[i] + " ");
        }

        return k;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/ASD2024-01-31/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }
}
