package by.it.group310971.a_kokhan.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class SourceScannerB {
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        System.out.println(src.toString());
        try {
            var textList = new ArrayList<Pair>();
            for (var filePath : findAllFiles(src)) {
                var content = new String(Files.readAllBytes(filePath));
                var index = content.indexOf("Test");
                try {
                    if (content.charAt(index - 1) == '@' || 
                        content.substring(index - 10, index - 1).equals("org.junit")){
                        continue;
                    }
                } catch (Exception e) {}
                var sb = new StringBuilder(content.substring(content.lastIndexOf('\n', content.indexOf("class"))));
                removeComments(sb);
                var i = 0;
                var length = sb.length()-1;
                while (i < length) {
                    if (sb.charAt(i) < 33) {
                        sb.deleteCharAt(i);
                        length--;
                    } else {
                        i++;
                    }
                }

                textList.add(new Pair(sb.length(), filePath.toString().substring(src.length())));
            }
            textList.sort(Comparator.comparingInt(pair -> pair.value));
            for (Pair pair : textList) {
                System.out.println(pair.toString());
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void removeComments(StringBuilder sb) {
        int i = 0;
        int n = sb.length();
        boolean inBlockComment = false; // Флаг для многострочных комментариев
        boolean inLineComment = false;  // Флаг для однострочных комментариев
        boolean inString = false;       // Флаг для строковых литералов

        while (i < n) {
            if (!inBlockComment && !inLineComment && sb.charAt(i) == '"') {
                inString = !inString; // Переключаем флаг строкового литерала
                i++;
            } else if (!inString && !inBlockComment && i + 1 < n && sb.charAt(i) == '/' && sb.charAt(i + 1) == '/') {
                inLineComment = true; // Начало однострочного комментария
                sb.delete(i, n); // Удаляем все символы до конца строки
                n = sb.length(); // Обновляем длину StringBuilder
            } else if (!inString && !inLineComment && i + 1 < n && sb.charAt(i) == '/' && sb.charAt(i + 1) == '*') {
                inBlockComment = true; // Начало многострочного комментария
                i += 2;
            } else if (inLineComment && sb.charAt(i) == '\n') {
                inLineComment = false; // Конец однострочного комментария
                i++;
            } else if (inBlockComment && i + 1 < n && sb.charAt(i) == '*' && sb.charAt(i + 1) == '/') {
                inBlockComment = false; // Конец многострочного комментария
                i += 2;
            } else if (inBlockComment || inLineComment) {
                sb.deleteCharAt(i); // Удаляем символы внутри комментариев
                n = sb.length(); // Обновляем длину StringBuilder
            } else {
                i++; // Пропускаем символы вне комментариев
            }
        }
    }

    public static ArrayList<Path> findAllFiles(String directory) {
        var fileList = new ArrayList<Path>();
        Path dirPath = Paths.get(directory);

        try {
            Files.walk(dirPath)
                 .filter(Files::isRegularFile).filter(file -> file.toString().endsWith(".java"))
                 .forEach(fileList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileList;
    }

    public static class Pair{
        int value;
        String path;

        public Pair(int value, String path) {
            this.value = value;
            this.path = path;
        }

        @Override
        public String toString() {
            return ".\\" + path + " " + value*2;
        }
    }
}
