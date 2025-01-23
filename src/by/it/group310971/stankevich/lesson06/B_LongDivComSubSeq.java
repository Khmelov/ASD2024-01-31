package by.it.group310971.stankevich.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: наибольшая кратная подпоследовательность
Дано:
    целое число 1≤n≤1000
    массив A[1…n] натуральных чисел, не превосходящих 2E9.
Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] делится на предыдущий
    т.е. для всех 1<=j<k, A[i[j+1]] делится на A[i[j]].
Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Sample Input:
    4
    3 6 7 12
    Sample Output:
    3
*/
public class B_LongDivComSubSeq {

    int getDivSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        //решение с помощью бинарного поиска
        int[] tail = new int[n]; //tail[i] - значение последнего элемента в подпоследовательности длины i+1
        int length = 0; //длина наибольшей кратной подпоследовательности

        //тут реализуйте логику задачи методами динамического программирования (!!!)

        int[] seq = new int[n]; // массив для хранения длин подпоследовательностей
        int maxLength = 0; // максимальная длина подпоследовательности

        for (int i = 0; i < n; i++) {
            // минимальная длина подпоследовательности - 1
            seq[i] = 1;
            for (int j = 0; j < i; j++) {
                if (m[i] % m[j] == 0 && seq[j] + 1 > seq[i]) {
                    seq[i] = seq[j] + 1;
                }
            }
            maxLength = Math.max(maxLength, seq[i]);
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!    КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return maxLength;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/stankevich/lesson06/dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }

}
