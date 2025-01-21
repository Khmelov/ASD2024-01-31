package by.it.group310971.stankevich.lesson14;

import java.util.Scanner;

/**
 * Задача "Ханойские башни" с дополнительной группировкой состояний по DSU.
 * Класс StatesHanoiTowerC.
 */
public class StatesHanoiTowerC {

    // --------------------------------
    // Глобальные поля для стержней (A,B,C) и записи состояний
    // --------------------------------

    // Максимальное количество дисков задаёт максимум шагов = 2^N - 1
    // Для N=10 будет 1023 шагов, что укладывается в разумные пределы.
    // Если N больше, это может стать слишком большим, но условие задачи
    // это не оговаривает.
    private static int N; // число дисков

    // Массивы для стержней A, B, C (каждый размером N).
    // topA, topB, topC - индексы верхнего диска ( -1 если стержень пуст)
    private static int[] A, B, C;
    private static int topA, topB, topC;

    // Все промежуточные состояния (кроме стартового). Их ровно 2^N - 1.
    // Каждый элемент states[i] = { sizeA, sizeB, sizeC }
    // где sizeX = (topX + 1) - текущая высота пирамиды на стержне X.
    private static int[][] states;
    // Счётчик записанных состояний
    private static int stepCount;

    // DSU массивы
    private static int[] parent; // родитель i-го состояния
    private static int[] size;   // размер поддерева (для union by size)

    // Для каждой записи i храним largestHeight[i] = max(A.size, B.size, C.size).
    private static int[] largestHeight;
    // Храним представителя для каждого возможного maxHeight (от 1 до N)
    // Если repForHeight[h] = -1, значит ещё нет представителя для высоты h.
    private static int[] repForHeight;

    // --------------------------------
    // МЕТОД MAIN
    // --------------------------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        // Инициализация стержней.
        A = new int[N];
        B = new int[N];
        C = new int[N];

        // Заполним стержень A дисками (N сверху - 1 снизу, либо наоборот).
        // Традиционно диск 1 -- самый маленький, диск N -- самый большой.
        // Но нам удобнее "толкать" и "выталкивать" с конца массива,
        // значит topA = N-1 => в A[topA] лежит диск 1 (самый маленький).
        // Однако обычно в классике: диск 1 сверху, диск 2 под ним и т.д.
        // Сделаем так: A[0] = N (большой), A[1] = N-1, ... A[N-1] = 1
        // но "верх" массива будет index = N-1.

        for (int i = 0; i < N; i++) {
            A[i] = N - i;
        }
        topA = N - 1; // последний индекс = верхний диск ( = 1)
        topB = -1;
        topC = -1;

        // Массив состояний (2^N - 1)
        int totalStates = (1 << N) - 1; // 2^N - 1
        states = new int[totalStates][3];
        stepCount = 0;

        // Рекурсивно совершаем ходы
        moveHanoi(N, 'A', 'B', 'C');

        // Теперь у нас записано ровно 2^N - 1 состояний (stepCount = totalStates).
        // largestHeight[i] для каждого i
        largestHeight = new int[totalStates];
        for (int i = 0; i < totalStates; i++) {
            int sA = states[i][0];
            int sB = states[i][1];
            int sC = states[i][2];
            int mh = sA;
            if (sB > mh) mh = sB;
            if (sC > mh) mh = sC;
            largestHeight[i] = mh;
        }

        // DSU инициализация
        parent = new int[totalStates];
        size   = new int[totalStates];
        for (int i = 0; i < totalStates; i++) {
            parent[i] = i;
            size[i]   = 1; // начальный размер поддерева
        }

        // repForHeight[h] = -1 значит нет представителя для высоты h
        repForHeight = new int[N + 1]; // высоты могут быть от 1 до N
        for (int h = 0; h <= N; h++) {
            repForHeight[h] = -1;
        }

        // Для каждого состояния объединяем в DSU по largestHeight
        for (int i = 0; i < totalStates; i++) {
            int h = largestHeight[i];
            if (repForHeight[h] == -1) {
                repForHeight[h] = i; // i - представитель для высоты h
            } else {
                union(i, repForHeight[h]);
            }
        }

        // Подсчитаем размеры финальных множеств
        // count[root] = сколько вершин (состояний) в поддереве root
        int[] count = new int[totalStates];
        for (int i = 0; i < totalStates; i++) {
            int r = find(i);
            count[r]++;
        }

        // Посчитаем сколько непустых множеств
        int nonEmptyCount = 0;
        for (int i = 0; i < totalStates; i++) {
            if (count[i] > 0) nonEmptyCount++;
        }

        // Соберём их размеры
        int[] sizesArray = new int[nonEmptyCount];
        int idx = 0;
        for (int i = 0; i < totalStates; i++) {
            if (count[i] > 0) {
                sizesArray[idx++] = count[i];
            }
        }

        // Отсортируем по возрастанию (пузырьком или любым другим способом без Collections)
        bubbleSort(sizesArray);

        // Выведем
        for (int i = 0; i < sizesArray.length; i++) {
            System.out.print(sizesArray[i]);
            if (i < sizesArray.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    // --------------------------------
    // РЕКУРСИВНЫЙ АЛГОРИТМ ХАНОЙСКИХ БАШЕН
    // --------------------------------
    private static void moveHanoi(int n, char from, char to, char aux) {
        if (n == 1) {
            moveTopDisk(from, to);
        } else {
            // Переносим n-1 дисков A->C (B - вспомогательный)
            moveHanoi(n - 1, from, aux, to);
            // Переносим последний диск A->B
            moveTopDisk(from, to);
            // Переносим n-1 дисков C->B (A - вспомогательный)
            moveHanoi(n - 1, aux, to, from);
        }
    }

    // Перенести верхний диск со стержня 'from' на стержень 'to'
    // и записать текущее состояние (A,B,C) в states[stepCount]
    private static void moveTopDisk(char from, char to) {
        int disk = 0;
        switch (from) {
            case 'A':
                disk = A[topA];
                topA--;
                break;
            case 'B':
                disk = B[topB];
                topB--;
                break;
            case 'C':
                disk = C[topC];
                topC--;
                break;
        }
        // Кладём диск на другой стержень
        switch (to) {
            case 'A':
                topA++;
                A[topA] = disk;
                break;
            case 'B':
                topB++;
                B[topB] = disk;
                break;
            case 'C':
                topC++;
                C[topC] = disk;
                break;
        }
        // Запись состояния (размеры каждого стержня)
        states[stepCount][0] = topA + 1;  // размер A
        states[stepCount][1] = topB + 1;  // размер B
        states[stepCount][2] = topC + 1;  // размер C
        stepCount++;
    }

    // --------------------------------
    // DSU (Union-Find)
    // --------------------------------

    // Найти корень (сжатие пути)
    private static int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Объединить множества (по размеру поддерева)
    private static void union(int x, int y) {
        int rx = find(x);
        int ry = find(y);
        if (rx != ry) {
            // union by size
            if (size[rx] < size[ry]) {
                parent[rx] = ry;
                size[ry] += size[rx];
            } else {
                parent[ry] = rx;
                size[rx] += size[ry];
            }
        }
    }

    // --------------------------------
    // Пузырьковая сортировка массива по возрастанию
    // --------------------------------
    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // обмен
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }
}
