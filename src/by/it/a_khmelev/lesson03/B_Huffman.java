package by.it.a_khmelev.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(file);
        int count = scanner.nextInt();
        int length = scanner.nextInt();
        Map<String, Character> codeMap = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String letter = scanner.next().replaceAll(":", "");
            String code = scanner.next();
            codeMap.put(code, letter.charAt(0));
        }
        StringBuilder code = new StringBuilder();
        while (scanner.hasNext()) {
            code.append(scanner.next());
        }
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            temp.append(code.charAt(i));
            if (codeMap.containsKey(temp.toString())) {
                result.append(codeMap.get(temp.toString()));
                temp.setLength(0);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }
}
