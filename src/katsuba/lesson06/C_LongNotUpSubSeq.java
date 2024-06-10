package katsuba.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        // Подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        // Общая длина последовательности
        int n = scanner.nextInt();
        // Создаем массив для хранения элементов последовательности
        int[] sequence = new int[n];
        // Считываем всю последовательность
        for (int i = 0; i < n; i++) {
            sequence[i] = scanner.nextInt();
        }
        // Создаем массив для хранения длин наибольших невозрастающих подпоследовательностей, заканчивающихся в соответствующем элементе
        int[] dp = new int[n];
        // Создаем массив для хранения предыдущего индекса элемента в наибольшей невозрастающей подпоследовательности
        int[] prevIndex = new int[n];
        // Инициализируем длины наибольших невозрастающих подпоследовательностей значениями 1 (так как каждый элемент образует подпоследовательность длиной 1)
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prevIndex[i] = -1; // Изначально нет предыдущего индекса
        }
        // Начинаем проходить по последовательности чисел, начиная со второго элемента
        for (int i = 1; i < n; i++) {
            // Для каждого элемента сравниваем его со всеми предыдущими элементами
            for (int j = 0; j < i; j++) {
                // Если текущий элемент меньше или равен предыдущему, то обновляем длину наибольшей невозрастающей подпоследовательности для текущего элемента
                if (sequence[i] <= sequence[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    prevIndex[i] = j; // Обновляем предыдущий индекс
                }
            }
        }
        // Находим максимальную длину наибольшей невозрастающей подпоследовательности
        int maxLIS = 0;
        int maxLISIndex = -1;
        for (int i = 0; i < n; i++) {
            if (dp[i] > maxLIS) {
                maxLIS = dp[i];
                maxLISIndex = i;
            }
        }
        // Восстанавливаем индексы элементов в наибольшей невозрастающей подпоследовательности
        List<Integer> resultIndices = new ArrayList<>();
        while (maxLISIndex != -1) {
            resultIndices.add(maxLISIndex);
            maxLISIndex = prevIndex[maxLISIndex];
        }
        // Выводим длину наибольшей невозрастающей подпоследовательности и индексы элементов
        System.out.println(maxLIS);
        for (int i = resultIndices.size() - 1; i >= 0; i--) {
            System.out.print((resultIndices.get(i) + 1) + " ");
        }
        System.out.println();
        // Возвращаем максимальную длину наибольшей невозрастающей подпоследовательности
        return maxLIS;
    }

    public static void main(String[] args) {
        try {
            String root = System.getProperty("user.dir") + "/src/";
            InputStream stream = new FileInputStream(root + "katsuba/lesson06/dataC.txt");
            C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
            int result = instance.getNotUpSeqSize(stream);
            boolean ok = (result == 4);
            System.out.println("C " + (ok ? "passed" : "failed"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
