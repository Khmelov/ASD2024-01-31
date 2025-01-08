package by.it.group310971.Isaichikova.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        int n = one.length() + 1;
        int m = two.length() + 1;
        int[][] d = new int[n][m];
        int[][] path = new int[n][m];
        for (int i = 0; i < n; i++) {
            d[i][0] = i;
        }
        for (int j = 0; j < m; j++) {
            d[0][j] = j;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    d[i][j] = d[i - 1][j - 1];
                    path[i][j] = 1;
                } else {
                    int min = Math.min(d[i - 1][j - 1], Math.min(d[i - 1][j], d[i][j - 1]));
                    if (min == d[i - 1][j - 1]) {
                        path[i][j] = 1;
                    } else if (min == d[i - 1][j]) {
                        path[i][j] = 2;
                    } else {
                        path[i][j] = 3;
                    }
                    d[i][j] = min + 1;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        int i = n - 1;
        int j = m - 1;
        while (i > 0 && j > 0) {
            char operation = '#';
            char symbol = ' ';
            switch (path[i][j]) {
                case 1:
                    operation = '#';
                    symbol = one.charAt(i - 1);
                    i--;
                    j--;
                    break;
                case 2:
                    operation = '-';
                    symbol = one.charAt(i - 1);
                    i--;
                    break;
                case 3:
                    if (d[i][j - 1] < d[i - 1][j]) {
                        operation = '+';
                        symbol = two.charAt(j - 1);
                        j--;
                    } else {
                        operation = '~';
                        symbol = two.charAt(j - 1);
                        i--;
                    }
                    break;
            }
            result.append(operation).append(symbol).append(',');
        }
        return result.toString();
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310971/Isaichikova/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}
