package by.it.a_khmelev.lesson08;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class A_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // Вместимость рюкзака
        int n = scanner.nextInt(); // Сколько есть вариантов золотых слитков

        int[] weights = new int[n]; // Веса слитков
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        // Создаем массив для хранения максимального веса
        int[] maxWeights = new int[W + 1];

        // Заполняем массив максимальных весов
        for (int w = 0; w <= W; w++) {
            for (int j = 0; j < n; j++) {
                if (weights[j] <= w) {
                    int weight = maxWeights[w - weights[j]] + weights[j];
                    if (weight > maxWeights[w]) {
                        maxWeights[w] = weight;
                    }
                }
            }
        }

        // Максимальный вес золота, который можно унести в рюкзаке
        int result = maxWeights[W];
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
