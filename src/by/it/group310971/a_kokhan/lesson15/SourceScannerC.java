package by.it.group310971.a_kokhan.lesson15;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.nio.file.attribute.BasicFileAttributes;

public class SourceScannerC {
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
        findAndPrintCopies(processedFiles);
    }

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

    private static boolean isTestFile(String content) {
        return content.contains("@Test") || content.contains("org.junit.Test");
    }

    private static String processFileContent(String content) {
        content = content.replaceAll("(?i)^\\s*package\\s+.*;?\\s*", "");
        content = content.replaceAll("(?i)^\\s*import\\s+.*;?\\s*", "");
        content = content.replaceAll("(//.*?$)|(/\\*.*?\\*/)", "");
        content = content.replaceAll("[\\x00-\\x1F]", " ");
        return content.trim();
    }

    static class ProcessedFile {
        String path;
        String content;

        ProcessedFile(String path, String content) {
            this.path = path;
            this.content = content;
        }
    }

    public static int levenshtein(String s1, String s2) {
        var dp = new int[s1.length() + 1][s2.length() + 1];
        for (var i = 0; i <= s1.length(); i++) {
            for (var j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1,
                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1)));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    private static void findAndPrintCopies(List<ProcessedFile> processedFiles) {
        Map<String, List<String>> copiesMap = new HashMap<>();

        for (int i = 0; i < processedFiles.size(); i++) {
            for (int j = i + 1; j < processedFiles.size(); j++) {
                int distance = levenshtein(processedFiles.get(i).content, processedFiles.get(j).content);
                if (distance < 1000) {
                    copiesMap.computeIfAbsent(processedFiles.get(i).path, k -> new ArrayList<>()).add(processedFiles.get(j).path);
                    copiesMap.computeIfAbsent(processedFiles.get(j).path, k -> new ArrayList<>()).add(processedFiles.get(i).path);
                }
                System.out.println(i + " " + j);
            }
        }

        copiesMap.forEach((filePath, copies) -> {
            System.out.println(filePath);
            Collections.sort(copies);
            for (String copy : copies) {
                System.out.println(copy);
            }
        });
    }
}

