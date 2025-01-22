package by.it.group310971.a_kokhan.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: рюкзак без повторов

Первая строка входа содержит целые числа
    1<=W<=100000     вместимость рюкзака
    1<=n<=300        число золотых слитков
                    (каждый можно использовать только один раз).
Следующая строка содержит n целых чисел, задающих веса каждого из слитков:
  0<=w[1]<=100000 ,..., 0<=w[n]<=100000

Найдите методами динамического программирования
максимальный вес золота, который можно унести в рюкзаке.

Sample Input:
10 3
1 4 8
Sample Output:
9

*/

public class B_Knapsack {

    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);
        int maxCapacity = scanner.nextInt();
        int barNumber = scanner.nextInt();

        int gold[] = new int[barNumber];
        for (int i = 0; i < barNumber; i++)
            gold[i]=scanner.nextInt();
        scanner.close();

        int[][] combinationMatrix = new int[gold.length + 1][maxCapacity + 1];
    
        for (int i = 1; i <= gold.length; i++)
            for (int j = 1; j <= maxCapacity; j++)
                if (j >= gold[i - 1])
                    combinationMatrix[i][j] = Math.max(combinationMatrix[i - 1][j], combinationMatrix[i - 1][j - gold[i - 1]] + gold[i - 1]);
                else
                    combinationMatrix[i][j] = combinationMatrix[i - 1][j];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return combinationMatrix[gold.length][maxCapacity];
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}
