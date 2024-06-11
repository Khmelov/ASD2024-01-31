package by.it.a_khmelev.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_EditDist {

    int getDistanceEditing(String one, String two) {
        // Массив для хранения результатов вычислений расстояния Левенштейна для подстрок
        int[][] distance = new int[one.length() + 1][two.length() + 1];

        // Инициализация базовых случаев
        for (int i = 0; i <= one.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= two.length(); j++) {
            distance[0][j] = j;
        }

        // Вычисление расстояния Левенштейна для всех подстрок
        for (int i = 1; i <= one.length(); i++) {
            for (int j = 1; j <= two.length(); j++) {
                int cost = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;
                distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1), distance[i - 1][j - 1] + cost);
            }
        }

        // Возвращаем результат для полных строк
        return distance[one.length()][two.length()];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEditing(scanner.nextLine(), scanner.nextLine()));
    }
}
