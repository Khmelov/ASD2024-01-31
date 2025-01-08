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
    Две непустые строки данных длиной не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Рекурсивно вычислить удаление редактирования двух данных непустых строк

    Пример ввода 1:
    аб
    аб
    Пример вывода 1:
    0

    Пример ввода 2:
    короткий
    порты
    Пример вывода 2:
    3

    Пример ввода 3:
    расстояние
    редактирование
    Пример вывода 3:
    5

*/

public class A_EditDist {


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ!!!!!!!!!!!!!!!!!!!!!!!!!
        int n = one.length() + 1;
        int m = two.length() + 1;
        int[][] d = new int[n][m];
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
                } else {
                    d[i][j] = Math.min(d[i - 1][j - 1], Math.min(d[i - 1][j], d[i][j - 1])) + 1;
                }
            }
        }
        int result = d[n - 1][m - 1];
        //!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Guzik/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }
}