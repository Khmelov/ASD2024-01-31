package DorofeyMI_310971.lesson15;

import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SourceScannerC {
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        List<File> javaFiles = new ArrayList<>();
        try {
            Files.walk(Paths.get(src))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> javaFiles.add(path.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<File, String> fileContents = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (File file : javaFiles) {
            System.out.println("Обработка файла: " + file.getPath()); // Отладочное сообщение
            executor.submit(() -> {
                try {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                        content = processContent(content);
                        fileContents.put(file, content);
                    }
                } catch (MalformedInputException e) {
                    System.err.println("MalformedInputException: " + file.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        findAndPrintDuplicates(fileContents);
    }

    private static String processContent(String content) {
        content = content.replaceAll("(?s)/\\*.*?\\*/|//.*(?=[\\n\\r])", ""); // Удаление комментариев
        content = content.replaceAll("package\\s+.*?;", ""); // Удаление строки package
        content = content.replaceAll("import\\s+.*?;", ""); // Удаление всех импортов
        content = content.replaceAll("[\\x00-\\x20]+", " "); // Замена символов с кодом <33 на пробел
        return content.trim();
    }

    private static void findAndPrintDuplicates(Map<File, String> fileContents) {
        List<Map.Entry<File, String>> entries = new ArrayList<>(fileContents.entrySet());
        entries.sort(Comparator.comparing(entry -> entry.getKey().getPath()));

        for (int i = 0; i < entries.size(); i++) {
            File file1 = entries.get(i).getKey();
            String content1 = entries.get(i).getValue();
            List<File> duplicates = new ArrayList<>();
            for (int j = i + 1; j < entries.size(); j++) {
                File file2 = entries.get(j).getKey();
                String content2 = entries.get(j).getValue();
                if (levenshteinDistance(content1, content2) < 10) {
                    duplicates.add(file2);
                }
            }
            if (!duplicates.isEmpty()) {
                System.out.println(file1.getPath());
                duplicates.forEach(duplicate -> System.out.println("  " + duplicate.getPath()));
            }
        }
    }

    private static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
