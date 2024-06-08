package by.it.group310971.Isaichikova.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result=new StringBuilder();
        Scanner scanner = new Scanner(file);
        Integer count = scanner.nextInt();
        Integer length = scanner.nextInt();
        Map<String, Character> codeToSymbol = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String letter = scanner.next();
            String code = scanner.next();
            codeToSymbol.put(code, letter.charAt(0));
        }
        String encodedString = scanner.next();
        int currentPosition = 0;
        while (currentPosition < encodedString.length()) {
            for (Map.Entry<String, Character> entry : codeToSymbol.entrySet()) {
                if (encodedString.startsWith(entry.getKey(), currentPosition)) {
                    result.append(entry.getValue());
                    currentPosition += entry.getKey().length();
                    break;
                }
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group310971/Isaichikova/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }
}
