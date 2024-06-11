//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package by.it.a_khmelev.lesson01;

import java.math.BigInteger;
import java.util.ArrayList;

public class FiboB {
    private long startTime = System.currentTimeMillis();

    public FiboB() {
    }

    private long time() {
        return System.currentTimeMillis() - this.startTime;
    }

    public static void main(String[] args) {
        FiboB fibo = new FiboB();
        int n = '\ud903';
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", Integer.valueOf(n), fibo.fastB(Integer.valueOf(n)), fibo.time());
    }

    BigInteger fastB(Integer n) {
        ArrayList<BigInteger> nums = new ArrayList(n);
        nums.add(BigInteger.ZERO);
        nums.add(BigInteger.ONE);

        for(int i = 2; i <= n; ++i) {
            BigInteger f = ((BigInteger)nums.get(i - 2)).add((BigInteger)nums.get(i - 1));
            nums.add(f);
        }

        return (BigInteger)nums.get(n);
    }
}
