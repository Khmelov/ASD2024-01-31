package by.it.group310971.a_kokhan.lesson02;
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
import java.util.ArrayList;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<Item> {
        int cost;
        int weight;
        int efficientCost;

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
            efficientCost = cost/weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            //тут может быть ваш компаратор
            return this.efficientCost-o.efficientCost;
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int itemNumber = input.nextInt();      //сколько предметов в файле
        int leftWeight = input.nextInt();      //какой вес у рюкзака
        int bufferInt;
        ArrayList<Item> efficiencyList = new ArrayList<>(itemNumber);

        for (int i = 0; i < itemNumber; i++) { //создавая каждый конструктором
            bufferInt = 0;
            var currentItem = new Item(input.nextInt(), input.nextInt());

            for (var element : efficiencyList){
                if (element.compareTo(currentItem) < 0)
                    break;
                bufferInt++;
            }
            efficiencyList.add(bufferInt, currentItem);
        }

        double result = 0;
        while (leftWeight > 0) {
            var currentItem = efficiencyList.getFirst();
            leftWeight -= currentItem.weight;
            if (leftWeight < 0){
                result += currentItem.efficientCost*(currentItem.weight + leftWeight);
                break;
            }
            result += currentItem.cost;
            efficiencyList.removeFirst();
        }
        input.close();
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root=System.getProperty("user.dir")+"/src/";
        File f=new File(root+"by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal=new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)",costFinal,finishTime - startTime);
    }
}