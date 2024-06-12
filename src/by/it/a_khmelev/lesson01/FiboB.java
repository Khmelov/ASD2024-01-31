package by.it.a_khmelev.lesson01;

import java.math.BigInteger;
import java.util.ArrayList;

public class FiboB {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {

        // вычисление чисел простым быстрым методом
        FiboB fibo = new FiboB();
        int n = 5;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    BigInteger fastB(Integer n) {
        // Инициализируем массив для хранения чисел Фибоначчи
        ArrayList<BigInteger> nums = new ArrayList<>(n + 1);
        nums.add(BigInteger.ZERO);  // F(0) = 0
        nums.add(BigInteger.ONE);   // F(1) = 1

        // Вычисляем числа Фибоначчи до n с помощью цикла
        for (int i = 2; i <= n; i++) {
            BigInteger f = nums.get(i - 2).add(nums.get(i - 1));
            nums.add(f);
        }

        // Возвращаем F(n)
        return nums.get(n);
    }
}
