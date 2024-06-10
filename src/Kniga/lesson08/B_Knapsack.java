package Kniga.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_Knapsack {

    int getMaxWeight(InputStream stream) {
        // Read input
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt();
        int n = scanner.nextInt();
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Initialize dp array
        int[] dp = new int[W + 1];

        // Process each gold bar
        for (int i = 0; i < n; i++) {
            for (int j = W; j >= gold[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - gold[i]] + gold[i]);
            }
        }

        // The maximum weight we can achieve for capacity W is dp[W]
        return dp[W];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/ASD2024-01-31/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
