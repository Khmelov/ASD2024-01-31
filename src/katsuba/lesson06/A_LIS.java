package katsuba.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_LIS {

    int getSeqSize(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        // Читаем длину последовательности
        int n = scanner.nextInt();
        // Создаем массив для хранения длин LIS
        int[] lis = new int[n];
        // Считываем последовательность чисел
        int[] sequence = new int[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = scanner.nextInt();
        }
        // Инициализируем массив LIS значениями 1 (так как каждое число образует LIS длиной 1)
        for (int i = 0; i < n; i++) {
            lis[i] = 1;
        }
        // Начинаем проходить по последовательности чисел, начиная с первого элемента
        for (int i = 1; i < n; i++) {
            // Для каждого элемента последовательности сравниваем его со всеми предыдущими элементами
            for (int j = 0; j < i; j++) {
                // Если текущий элемент больше предыдущего и длина LIS, оканчивающегося на предыдущем элементе,
                // увеличиваем на 1 и больше длины LIS, оканчивающегося на текущем элементе,
                // то обновляем длину LIS для текущего элемента
                if (sequence[i] > sequence[j] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                }
            }
        }
        // Находим максимальную длину LIS
        int maxLIS = 0;
        for (int i = 0; i < n; i++) {
            if (lis[i] > maxLIS) {
                maxLIS = lis[i];
            }
        }
        // Возвращаем максимальную длину LIS
        return maxLIS;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}
