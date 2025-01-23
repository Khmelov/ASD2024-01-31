package by.it.group310971.Isaichikova.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_MergeSort {

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        int n = scanner.nextInt();
        int[] a=new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }
        mergeSort(a, 0, n - 1);
        return a;
    }

    private void mergeSort(int[] a, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(a, left, middle);
            mergeSort(a, middle + 1, right);
            merge(a, left, middle, right);
        }
    }

    private void merge(int[] a, int left, int middle, int right) {
        int[] leftArray = new int[middle - left + 1];
        int[] rightArray = new int[right - middle];

        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = a[left + i];
        }
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = a[middle + 1 + i];
        }

        int i = 0, j = 0, k = left;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                a[k] = leftArray[i];
                i++;
            } else {
                a[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < leftArray.length) {
            a[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < rightArray.length) {
            a[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Isaichikova/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        int[] result=instance.getMergeSort(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }
}
