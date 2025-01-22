package by.it.group310971.fedorenko.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {
    static class InversionCounter extends RecursiveTask<Integer> {
        private final int[] a;
        private final int start;
        private final int end;

        public InversionCounter(int[] a, int start, int end) {
            this.a = a;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (start >= end) {
                return 0;
            }

            int mid = start + (end - start) / 2;

            InversionCounter left = new InversionCounter(a, start, mid);
            InversionCounter right = new InversionCounter(a, mid + 1, end);

            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();

            int mergeResult = mergeAndCountSplitInversions(a, start, mid, end);

            return mergeResult + leftResult + rightResult;
        }

        private int mergeAndCountSplitInversions(int[] a, int start, int mid, int end) {
            int[] temp = new int[a.length];
            System.arraycopy(a, 0, temp, 0, a.length);

            int i = start;
            int j = mid + 1;
            int k = start;
            int splitInversions = 0;

            while (i <= mid && j <= end) {
                if (temp[i] <= temp[j]) {
                    a[k++] = temp[i++];
                } else {
                    a[k++] = temp[j++];
                    splitInversions += mid - i + 1;
                }
            }

            while (i <= mid) {
                a[k++] = temp[i++];
            }

            while (j <= end) {
                a[k++] = temp[j++];
            }

            return splitInversions;
        }
    }

    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        // ForkJoinPool для запуска вычислений в нескольких потоках
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        InversionCounter inversionCounter = new InversionCounter(a, 0, n - 1);
        return forkJoinPool.invoke(inversionCounter);
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "fedorenko/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
