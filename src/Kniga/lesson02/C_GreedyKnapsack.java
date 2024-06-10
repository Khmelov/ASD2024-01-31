package Kniga.lesson02;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<Item> {
        int cost;
        int weight;

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
        }

        @Override
        public int compareTo(Item o) {
            double ratio1 = (double) this.cost / this.weight;
            double ratio2 = (double) o.cost / o.weight;
            // Сортировка в порядке убывания отношения стоимости к весу
            return Double.compare(ratio2, ratio1);
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      // Количество предметов в файле
        int W = input.nextInt();      // Вес рюкзака
        Item[] items = new Item[n];   // Список предметов

        // Заполнение массива предметов
        for (int i = 0; i < n; i++) {
            items[i] = new Item(input.nextInt(), input.nextInt());
        }

        // Сортировка предметов
        Arrays.sort(items);

        System.out.println("Отсортированный список предметов:");
        for (Item item : items) {
            System.out.println(item);
        }

        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        double totalCost = 0;
        int currentWeight = 0; // Текущий вес рюкзака

        // Заполнение рюкзака
        for (Item item : items) {
            if (currentWeight + item.weight <= W) { // Если предмет полностью помещается
                totalCost += item.cost;
                currentWeight += item.weight;
            } else { // Если предмет не помещается полностью
                double fraction = (double) (W - currentWeight) / item.weight;
                totalCost += item.cost * fraction;
                break; // Рюкзак заполнен
            }
        }

        System.out.printf("Удалось собрать рюкзак на сумму %.2f\n", totalCost);
        return totalCost;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        File f = new File("D:/Aisd/ASD2024-01-31/src/by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}