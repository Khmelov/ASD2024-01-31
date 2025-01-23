package by.it.group310971.katsuba.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        int m = one.length();
        int r = two.length();
        int[][] dp = new int[m + 1][r + 1];
        StringBuilder result = new StringBuilder();

        // Инициализация базовых случаев
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // Преобразование первой строки в пустую строку
        }
        for (int j = 0; j <= r; j++) {
            dp[0][j] = j; // Преобразование пустой строки во вторую строку
        }

        // Заполнение таблицы dp
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= r; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // Если символы равны, операции не требуется
                } else {
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + 1
                    ); // Минимум среди вставки, удаления и замены
                }
            }
        }

        // Обратное отслеживание пути
        int i = m, j = r;
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && one.charAt(i - 1) == two.charAt(j - 1)) {
                result.insert(0, "#,");
                i--;
                j--;
            } else if (i > 0 && (j == 0 || dp[i][j] == dp[i - 1][j] + 1)) {
                result.insert(0, "-" + one.charAt(i - 1) + ",");
                i--;
            } else if (j > 0 && (i == 0 || dp[i][j] == dp[i][j - 1] + 1)) {
                result.insert(0, "+" + two.charAt(j - 1) + ",");
                j--;
            } else if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + 1) {
                result.insert(0, "~" + two.charAt(j - 1) + ",");
                i--;
                j--;
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}
