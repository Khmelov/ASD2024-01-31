package by.it.a_khmelev.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_EditDist {

    String getDistanceEditing(String one, String two) {
        // Матрица для хранения расстояний Левенштейна
        int[][] distance = new int[one.length() + 1][two.length() + 1];

        // Заполнение базовых случаев
        for (int i = 0; i <= one.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= two.length(); j++) {
            distance[0][j] = j;
        }

        // Вычисление расстояния Левенштейна
        for (int i = 1; i <= one.length(); i++) {
            for (int j = 1; j <= two.length(); j++) {
                int cost = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;
                distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1), distance[i - 1][j - 1] + cost);
            }
        }

        // Восстановление редакционного предписания
        StringBuilder result = new StringBuilder();
        int i = one.length(), j = two.length();
        while (i > 0 || j > 0) {
            if (i > 0 && distance[i][j] == distance[i - 1][j] + 1) {
                result.insert(0, "-" + one.charAt(i - 1) + ",");
                i--;
            } else if (j > 0 && distance[i][j] == distance[i][j - 1] + 1) {
                result.insert(0, "+" + two.charAt(j - 1) + ",");
                j--;
            } else {
                result.insert(0, "#" + one.charAt(i - 1) + ",");
                i--;
                j--;
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
    }
}
