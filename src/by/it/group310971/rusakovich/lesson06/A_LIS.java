package by.it.group310971.rusakovich.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_LIS {

    int getSeqSize(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);

        // Общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];

        // Читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        // Массив для хранения длин наибольших возрастающих подпоследовательностей
        int[] lis = new int[n];

        // Изначально каждая подпоследовательность имеет длину 1 (сам элемент)
        for (int i = 0; i < n; i++) {
            lis[i] = 1;
        }

        // Заполняем массив lis
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (m[i] > m[j] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                }
            }
        }

        // Находим максимальное значение в массиве lis
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (result < lis[i]) {
                result = lis[i];
            }
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/rusakovich/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}
