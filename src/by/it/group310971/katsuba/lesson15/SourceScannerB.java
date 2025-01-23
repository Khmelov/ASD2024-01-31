package by.it.group310971.katsuba.lesson15;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SourceScannerB {
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        List<File> javaFiles = new ArrayList<>();

        // Сбор всех файлов .java из каталога src и его подкаталогов
        gatherJavaFiles(new File(src), javaFiles);

        List<FileSizeInfo> fileSizeInfos = new ArrayList<>();

        for (File file : javaFiles) {
            try {
                String cleanedText = processJavaFile(file);
                if (cleanedText != null) {
                    int sizeInBytes = cleanedText.getBytes().length;
                    fileSizeInfos.add(new FileSizeInfo(file.getPath().replace(src, ""), sizeInBytes));
                }
            } catch (MalformedInputException e) {
                System.err.println("Ошибка чтения файла: " + file.getPath() + " - " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Ошибка обработки файла: " + file.getPath() + " - " + e.getMessage());
            }
        }

        // Сортировка по размеру и лексикографически
        fileSizeInfos.sort(Comparator.comparingInt(FileSizeInfo::getSize)
                .thenComparing(FileSizeInfo::getRelativePath));

        // Вывод результата
        for (FileSizeInfo info : fileSizeInfos) {
            System.out.println(info.getSize() + " bytes - " + info.getRelativePath());
        }
    }

    private static void gatherJavaFiles(File directory, List<File> javaFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    gatherJavaFiles(file, javaFiles);
                } else if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
    }

    private static String processJavaFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            boolean insideComment = false;

            while ((line = reader.readLine()) != null) {
                line = removePackageAndImports(line);
                line = removeComments(line, insideComment);

                if (line.trim().length() > 0) {
                    sb.append(line).append(System.lineSeparator());
                }
            }
        }

        String result = sb.toString().trim();
        return result.isEmpty() ? null : result; // Возвращаем null, если пустой результат
    }

    private static String removePackageAndImports(String line) {
        if (line.startsWith("package") || line.startsWith("import")) {
            return ""; // Удаляем строки с package и import
        }
        return line;
    }

    private static String removeComments(String line, boolean insideComment) {
        if (insideComment) {
            if (line.contains("*/")) {
                insideComment = false;
                return ""; // Удаляем многострочные комментарии
            }
            return ""; // Удаляем все строки внутри многострочного комментария
        }

        if (line.contains("/*")) {
            insideComment = true;
            return line.substring(0, line.indexOf("/*")); // Удаляем строку до многострочного комментария
        }

        if (line.contains("//")) {
            return line.substring(0, line.indexOf("//")); // Удаляем строку до однострочного комментария
        }

        return line;
    }

    private static class FileSizeInfo {
        private final String relativePath;
        private final int size;

        public FileSizeInfo(String relativePath, int size) {
            this.relativePath = relativePath;
            this.size = size;
        }

        public String getRelativePath() {
            return relativePath;
        }

        public int getSize() {
            return size;
        }
    }
}
