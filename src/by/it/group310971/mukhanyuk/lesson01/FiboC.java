package by.it.group310971.mukhanyuk.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.util.ArrayList;
import java.util.List;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 55555;
        int m = 1000;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        if (n <= 1) {
            return n % m;
        }

        List<Long> pisanoPeriod = new ArrayList<>();
        pisanoPeriod.add(0L);
        pisanoPeriod.add(1L);

        for (int i = 2; i < m * m; i++) {
            pisanoPeriod.add((pisanoPeriod.get(i - 1) + pisanoPeriod.get(i - 2)) % m);

            // Если мы нашли начало следующего периода Пизано, выходим из цикла
            if (pisanoPeriod.get(i) == 1 && pisanoPeriod.get(i - 1) == 0) {
                pisanoPeriod.remove(i);
                pisanoPeriod.remove(i - 1);
                break;
            }
        }

        int pisanoLength = pisanoPeriod.size();
        int remainderIndex = (int) (n % pisanoLength);

        return pisanoPeriod.get(remainderIndex);

    }


}

