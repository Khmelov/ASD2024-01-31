package by.it.group310971.katsuba.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_EditDist {

    int getDistanceEdinting(String one, String two) {
        int m = one.length();
        int r = two.length();
        int[][] dp = new int[m + 1][r + 1];

        // Инициализируем базовые случаи
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;  // Преобразование первой строки в пустую строку
        }
        for (int j = 0; j <= r; j++) {
            dp[0][j] = j;  // Преобразование пустой строки во вторую строку
        }

        // Заполняем таблицу dp
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= r; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];  // Если символы равны, операции не требуется
                } else {
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + 1
                    );  // Минимум среди вставки, удаления и замены
                }
            }
        }

        return dp[m][r];  // Результат находится в правом нижнем углу таблицы
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}
