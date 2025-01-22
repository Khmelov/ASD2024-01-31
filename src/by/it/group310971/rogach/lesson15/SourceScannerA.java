package by.it.group310971.rogach.lesson15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SourceScannerA {

    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(new File(src), javaFiles);

        List<FileSizeInfo> fileSizes = new ArrayList<>();
        for (File file : javaFiles) {
            if (!containsTestAnnotation(file)) {
                try {
                    String fileContent = readJavaFile(file);
                    String cleanedContent = cleanContent(fileContent);
                    int sizeInBytes = cleanedContent.getBytes(StandardCharsets.UTF_8).length;
                    fileSizes.add(new FileSizeInfo(sizeInBytes, getRelativePath(src, file)));
                } catch (IOException e) {
                    // Игнорируем MalformedInputException и другие IOException
                    continue;
                }
            }
        }

        fileSizes.stream()
                .sorted(Comparator.comparingInt(FileSizeInfo::size)
                        .thenComparing(FileSizeInfo::path))
                .forEach(info ->
                        System.out.println(info.size + " " + info.path));
    }

    private static void collectJavaFiles(File directory, List<File> javaFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    collectJavaFiles(file, javaFiles);
                } else if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
    }

    private static boolean containsTestAnnotation(File file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("@Test") || line.contains("org.junit.Test")) {
                    return true;
                }
            }
        } catch (IOException e) {
            // Игнорируем ошибки чтения
        }
        return false;
    }

    private static String readJavaFile(File file) throws IOException {
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    private static String cleanContent(String content) {
        // Удаляем строку package и все импорты
        StringBuilder cleaned = new StringBuilder();
        String[] lines = content.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (!line.startsWith("package") && !line.startsWith("import")) {
                cleaned.append(line).append("\n");
            }
        }

        // Удаляем символы с кодом < 33 в начале и конце
        String result = cleaned.toString().replaceAll("^[\\x00-\\x1F]+|[\\x00-\\x1F]+$", "");
        return result.trim();
    }

    private static String getRelativePath(String basePath, File file) {
        return basePath.length() < file.getPath().length()
                ? file.getPath().substring(basePath.length())
                : file.getPath();
    }

    private static class FileSizeInfo {
        private final int size;
        private final String path;

        public FileSizeInfo(int size, String path) {
            this.size = size;
            this.path = path;
        }

        public int size() {
            return size;
        }

        public String path() {
            return path;
        }
    }
}