package by.it.a_khmelev.lesson08;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class B_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // Вместимость рюкзака
        int n = scanner.nextInt(); // Число золотых слитков

        int[] weights = new int[n]; // Веса слитков
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        // Создаем массив для хранения максимального веса
        int[][] maxWeights = new int[n + 1][W + 1];

        // Заполняем массив максимальных весов
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                maxWeights[i][w] = maxWeights[i - 1][w];
                if (weights[i - 1] <= w) {
                    int weight = maxWeights[i - 1][w - weights[i - 1]] + weights[i - 1];
                    if (weight > maxWeights[i][w]) {
                        maxWeights[i][w] = weight;
                    }
                }
            }
        }

        // Максимальный вес золота, который можно унести в рюкзаке
        int result = maxWeights[n][W];
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
