package by.it._310971_hrakovich.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Задача на программирование: наибольшая невозростающая подпоследовательность

Дано:
    целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] не больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]>=A[i[j+1]].

    В первой строке выведите её длину k,
    во второй - её индексы i[1]<i[2]<…<i[k]
    соблюдая A[i[1]]>=A[i[2]]>= ... >=A[i[n]].

    (индекс начинается с 1)

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    5 3 4 4 2

    Sample Output:
    4
    1 3 4 5
*/


public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int[] numbersArray = new int[scanner.nextInt()];
        //читаем всю последовательность
        for (int i = 0; i < numbersArray.length; i++) {
            numbersArray[i] = scanner.nextInt();
        }
        scanner.close();
        //тут реализуйте логику задачи методами динамического программирования (!!!)
        int[] sequenceLenghtArray = new int[numbersArray.length];
        int[] prev = new int[numbersArray.length];

        // Инициализация
        for (int i = 0; i < numbersArray.length; i++) {
            sequenceLenghtArray[i] = 1;
            prev[i] = -1;
        }

        // Динамическое программирование
        int maxLength = 1;
        int maxIndex = 0;
        for (int i = 1; i < numbersArray.length; i++) {
            for (int j = 0; j < i; j++)
                if (numbersArray[j] >= numbersArray[i] && sequenceLenghtArray[j] + 1 > sequenceLenghtArray[i]) {
                    sequenceLenghtArray[i] = sequenceLenghtArray[j] + 1;
                    prev[i] = j;
                }
            if (sequenceLenghtArray[i] > maxLength) {
                maxLength = sequenceLenghtArray[i];
                maxIndex = i;
            }
        }

        // Восстановление максимальной убывающей подпоследовательности
        List<Integer> result = new ArrayList<>();
        while (maxIndex != -1) {
            result.add(0, maxIndex + 1); // Индексы начинаются с 1
            maxIndex = prev[maxIndex];
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result.size();
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}