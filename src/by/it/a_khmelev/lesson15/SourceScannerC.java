package by.it.a_khmelev.lesson15;

import java.util.Arrays;
import java.util.Scanner;

public class SourceScannerC  {

    static void rekr(Integer[] state, Integer height, Integer[] result, Integer from, Integer to) {
        if (height == 1) {
            state[to]++;
            state[from]--;
            result[getMax(state)]++;
        } else {
            int temp = getIntermediatePeg(from, to);
            rekr(state, height - 1, result, from, temp);
            state[to]++;
            state[from]--;
            result[getMax(state)]++;
            rekr(state, height - 1, result, temp, to);
        }
    }

    private static int getIntermediatePeg(int from, int to) {
        return from == 0 ? (to == 1 ? 2 : 1) :
                from == 1 ? (to == 0 ? 2 : 0) :
                        (to == 1 ? 0 : 1);
    }

    private static int getMax(Integer[] state) {
        return state[0] > state[1] ? (state[0] > state[2] ? state[0] : state[2]) :
                (state[2] > state[1] ? state[2] : state[1]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Integer[] result = new Integer[n + 1];
        Integer[] state = new Integer[3];

        // Инициализация массивов
        state[0] = n;
        state[1] = 0;
        state[2] = 0;

        for (int i = 0; i <= n; ++i) {
            result[i] = 0;
        }

        // Выполняем рекурсивный расчет
        rekr(state, n, result, 0, 2);

        // Сортировка и вывод результата
        Arrays.sort(result);
        for (int value : result) {
            if (value > 0) {
                System.out.print(value + " ");
            }
        }
    }
}
