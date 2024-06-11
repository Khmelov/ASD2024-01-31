package by.it.a_khmelev.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_CountSort {

    int[] countSort(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt(); // размер массива
        int[] points = new int[n];

        // Чтение точек
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }

        // Создание массива для подсчета встречаемости каждого числа
        int[] count = new int[11]; // числа от 0 до 10, включительно

        // Подсчет встречаемости каждого числа
        for (int i = 0; i < n; i++) {
            count[points[i]]++;
        }

        // Заполнение итогового массива в отсортированном порядке
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                points[index++] = i;
            }
        }

        return points;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}
