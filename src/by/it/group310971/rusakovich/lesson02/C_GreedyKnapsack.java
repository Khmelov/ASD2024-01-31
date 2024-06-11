package by.it.group310971.rusakovich.lesson02;

/*
Даны
1) объем рюкзака 4
2) число возможных предметов 60
3) сам набор предметов
    100 50
    120 30
    100 50
Все это указано в файле (by/it/a_khmelev/lesson02/greedyKnapsack.txt)
Необходимо собрать наиболее дорогой вариант рюкзака для этого объема
Предметы можно резать на кусочки (т.е. алгоритм будет жадным)
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
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
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    '}';
        }

        // Comparator to sort items by value-to-weight ratio in descending order
        @Override
        public int compareTo(Item o) {
            double thisRatio = (double)this.cost / this.weight;
            double otherRatio = (double)o.cost / o.weight;
            return Double.compare(otherRatio, thisRatio);
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      // Number of items
        int W = input.nextInt();      // Knapsack capacity
        Item[] items = new Item[n];   // Array to store the items
        for (int i = 0; i < n; i++) { // Create the items
            items[i] = new Item(input.nextInt(), input.nextInt());
        }

        // Sort the items by value-to-weight ratio in descending order
        Arrays.sort(items);

        double result = 0;
        int currentWeight = 0;

        // Iterate through the sorted items and add them to the knapsack
        for (Item item : items) {
            if (currentWeight + item.weight <= W) {
                // If the item fits in the knapsack, add the entire item
                result += item.cost;
                currentWeight += item.weight;
            } else {
                // If the item doesn't fit, add a fraction of the item
                double remainingCapacity = W - currentWeight;
                double fraction = remainingCapacity / item.weight;
                result += item.cost * fraction;
                currentWeight = W;
            }

            // If the knapsack is full, stop adding items
            if (currentWeight == W) {
                break;
            }
        }

        System.out.printf("Total value: %f\n", result);
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group310971/rusakovich/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}