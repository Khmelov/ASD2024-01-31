package by.it.a_khmelev.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // Вместимость рюкзака
        int n = scanner.nextInt(); // Количество доступных слитков золота
        int[] weights = new int[n]; // Массив весов слитков золота

        // Заполнение массива весов слитков
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        // Массив, в котором будем хранить максимальный вес золота для каждой вместимости рюкзака от 0 до W
        int[][] maxWeights = new int[n + 1][W + 1];

        // Вычисление максимального веса золота, который можно унести в рюкзаке
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (weights[i - 1] <= w) {
                    maxWeights[i][w] = Math.max(maxWeights[i - 1][w], maxWeights[i - 1][w - weights[i - 1]] + weights[i - 1]);
                } else {
                    maxWeights[i][w] = maxWeights[i - 1][w];
                }
            }
        }

        return maxWeights[n][W]; // Возвращаем максимальный вес золота для вместимости рюкзака W
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
