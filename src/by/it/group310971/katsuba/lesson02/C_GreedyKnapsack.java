package by.it.group310971.katsuba.lesson02;

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
            // Сортировка по убыванию стоимости за единицу веса
            return Double.compare(o.valuePerWeight, this.valuePerWeight);
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

        // Сортируем предметы по убыванию стоимости за единицу веса
        Arrays.sort(items);

        double result = 0;
        int remainingWeight = W;

        for (Item item : items) {
            if (remainingWeight == 0) {
                break;
            }
            if (item.weight <= remainingWeight) {
                // Если весь предмет помещается в рюкзак
                result += item.cost;
                remainingWeight -= item.weight;
            } else {
                // Если только часть предмета помещается в рюкзак
                result += item.valuePerWeight * remainingWeight;
                remainingWeight = 0;
            }
        }

        System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "katsuba/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d ms)", costFinal, finishTime - startTime);
    }
}
