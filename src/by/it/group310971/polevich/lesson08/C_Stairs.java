package by.it.group310971.polevich.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Даны число 1<=n<=100 ступенек лестницы и
целые числа −10000<=a[1],…,a[n]<=10000, которыми помечены ступеньки.
Найдите максимальную сумму, которую можно получить, идя по лестнице
снизу вверх (от нулевой до n-й ступеньки), каждый раз поднимаясь на
одну или на две ступеньки.
Sample Input 1:
2
1 2
Sample Output 1:
3
Sample Input 2:
2
2 -1
Sample Output 2:
1
Sample Input 3:
3
-1 2 1
Sample Output 3:
3
*/

public class C_Stairs {

    int getMaxSum(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int stairs[] = new int[n];
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int a = stairs[0];
        int b = Math.max(stairs[0], stairs[1]);
        int result;
        // проходим по оставшимся ступенькам, вычисляя максимальные суммы
        for (int i = 2; i < n; i++) {
            int c = Math.max(a + stairs[i], b + stairs[i]);
            a = b;
            b = c;

        }
        result = b;
        // возвращаем максимальную сумму, которую можно набрать
        return result;
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/group310971/polevich/lesson08/dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res = instance.getMaxSum(stream);
        System.out.println(res);
    }

}