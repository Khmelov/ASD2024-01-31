package by.it.group310971.Isaichikova.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_BinaryFind {
    int[] findIndex(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int[] a=new int[n];
        for (int i = 1; i <= n; i++) {
            a[i-1] = scanner.nextInt();
        }

        int k = scanner.nextInt();
        int[] result=new int[k];
        for (int i = 0; i < k; i++) {
            int value = scanner.nextInt();
            int left = 0;
            int right = n - 1;
            while (left <= right) {
                int middle = (left + right) / 2;
                if (a[middle] == value) {
                    result[i] = middle + 1;
                    break;
                } else if (a[middle] < value) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
            if (left > right) { // не нашли
                result[i] = -1;
            }
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Isaichikova/lesson04/dataA.txt");
        A_BinaryFind instance = new A_BinaryFind();
        int[] result=instance.findIndex(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }
}
