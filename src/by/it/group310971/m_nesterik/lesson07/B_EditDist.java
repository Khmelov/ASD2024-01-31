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
    Итерационно вычислить расстояние редактирования двух данных непустых строк
    Sample Input 1:
    ab
    ab
    Sample Output 1:
    0
    Sample Input 2:
    short
    ports
    Sample Output 2:
    3
    Sample Input 3:
    distance
    editing
    Sample Output 3:
    5
*/

public class B_EditDist {


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // получаем длины двух входных строк
        int m = one.length();
        int n = two.length();

        // создаем двумерный массив для хранения промежуточных результатов
        int[][] dp = new int[m + 1][n + 1];

        // заполняем первую строку массива
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // заполняем первый столбец массива
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // заполняем остальную часть массива
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    // если символы совпадают, расстояние равно значению в предыдущей ячейке
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // в противном случае, расстояние равно 1 плюс минимум из трех возможных операций: замена символа, вставка символа, удаление символа
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // возвращаем число операций
        return dp[one.length()][two.length()];
    }



    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/m_nestrik/lesson07/dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}