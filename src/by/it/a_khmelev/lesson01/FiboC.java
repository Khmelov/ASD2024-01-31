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
        long n = 55555L; // n should be long to support up to 1E18
        int m = 1000;
        System.out.printf("fasterC(%d, %d)=%d \n\t time=%d \n\n", n, m, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        // Calculate Pisano period
        List<Long> pisanoPeriod = new ArrayList<>();
        pisanoPeriod.add(0L);
        pisanoPeriod.add(1L);
        int i = 2;

        while (true) {
            pisanoPeriod.add((pisanoPeriod.get(i - 1) + pisanoPeriod.get(i - 2)) % m);
            // Check for the beginning of a new period
            if (pisanoPeriod.get(i) == 1 && pisanoPeriod.get(i - 1) == 0) {
                break;
            }
            i++;
        }

        int periodLength = pisanoPeriod.size() - 2; // exclude the last two numbers of the new cycle

        // Find the position in the Pisano period
        int position = (int)(n % periodLength);

        return pisanoPeriod.get(position);
    }
}
