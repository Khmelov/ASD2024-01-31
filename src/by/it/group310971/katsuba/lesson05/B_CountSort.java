package by.it.group310971.katsuba.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_CountSort {

    int[] countSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //размер массива
        int n = scanner.nextInt();
        int[] points = new int[n];

        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }

        // Массив для подсчета вхождений каждого числа
        int[] counts = new int[11]; // Индексы от 0 до 10 для чисел от 1 до 10

        // Подсчитываем количество вхождений каждого числа
        for (int num : points) {
            counts[num]++;
        }

        // Выводим числа в отсортированном порядке
        int[] result = new int[n];
        int index = 0;
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < counts[i]; j++) {
                result[index++] = i;
            }
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int num : result) {
            System.out.print(num + " ");
        }
    }
}
