package by.it.group310971.katsuba.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt();
        int n = scanner.nextInt();
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Инициализация массива для хранения максимального веса золота
        int[] dp = new int[W + 1];

        // Заполнение массива dp
        for (int j = 1; j <= W; j++) {
            for (int i = 0; i < n; i++) {
                if (gold[i] <= j) {
                    dp[j] = Math.max(dp[j], dp[j - gold[i]] + gold[i]);
                }
            }
        }

        return dp[W];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
