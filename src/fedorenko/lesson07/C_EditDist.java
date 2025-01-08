package fedorenko.lesson07;

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
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int m = one.length();
        int n = two.length();
        // создаем двумерный массив для хранения промежуточных результатов
        int[][] dp = new int[one.length() + 1][two.length() + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {

                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j -
                            1], dp[i - 1][j - 1]));
                }
            }
        }
        // восстановление операций
        int i = one.length(), j = two.length();
        StringBuilder result = new StringBuilder();
        while (i > 0 && j > 0) {
            if (one.charAt(i - 1) == two.charAt(j - 1)) {
                result.append("#,");
                i--;
                j--;
            } else if (dp[i][j] == dp[i - 1][j - 1] + 1) {
                result.append("~").append(two.charAt(j - 1)).append(",");
                i--;
                j--;
            } else if (dp[i][j] == dp[i][j - 1] + 1) {
                result.append("+").append(two.charAt(j - 1)).append(",");
                j--;
            } else {
                result.append("-").append(one.charAt(i - 1)).append(",");
                i--;
            }
        }

        while (i > 0) {
            result.append("-").append(one.charAt(i - 1)).append(",");
            i--;
        }
        while (j > 0) {
            result.append("+").append(two.charAt(j - 1)).append(",");
            j--;
        }


        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result.toString();
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "fedorenko/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}