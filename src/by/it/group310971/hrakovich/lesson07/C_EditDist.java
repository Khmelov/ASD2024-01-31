package by.it.group310971.hrakovich.lesson07;

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
        int oneLength = one.length(), twoLength = two.length();
        int[][] distanceMatrix = new int[oneLength + 1][twoLength + 1];

        for (int i = 0; i <= oneLength; i++)
            distanceMatrix[i][0] = i;

        for (int j = 0; j <= twoLength; j++)
            distanceMatrix[0][j] = j;

        for (int i = 1; i <= oneLength; i++)
            for (int j = 1; j <= twoLength; j++)
                if (one.charAt(i - 1) == two.charAt(j - 1))
                    distanceMatrix[i][j] = distanceMatrix[i - 1][j - 1];
                else
                    distanceMatrix[i][j] = 1 + Math.min(distanceMatrix[i - 1][j - 1], Math.min(distanceMatrix[i][j - 1], distanceMatrix[i - 1][j]));

        int i = oneLength;
        int j = twoLength;
        StringBuilder prescription = new StringBuilder();
        while (i > 0 && j > 0) {
            if (one.charAt(i - 1) == two.charAt(j - 1)) {
                prescription.insert(0, "#,");
                i--;
                j--;
            } else if (distanceMatrix[i][j] == distanceMatrix[i - 1][j - 1] + 1) {
                prescription.insert(0, "~" + two.charAt(j - 1) + ",");
                i--;
                j--;
            } else if (distanceMatrix[i][j] == distanceMatrix[i][j - 1] + 1) {
                prescription.insert(0, "+" + two.charAt(j - 1) + ",");
                j--;
            } else if (distanceMatrix[i][j] == distanceMatrix[i - 1][j] + 1) {
                prescription.insert(0, "-" + one.charAt(i - 1) + ",");
                i--;
            }
        }
        while (i > 0) {
            prescription.insert(0, "-" + one.charAt(i - 1));
            i--;
        }
        while (j > 0) {
            prescription.insert(0, "+" + two.charAt(j - 1));
            j--;
        }
        return prescription.toString();
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        scanner.close();
    }

}