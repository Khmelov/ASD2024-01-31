package by.it.a_khmelev.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_LIS {

    int getSeqSize(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt(); // общая длина последовательности
        int[] m = new int[n]; // массив натуральных чисел
        int[] dp = new int[n]; // массив для хранения длины наибольшей возрастающей подпоследовательности, заканчивающейся на элементе i

        // Чтение последовательности
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        int result = 0; // результат

        // Вычисление длины наибольшей возрастающей подпоследовательности
        for (int i = 0; i < n; i++) {
            dp[i] = 1; // Инициализация длины подпоследовательности как 1
            for (int j = 0; j < i; j++) {
                if (m[i] > m[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1); // Обновление длины подпоследовательности, если нашелся более длинный путь
                }
            }
            result = Math.max(result, dp[i]); // Обновление максимальной длины подпоследовательности
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}
