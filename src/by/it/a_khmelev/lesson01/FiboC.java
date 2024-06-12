package by.it.a_khmelev.lesson01;

import java.util.ArrayList;
import java.util.List;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        long n = 55555L;
        int m = 1000;
        System.out.printf("fasterC(%d, %d)=%d \n\t time=%d \n\n", n, m, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        // Решение с использованием периода Пизано
        List<Long> pisanoPeriod = getPisanoPeriod(m);
        int periodLength = pisanoPeriod.size() - 2;  // Исключаем последние два элемента цикла (0, 1)

        // Найти остаток от деления n на длину периода Пизано
        int reducedN = (int)(n % periodLength);

        // Вернуть остаток от деления n-го числа Фибоначчи на m
        return pisanoPeriod.get(reducedN);
    }

    // Функция для нахождения периода Пизано для данного m
    private List<Long> getPisanoPeriod(int m) {
        List<Long> period = new ArrayList<>();
        period.add(0L);
        period.add(1L);

        for (int i = 2; i < m * m; i++) {
            long next = (period.get(i - 1) + period.get(i - 2)) % m;
            period.add(next);
            // Период Пизано всегда начинается с 01
            if (period.get(i) == 1 && period.get(i - 1) == 0) {
                break;
            }
        }

        return period;
    }
}
