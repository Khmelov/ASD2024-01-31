package by.it.group310971.a_kokhan.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить рекурсивный способ вычисления чисел Фибоначчи
 */

public class FiboA {


    private long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        FiboA fibo = new FiboA();
        int n = 33;
        System.out.printf("calc(%d)=%d \n\t time=%d \n\n", n, fibo.calc(n), fibo.time());

        //вычисление чисел фибоначчи медленным методом (рекурсией)
        fibo = new FiboA();
        n = 34;
        System.out.printf("slowA(%d)=%d \n\t time=%d \n\n", n, fibo.slowA(n), fibo.time());
    }

    private long time() {
        long res = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        return res;
    }
    
    //здесь простейший вариант, в котором код совпадает
    //с математическим определением чисел Фибоначчи
    //время O(2^n)
    private int calc(int n) {
    	if (!isValueSuitable(n))
    		return globalInt;
    	int currentFibonacciNumber = 1, previousFibonacciNumber = 0, numberBuffer;
    	while (globalInt > 0) {
    		globalInt--;
    		numberBuffer = currentFibonacciNumber;
    		currentFibonacciNumber += previousFibonacciNumber;
    		previousFibonacciNumber = numberBuffer;
    	}
        return currentFibonacciNumber;
    }

    //рекурсия
    //здесь нужно реализовать вариант без ограничения на размер числа,
    //в котором код совпадает с математическим определением чисел Фибоначчи
    //время O(2^n)
    BigInteger slowA(Integer n) {
    	if (!isValueSuitable(n))
    		return BigInteger.valueOf(globalInt);
        return getNextFibonacciNumberRecursively(BigInteger.ZERO, BigInteger.ONE, n);
    }
//    BigInteger slowA(Integer n) { // Это тот алгоритм, который соответствует условию задачи, но он вызывает переполнение стека, поэтому я написал другой
//    	if (n == 1)
//    		return BigInteger.valueOf(n);
//        return slowA(n-2).add(slowA(n-1));
//    }
    
    private int globalInt;
    private boolean isValueSuitable(Integer value) {
    	boolean returnValue = true;
    	if (value < 3) {
        	if (value < 1)
        		throw new IllegalArgumentException("Only 1 or greater input allowed");
        	returnValue = false;
    	}
    	globalInt = value - 1;
    	return returnValue;
    }

    private BigInteger getNextFibonacciNumberRecursively(BigInteger currentFibonacciNumber, BigInteger previousFibonacciNumber, int numberOfIterationLeft) {
    	if (numberOfIterationLeft > 0) {
    		numberOfIterationLeft--;
    		currentFibonacciNumber = getNextFibonacciNumberRecursively(currentFibonacciNumber.add(previousFibonacciNumber), currentFibonacciNumber, numberOfIterationLeft);
    	}
    	return currentFibonacciNumber;
    }
}

