package DorofeyMI_310971.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//Lesson 3. A_Huffman.
//Разработайте метод encode(File file) для кодирования строки (код Хаффмана)

// По данным файла (непустой строке ss длины не более 104104),
// состоящей из строчных букв латинского алфавита,
// постройте оптимальный по суммарной длине беспрефиксный код.

// Используйте Алгоритм Хаффмана — жадный алгоритм оптимального
// безпрефиксного кодирования алфавита с минимальной избыточностью.

// В первой строке выведите количество различных букв kk,
// встречающихся в строке, и размер получившейся закодированной строки.
// В следующих kk строках запишите коды букв в формате "letter: code".
// В последней строке выведите закодированную строку. Примеры ниже

//        Sample Input 1:
//        a
//
//        Sample Output 1:
//        1 1
//        a: 0
//        0

//        Sample Input 2:
//        abacabad
//
//        Sample Output 2:
//        4 14
//        a: 0
//        b: 10
//        c: 110
//        d: 111
//        01001100100111

public class A_Huffman {

    // Индекс кодов символов
    static private Map<Character, String> codes = new TreeMap<>();

    // Абстрактный класс элемента дерева
    abstract class Node implements Comparable<Node> {
        private final int frequency; // Частота символов

        // Конструктор по умолчанию
        private Node(int frequency) {
            this.frequency = frequency;
        }

        // Метод нужен для корректной работы узла в приоритетной очереди
        // или для сортировок
        @Override
        public int compareTo(Node o) {
            return Integer.compare(frequency, o.frequency);
        }

        // Генерация кодов (вызывается на корневом узле
        // один раз в конце, т.е. после построения дерева)
        abstract void fillCodes(String code);
    }

    // Расширение базового класса до внутреннего узла дерева
    private class InternalNode extends Node {
        // Внутренний узел дерева
        Node left;  // Левый ребенок бинарного дерева
        Node right; // Правый ребенок бинарного дерева

        // Для этого дерева не существует внутренних узлов без обоих детей
        // Поэтому такого конструктора будет достаточно
        InternalNode(Node left, Node right) {
            super(left.frequency + right.frequency);
            this.left = left;
            this.right = right;
        }

        @Override
        void fillCodes(String code) {
            left.fillCodes(code + "0");
            right.fillCodes(code + "1");
        }
    }

    // Расширение базового класса до листа дерева
    private class LeafNode extends Node {
        // Лист
        char symbol; // Символы хранятся только в листах

        LeafNode(int frequency, char symbol) {
            super(frequency);
            this.symbol = symbol;
        }

        @Override
        void fillCodes(String code) {
            // Добрались до листа, значит рекурсия закончена, код уже готов
            // И можно запомнить его в индексе для поиска кода по символу.
            codes.put(this.symbol, code);
        }
    }

    // Метод для кодирования строки по алгоритму Хаффмана
    String encode(File file) throws FileNotFoundException {
        // Прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        String s = scanner.next();
        scanner.close();

        // Подсчитаем частоту каждого символа в строке
        Map<Character, Integer> count = new HashMap<>();
        for (char ch : s.toCharArray()) {
            count.put(ch, count.getOrDefault(ch, 0) + 1);
        }

        // Создадим листы для каждого символа и добавим их в приоритетную очередь
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : count.entrySet()) {
            priorityQueue.add(new LeafNode(entry.getValue(), entry.getKey()));
        }

        // Построим дерево Хаффмана
        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            priorityQueue.add(new InternalNode(left, right));
        }

        // Получим корень дерева
        Node root = priorityQueue.poll();
        if (root != null) {
            root.fillCodes("");
        }

        // Создадим закодированную строку на основе индекса кодов
        StringBuilder encodedString = new StringBuilder();
        for (char ch : s.toCharArray()) {
            encodedString.append(codes.get(ch));
        }

        return encodedString.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "DorofeyMI_310971/lesson03/dataHuffman.txt");
        A_Huffman instance = new A_Huffman();
        long startTime = System.currentTimeMillis();
        String result = instance.encode(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("%d %d\n", codes.size(), result.length());
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
        System.out.println(result);
    }

}
