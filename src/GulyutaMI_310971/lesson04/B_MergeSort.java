package GulyutaMI_310971.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Реализуйте сортировку слиянием для одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо отсортировать полученный массив.

Sample Input:
5
2 3 9 2 9
Sample Output:
2 2 3 9 9
*/
public class B_MergeSort {

    // Метод для слияния двух подмассивов
    void merge(int[] array, int left, int middle, int right) {
        // Находим размеры подмассивов
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Создаем временные массивы
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Копируем данные во временные массивы
        for (int i = 0; i < n1; ++i)
            L[i] = array[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = array[middle + 1 + j];

        // Сливаем временные массивы

        // Индексы первого и второго подмассивов
        int i = 0, j = 0;

        // Индекс объединенного подмассива
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }

        // Копируем оставшиеся элементы L[], если они есть
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        // Копируем оставшиеся элементы R[], если они есть
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }
    // Основной метод, который сортирует array[l..r] с помощью merge()
    void sort(int[] array, int left, int right) {
        if (left < right) {
            // Находим середину
            int middle = left + (right - left) / 2;

            // Сортируем обе половины
            sort(array, left, middle);
            sort(array, middle + 1, right);

            // Сливаем отсортированные половины
            merge(array, left, middle, right);
        }
    }
    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a=new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }
        sort(a, 0, a.length - 1);
        // тут ваше решение (реализуйте сортировку слиянием)
        // https://ru.wikipedia.org/wiki/Сортировка_слиянием






        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "GulyutaMI_310971/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
