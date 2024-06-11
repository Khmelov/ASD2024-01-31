package by.it.a_khmelev.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_Stairs {

    int getMaxSum(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt(); // Количество ступенек
        int[] stairs = new int[n]; // Массив, содержащий значения на каждой ступеньке

        // Заполнение массива значениями на каждой ступеньке
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }

        // Массив для хранения максимальной суммы для каждой ступеньки
        int[] maxSum = new int[n];

        // Базовые случаи
        maxSum[0] = stairs[0];
        maxSum[1] = Math.max(stairs[0] + stairs[1], stairs[1]);

        // Рекурсивное заполнение массива maxSum
        for (int i = 2; i < n; i++) {
            maxSum[i] = Math.max(maxSum[i - 1], maxSum[i - 2]) + stairs[i];
        }

        return maxSum[n - 1]; // Возвращаем максимальную сумму
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res = instance.getMaxSum(stream);
        System.out.println(res);
    }
}
