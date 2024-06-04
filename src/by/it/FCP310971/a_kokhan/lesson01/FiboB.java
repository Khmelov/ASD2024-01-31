package by.it.FCP310971.a_kokhan.lesson01;

import java.math.BigInteger;
import java.util.HashMap;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

public class FiboB {
    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }
    
    public static void main(String[] args) {
        //вычисление чисел простым быстрым методом
        FiboB fibo = new FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

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
    //здесь нужно реализовать вариант с временем O(n) и памятью O(n)

    private boolean isNumberEven;
    private BigInteger[] lastFibonacciNumberArray;
    
    BigInteger fastB(Integer n) {
    	HashMap<Boolean, Integer> convertMap = new HashMap<>();
    	convertMap.put(true, 1);
    	convertMap.put(false, 0);
    	
    	if (!isValueSuitable(n))
    		return BigInteger.valueOf(globalInt);
    	isNumberEven = true;
    	lastFibonacciNumberArray = new BigInteger[] {BigInteger.ZERO, BigInteger.ONE};
    	while (globalInt > 0) {
    		globalInt--;
    		isNumberEven = !isNumberEven;
    		lastFibonacciNumberArray[convertMap.get(isNumberEven)] = lastFibonacciNumberArray[convertMap.get(isNumberEven)].add(lastFibonacciNumberArray[convertMap.get(!isNumberEven)]);
    	}
        return lastFibonacciNumberArray[convertMap.get(isNumberEven)];
    }
}

