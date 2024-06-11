//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package by.it.a_khmelev.lesson01;

import java.util.ArrayList;
import java.util.List;

public class FiboC {
    private long startTime = System.currentTimeMillis();

    public FiboC() {
    }

    private long time() {
        return System.currentTimeMillis() - this.startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = '\ud903';
        int m = 1000;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", Integer.valueOf(n), fibo.fasterC((long)n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        List<Long> o = new ArrayList();
        o.add(0L);
        o.add(1L);

        int i;
        for(i = 2; (Long)o.get(i - 2) != 0L || (Long)o.get(i - 1) != 1L || i <= 2; ++i) {
            o.add(((Long)o.get(i - 2) + (Long)o.get(i - 1)) % (long)m);
        }

        return (Long)o.get((int)(n % (long)(i - 2)));
    }
}
