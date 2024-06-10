 package Kniga.lesson01;



/*
 * Вам необходимо выполнить рекурсивный способ вычисления чисел Фибоначчи
 */

import java.math.BigInteger;

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

     private int calc(int n) {
         // Простейший рекурсивный метод для вычисления чисел Фибоначчи
         if (n <= 1) {
             return n;
         } else {
             return calc(n - 1) + calc(n - 2);
         }
     }


     BigInteger slowA(Integer n) {
         // Рекурсивный метод с использованием BigInteger для вычисления чисел Фибоначчи
         if (n == 0) {
             return BigInteger.ZERO;
         } else if (n == 1) {
             return BigInteger.ONE;
         } else {
             return slowA(n - 1).add(slowA(n - 2));
         }
     }
 }


