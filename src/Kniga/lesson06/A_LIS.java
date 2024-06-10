package Kniga.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_LIS {

    int getSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        // общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        // читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        // массив для хранения длин наибольших возрастающих подпоследовательностей
        int[] dp = new int[n];
        // инициализация dp массив с 1
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        // основной алгоритм динамического программирования
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (m[i] > m[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                }
            }
        }

        // находим максимальную длину среди всех возможных LIS
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] > result) {
                result = dp[i];
            }
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/ASD2024-01-31/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}
