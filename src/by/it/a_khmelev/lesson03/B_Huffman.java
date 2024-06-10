package by.it.a_khmelev.lesson03;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();

        // Прочитаем данные из файла
        Scanner scanner = new Scanner(file);
        int count = scanner.nextInt(); // Количество различных букв
        int length = scanner.nextInt(); // Размер получившейся закодированной строки
        Map<String, String> map = new HashMap<>(); // Хранение кодов и соответствующих символов

        // Считываем коды букв и сохраняем их в карте
        for (int i = 0; i < count; i++) {
            String letter = scanner.next().substring(0, 1); // Получаем символ
            String code = scanner.next(); // Получаем код
            map.put(code, letter); // Добавляем в карту
        }

        // Считываем закодированную строку
        String encodedString = scanner.next();

        // Декодируем строку по коду Хаффмана
        StringBuilder currentCode = new StringBuilder(); // Хранение текущего кода
        for (int i = 0; i < length; i++) {
            currentCode.append(encodedString.charAt(i)); // Добавляем символ к текущему коду
            if (map.containsKey(currentCode.toString())) { // Если текущий код существует в карте
                result.append(map.get(currentCode.toString())); // Добавляем символ в результат
                currentCode = new StringBuilder(); // Сбрасываем текущий код
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/ASD2024-01-31/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }
}
