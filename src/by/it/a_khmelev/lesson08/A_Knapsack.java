package by.it.a_khmelev.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // Вместимость рюкзака
        int n = scanner.nextInt(); // Количество доступных слитков золота
        int[] weights = new int[n]; // Массив весов слитков золота

        // Заполнение массива весов слитков
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        // Массив, в котором будем хранить максимальный вес золота для каждой вместимости рюкзака
        int[] maxWeights = new int[W + 1];

        // Вычисление максимального веса золота, который можно унести в рюкзаке
        for (int weight : weights) {
            for (int w = weight; w <= W; w++) {
                maxWeights[w] = Math.max(maxWeights[w], maxWeights[w - weight] + weight);
            }
        }

        return maxWeights[W]; // Возвращаем максимальный вес золота для вместимости рюкзака W
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
