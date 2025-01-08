package by.it.group310971.katsuba.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_LongDivComSubSeq {

    int getDivSeqSize(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);
        // Читаем длину последовательности
        int n = scanner.nextInt();
        // Создаем массив для хранения длин LCM
        int[] lcm = new int[n];
        // Считываем последовательность чисел
        int[] sequence = new int[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = scanner.nextInt();
        }
        // Инициализируем массив LCM значениями 1 (так как каждое число образует LCM длиной 1)
        for (int i = 0; i < n; i++) {
            lcm[i] = 1;
        }
        // Начинаем проходить по последовательности чисел, начиная с первого элемента
        for (int i = 1; i < n; i++) {
            // Для каждого элемента последовательности сравниваем его со всеми предыдущими элементами
            for (int j = 0; j < i; j++) {
                // Если текущий элемент делится на предыдущий и длина LCM, оканчивающегося на предыдущем элементе,
                // увеличиваем на 1 и больше длины LCM, оканчивающегося на текущем элементе,
                // то обновляем длину LCM для текущего элемента
                if (sequence[i] % sequence[j] == 0 && lcm[i] < lcm[j] + 1) {
                    lcm[i] = lcm[j] + 1;
                }
            }
        }
        // Находим максимальную длину LCM
        int maxLCM = 0;
        for (int i = 0; i < n; i++) {
            if (lcm[i] > maxLCM) {
                maxLCM = lcm[i];
            }
        }
        // Возвращаем максимальную длину LCM
        return maxLCM;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "katsuba/lesson06/dataB.txt");
        B_LongDivComSubSeq instance = new B_LongDivComSubSeq();
        int result = instance.getDivSeqSize(stream);
        System.out.print(result);
    }
}
