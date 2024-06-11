package by.it.a_khmelev.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt(); // общая длина последовательности
        int[] m = new int[n]; // массив натуральных чисел
        int[] dp = new int[n]; // массив для хранения длины наибольшей невозрастающей подпоследовательности, заканчивающейся на элементе i

        // Чтение последовательности
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        // Вычисление длины наибольшей невозрастающей подпоследовательности
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1; // Инициализация длины подпоследовательности как 1
            for (int j = 0; j < i; j++) {
                if (m[i] <= m[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1); // Обновление длины подпоследовательности, если нашелся более длинный путь
                }
            }
            maxLength = Math.max(maxLength, dp[i]); // Обновление максимальной длины подпоследовательности
        }

        // Восстановление самой последовательности
        List<Integer> resultIndices = new ArrayList<>();
        int currentLength = maxLength;
        for (int i = n - 1; i >= 0; i--) {
            if (dp[i] == currentLength) {
                resultIndices.add(i + 1); // Добавление индекса элемента в последовательности
                currentLength--;
            }
        }
        Collections.reverse(resultIndices); // Инвертирование списка индексов, чтобы они были в порядке возрастания

        // Вывод результатов
        System.out.println(maxLength);
        for (int index : resultIndices) {
            System.out.print(index + " ");
        }

        return maxLength;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        instance.getNotUpSeqSize(stream);
    }
}
