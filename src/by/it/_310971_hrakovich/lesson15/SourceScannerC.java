package by.it._310971_hrakovich.lesson15;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.LinkOption;

public class SourceScannerC {


    // Метод для чтения всех .java файлов из каталогаго подкаталогов
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        List<ProcessedFile> processedFiles = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(src), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".java")) {
                        String content = readFile(file);
                        if (!isTestFile(content)) {
                            String processedContent = processFileContent(content);
                            processedFiles.add(new ProcessedFile(file.toString(), processedContent));
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Найти копии по метрике Левенштейна
        findAndPrintCopies(processedFiles);
    }

    // Чтение содержимого файла с обработкой MalformedInputException
    private static String readFile(Path file) {
        StringBuilder content = new StringBuilder();
        try {
            Files.lines(file, StandardCharsets.UTF_8).forEach(line -> content.append(line).append("\n"));
        } catch (MalformedInputException e) {
            System.err.println("Неверный формат файла: " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // Проверка, является ли файл тестом
    private static boolean isTestFile(String content) {
        return content.contains("@Test") || content.contains("org.junit.Test");
    }

    // Обработка содержимого файла
    private static String processFileContent(String content) {
        // Удаление строки package
        content = content.replaceAll("(?i)^\\s*package\\s+.*;?\\s*", "");
        // Удаление импортов
        content = content.replaceAll("(?i)^\\s*import\\s+.*;?\\s*", "");
        // Удаление комментариев
        content = content.replaceAll("(//.*?$)|(/\\*.*?\\*/)", "");
        // Замена символов < 33 на пробел
        content = content.replaceAll("[\\x00-\\x1F]", " ");
        // Удаляем лишние пробелы и trim
        return content.trim();
    }

    // Класс для хранения пути файла и его контента
    static class ProcessedFile {
        String path;
        String content;

        ProcessedFile(String path, String content) {
            this.path = path;
            this.content = content;
        }
    }

    // Вычисление расстояния Левенштейна между двумя строками
    public static int levenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j; // удаление
                } else if (j == 0) {
                    dp[i][j] = i; // добавление
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1,
                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1)));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    // Поиск и вывод копий файлов
    private static void findAndPrintCopies(List<ProcessedFile> processedFiles) {
        Map<String, List<String>> copiesMap = new HashMap<>();

        for (int i = 0; i < processedFiles.size(); i++) {
            for (int j = i + 1; j < processedFiles.size(); j++) {
                int distance = levenshtein(processedFiles.get(i).content, processedFiles.get(j).content);
                if (distance < 10) { // найти копии
                    copiesMap.computeIfAbsent(processedFiles.get(i).path, k -> new ArrayList<>()).add(processedFiles.get(j).path);
                    copiesMap.computeIfAbsent(processedFiles.get(j).path, k -> new ArrayList<>()).add(processedFiles.get(i).path);
                }
            }
        }

        // Сортируем и выводим результаты
        copiesMap.forEach((filePath, copies) -> {
            System.out.println(filePath);
            Collections.sort(copies);
            for (String copy : copies) {
                System.out.println(copy);
            }
        });
    }
}
