package by.it.FCP310971.a_kokhan.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по неубыванию последовательность этих чисел.

При сортировке реализуйте метод со сложностью O(n)

Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class B_CountSort {


    int[] countSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int pointsNumber = scanner.nextInt();
        int[] points = new int[pointsNumber];

        //читаем точки
        for (int i = 0; i < pointsNumber; i++)
            points[i]=scanner.nextInt();
        scanner.close();
        //тут реализуйте логику задачи с применением сортировки подсчетом

        int[] numberEntryArray = new int[10];
        for (int i = 0; i < pointsNumber; i++)
            numberEntryArray[points[i]]++;
        int[] result = new int[pointsNumber];
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < numberEntryArray[i]; j++)
                pointsNumber--;
                result[pointsNumber] = i;
            }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result=instance.countSort(stream);
        for (int index:result)
            System.out.print(index+" ");
    }
}
