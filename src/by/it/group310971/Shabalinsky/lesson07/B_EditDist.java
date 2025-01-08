package by.it.group310971.Shabalinsky.lesson07;

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
        int m = one.length();
        int n = two.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        // базовый случай: строка one пустая
        for (int i = 0; i <= n; i++) {
            prev[i] = i;
        }

        // итеративное заполнение массива
        for (int i = 0; i < m; i++) {
            // инициализация первого элемента текущей строки
            curr[0] = i + 1;
            for (int j = 0; j < n; j++) {
                if (one.charAt(i) == two.charAt(j)) {
                    curr[j + 1] = prev[j];
                } else {
                    curr[j + 1] = 1 + Math.min(curr[j], Math.min(prev[j + 1], prev[j]));
                }
            }
            // обновляем prev для следующей итерации
            System.arraycopy(curr, 0, prev, 0, n + 1);
        }

        return curr[n];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Shabalinsky/lesson07/dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }
}