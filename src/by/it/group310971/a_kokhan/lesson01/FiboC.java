package by.it.group310971.a_kokhan.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.util.ArrayList;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 999999;
        int m = 321;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    //Решение сложно найти интуитивно
    //возможно потребуется дополнительный поиск информации
    //см. период Пизано
    private static final Long ZERO = 0L, ONE = 1L;
    long fasterC(long n, int m) {
    	ArrayList<Long> remainderList = new ArrayList<>();
    	remainderList.add(ZERO);
    	remainderList.add(ONE);
    	
    	Long penultimateRemainder = ZERO, lastRemainder = ONE;
    	do {
    		remainderList.add((penultimateRemainder + lastRemainder) % m);
       		lastRemainder = remainderList.getLast();
    		penultimateRemainder = remainderList.get(remainderList.size()-2);
    		if (lastRemainder.equals(ONE) && 
    				penultimateRemainder.equals(ZERO))
    			break;
    	} while (true);
        return remainderList.get((int) (n % (remainderList.size()-2)));
    }


}

