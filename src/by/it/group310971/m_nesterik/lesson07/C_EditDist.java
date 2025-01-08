package by.it.group310971.m_nesterik.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/
Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.
Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки
    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,
    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,
    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,
    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/

public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        int m = one.length();
        int n = two.length();
        int[][] dp = new int[m + 1][n + 1];
        char[][] op = new char[m + 1][n + 1];

        // базовые случаи
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
            op[i][0] = '-';
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
            op[0][j] = '+';
        }

        // заполнение массивов
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                    op[i][j] = '#';
                } else {
                    int min = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    if (min == dp[i - 1][j]) {
                        dp[i][j] = min + 1;
                        op[i][j] = '-';
                    } else if (min == dp[i][j - 1]) {
                        dp[i][j] = min + 1;
                        op[i][j] = '+';
                    } else {
                        dp[i][j] = min + 1;
                        op[i][j] = '~';
                    }
                }
            }
        }

        // восстановление редакционного предписания
        StringBuilder result = new StringBuilder();
        int i = m, j = n;
        while (i > 0 || j > 0) {
            char operation = op[i][j];
            if (operation == '#') {
                result.insert(0, "#,");
                i--;
                j--;
            } else if (operation == '-') {
                result.insert(0, "-").insert(1, one.charAt(i - 1)).insert(2, ",");
                i--;
            } else if (operation == '+') {
                result.insert(0, "+").insert(1, two.charAt(j - 1)).insert(2, ",");
                j--;
            } else if (operation == '~') {
                result.insert(0, "~").insert(1, two.charAt(j - 1)).insert(2, ",");
                i--;
                j--;
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/m_nesterik/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}