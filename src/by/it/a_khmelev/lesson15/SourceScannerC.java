package by.it.a_khmelev.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SourceScannerC  {

    public static void main(String[] args) {
        // ��������� ���� � �������� src
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        Path srcPath = Paths.get(src);

        // ������ ��� �������� ������� � ����� ������
        List<FileData> fileDataList = new ArrayList<>();

        try {
            // ����������� ����� ���� ������ .java
            Files.walk(srcPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            // ������ ���������� �����
                            String content = Files.readString(path, StandardCharsets.UTF_8);

                            // ���������� �����, ���������� @Test ��� org.junit.Test
                            if (content.contains("@Test") || content.contains("org.junit.Test")) {
                                return;
                            }

                            // ������������ ����� �����
                            String processedContent = processContent(content);

                            // ��������� ������������ ����� � ���� � ������
                            String relativePath = srcPath.relativize(path).toString();
                            fileDataList.add(new FileData(relativePath, processedContent));
                        } catch (MalformedInputException e) {
                            // ���������� ������ ������
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ������� ���� ������ � ����������� ����������� < 10
        Map<String, List<String>> duplicates = findDuplicates(fileDataList);

        // ��������� � ������� ����������
        List<String> keys = new ArrayList<>(duplicates.keySet());
        Collections.sort(keys);

        for (String key : keys) {
            System.out.println(key);
            List<String> copies = duplicates.get(key);
            copies.sort(Comparator.naturalOrder());
            for (String copy : copies) {
                System.out.println(copy);
            }
        }
    }

    // ������������ ���������� �����
    private static String processContent(String content) {
        // ������� ������ package � ��� �������
        String[] lines = content.split("\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.startsWith("package") && !trimmed.startsWith("import")) {
                result.append(line).append("\n");
            }
        }

        // ������� �����������
        String withoutComments = removeComments(result.toString());

        // �������� ������� � ����� <33 �� ������ � ��������� trim()
        StringBuilder cleaned = new StringBuilder();
        for (char c : withoutComments.toCharArray()) {
            if (c < 33) {
                cleaned.append(' ');
            } else {
                cleaned.append(c);
            }
        }

        return cleaned.toString().trim();
    }

    // ������� ����������� �� ������ �� O(n)
    private static String removeComments(String content) {
        StringBuilder result = new StringBuilder();
        boolean inBlockComment = false;
        boolean inLineComment = false;

        for (int i = 0; i < content.length(); i++) {
            if (inBlockComment) {
                if (i + 1 < content.length() && content.charAt(i) == '*' && content.charAt(i + 1) == '/') {
                    inBlockComment = false;
                    i++; // ���������� '/'
                }
            } else if (inLineComment) {
                if (content.charAt(i) == '\n') {
                    inLineComment = false;
                }
            } else {
                if (i + 1 < content.length() && content.charAt(i) == '/' && content.charAt(i + 1) == '*') {
                    inBlockComment = true;
                    i++; // ���������� '*'
                } else if (i + 1 < content.length() && content.charAt(i) == '/' && content.charAt(i + 1) == '/') {
                    inLineComment = true;
                    i++; // ���������� '/'
                } else {
                    result.append(content.charAt(i));
                }
            }
        }

        return result.toString();
    }

    // ������� ��������� ������ �� ���������� �����������
    private static Map<String, List<String>> findDuplicates(List<FileData> fileDataList) {
        Map<String, List<String>> duplicates = new HashMap<>();
        int n = fileDataList.size();

        for (int i = 0; i < n; i++) {
            FileData file1 = fileDataList.get(i);
            for (int j = i + 1; j < n; j++) {
                FileData file2 = fileDataList.get(j);

                int distance = levenshteinDistance(file1.getContent(), file2.getContent());
                if (distance < 10) {
                    duplicates.computeIfAbsent(file1.getPath(), k -> new ArrayList<>()).add(file2.getPath());
                }
            }
        }

        return duplicates;
    }

    // ������ ���������� �����������
    private static int levenshteinDistance(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
                    );
                }
            }
        }

        return dp[m][n];
    }

    // ��������������� ����� ��� �������� ���������� � �����
    static class FileData {
        private final String path;
        private final String content;

        public FileData(String path, String content) {
            this.path = path;
            this.content = content;
        }

        public String getPath() {
            return path;
        }

        public String getContent() {
            return content;
        }
    }
}