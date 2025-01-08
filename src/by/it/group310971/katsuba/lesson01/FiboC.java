package by.it.group310971.katsuba.lesson01;

import java.util.ArrayList;
import java.util.List;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        long n = 55555;
        int m = 1000;
        System.out.printf("fasterC(%d, %d)=%d \n\t time=%d ms\n\n", n, m, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        if (m <= 1) {
            return 0;
        }

        // Найти период Пизано
        List<Long> pisanoPeriod = getPisanoPeriod(m);
        int periodLength = pisanoPeriod.size();

        // Индекс в периоде Пизано
        int remainderIndex = (int) (n % periodLength);

        // Результат
        return pisanoPeriod.get(remainderIndex);
    }

    private List<Long> getPisanoPeriod(int m) {
        List<Long> pisanoPeriod = new ArrayList<>();
        pisanoPeriod.add(0L);
        pisanoPeriod.add(1L);

        for (int i = 2; i < m * m; i++) {
            long next = (pisanoPeriod.get(i - 1) + pisanoPeriod.get(i - 2)) % m;
            pisanoPeriod.add(next);

            // Проверка на начало нового периода
            if (pisanoPeriod.get(i) == 1 && pisanoPeriod.get(i - 1) == 0) {
                pisanoPeriod.remove(pisanoPeriod.size() - 1);
                pisanoPeriod.remove(pisanoPeriod.size() - 1);
                break;
            }
        }

        return pisanoPeriod;
    }
}
