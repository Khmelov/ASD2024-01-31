package by.it.a_khmelev.lesson02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<Item> {
        int cost;
        int weight;
        double valuePerWeight;

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
            this.valuePerWeight = (double) cost / weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    ", valuePerWeight=" + valuePerWeight +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            return Double.compare(o.valuePerWeight, this.valuePerWeight); // Сортировка по убыванию стоимости за единицу веса
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      // сколько предметов в файле
        int W = input.nextInt();      // какой вес у рюкзака
        Item[] items = new Item[n];   // получим список предметов
        for (int i = 0; i < n; i++) { // создавая каждый конструктором
            items[i] = new Item(input.nextInt(), input.nextInt());
        }
        // покажем предметы
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        // Сортировка предметов по стоимости единицы веса
        Arrays.sort(items);

        // Итогом является максимально возможная стоимость вещей в рюкзаке
        // вещи можно резать на кусочки (непрерывный рюкзак)
        double result = 0;
        int currentWeight = 0;

        for (Item item : items) {
            if (currentWeight + item.weight <= W) {
                // Если предмет помещается целиком, добавляем его
                result += item.cost;
                currentWeight += item.weight;
            } else {
                // Если предмет не помещается целиком, добавляем его частично
                int remainingWeight = W - currentWeight;
                result += item.valuePerWeight * remainingWeight;
                break;
            }
        }

        System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}
