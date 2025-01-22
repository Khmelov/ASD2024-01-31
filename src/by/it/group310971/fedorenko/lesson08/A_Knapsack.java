package by.it.group310971.fedorenko.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: рюкзак с повторами

Первая строка входа содержит целые числа
    1<=W<=100000     вместимость рюкзака
    1<=n<=300        сколько есть вариантов золотых слитков
                     (каждый можно использовать множество раз).
Следующая строка содержит n целых чисел, задающих веса слитков:
  0<=w[1]<=100000 ,..., 0<=w[n]<=100000

Найдите методами динамического программирования
максимальный вес золота, который можно унести в рюкзаке.


Sample Input:
10 3
1 4 8
Sample Output:
10

Sample Input 2:

15 3
2 8 16
Sample Output 2:
14

*/

public class A_Knapsack {

    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt();
        int n = scanner.nextInt();
        int gold[] = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }
        // массив для хранения результатов
        int[][] dp = new int[n+1][W+1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                dp[i][j] = -1;
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return knapsackWithRepeat(gold, W, n, dp);  // вызываем рекурсивную функцию
    }

    int knapsackWithRepeat(int[] gold, int W, int n, int[][] dp) {
        // если количество предметов или вместимость рюкзака равны 0, возвращаем 0
        if (n == 0 || W == 0) {
            return 0;
        }
        // если значение для данной пары (n, W) уже вычислено, возвращаем его
        if (dp[n][W] != -1) {
            return dp[n][W];
        }
        if (gold[n-1] > W) {
            dp[n][W] = knapsackWithRepeat(gold, W, n-1, dp);
        } else {
            dp[n][W] = Math.max(knapsackWithRepeat(gold, W, n-1, dp),
                    gold[n-1] + knapsackWithRepeat(gold, W-gold[n-1], n,
                            dp));
        }
        return dp[n][W];
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "fedorenko/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
