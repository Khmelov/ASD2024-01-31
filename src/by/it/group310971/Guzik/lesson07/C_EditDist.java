package by.it.group310971.Guzik.lesson07;

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
        int n = one.length() + 1;
        int m = two.length() + 1;
        int[][] d = new int[n][m];
        int[][] path = new int[n][m];
        for (int i = 0; i < n; i++) {
            d[i][0] = i;
        }
        for (int j = 0; j < m; j++) {
            d[0][j] = j;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    d[i][j] = d[i - 1][j - 1];
                    path[i][j] = 1;
                } else {
                    int min = Math.min(d[i - 1][j - 1], Math.min(d[i - 1][j], d[i][j - 1]));
                    if (min == d[i - 1][j - 1]) {
                        path[i][j] = 1;
                    } else if (min == d[i - 1][j]) {
                        path[i][j] = 2;
                    } else {
                        path[i][j] = 3;
                    }
                    d[i][j] = min + 1;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        int i = n - 1;
        int j = m - 1;
        while (i > 0 && j > 0) {
            char operation = '#';
            char symbol = ' ';
            switch (path[i][j]) {
                case 1:
                    operation = '#';
                    symbol = one.charAt(i - 1);
                    i--;
                    j--;
                    break;
                case 2:
                    operation = '-';
                    symbol = one.charAt(i - 1);
                    i--;
                    break;
                case 3:
                    if (d[i][j - 1] < d[i - 1][j]) {
                        operation = '+';
                        symbol = two.charAt(j - 1);
                        j--;
                    } else {
                        operation = '~';
                        symbol = two.charAt(j - 1);
                        i--;
                    }
                    break;
            }
            result.append(operation).append(symbol).append(',');
        }
        return result.toString();
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Guzik/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}