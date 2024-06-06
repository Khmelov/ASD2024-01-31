package by.it.FCP310971.a_kokhan.lesson08;

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

    private int index;
    private int maxIndex;

    int getMaxSum(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int stairsNumber = scanner.nextInt();
        maxIndex = stairsNumber-1;
        int stairs[] = new int[stairsNumber];
        for (int i = 0; i < stairsNumber; i++) {
            stairs[i]=scanner.nextInt();
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int result = 0;

        index = 0;
        while (index < stairsNumber) {
            if (stairs[safeIndex()] < 0) {
                index++;
                if (stairs[safeIndex()] < 0 && stairs[index-1] < stairs[safeIndex()])
                    index++;
            }
            result += stairs[safeIndex()];
            index++;
        }


        scanner.close();
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    private int safeIndex(){
        return index > maxIndex ? maxIndex : index;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res=instance.getMaxSum(stream);
        System.out.println(res);
    }

}
