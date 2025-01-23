package by.it.a_khmelev.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SourceScannerB  {

    public static void main(String[] args) {
        // Получение пути к каталогу src
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        Path srcPath = Paths.get(src);

        // Список для хранения информации о файлах
        List<FileInfo> fileInfos = new ArrayList<>();

        try {
            // Рекурсивный обход всех файлов .java
            Files.walk(srcPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            // Читаем содержимое файла
                            String content = Files.readString(path, StandardCharsets.UTF_8);

                            // Пропускаем файлы, содержащие @Test или org.junit.Test
                            if (content.contains("@Test") || content.contains("org.junit.Test")) {
                                return;
                            }

                            // Обрабатываем текст файла
                            String processedContent = processContent(content);

                            // Рассчитываем размер текста в байтах
                            byte[] contentBytes = processedContent.getBytes(StandardCharsets.UTF_8);
                            int size = contentBytes.length;

                            // Добавляем информацию о файле в список
                            String relativePath = srcPath.relativize(path).toString();
                            fileInfos.add(new FileInfo(relativePath, size));
                        } catch (MalformedInputException e) {
                            // Игнорируем ошибки чтения
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Сортируем файлы по размеру, а затем лексикографически по пути
        fileInfos.sort(Comparator.comparingInt(FileInfo::getSize)
                .thenComparing(FileInfo::getPath));

        // Выводим информацию о файлах в консоль
        fileInfos.forEach(fileInfo ->
                System.out.println(fileInfo.getSize() + " " + fileInfo.getPath()));
    }

    // Обрабатывает содержимое файла
    private static String processContent(String content) {
        // Удаляем строку package и все импорты
        String[] lines = content.split("\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.startsWith("package") && !trimmed.startsWith("import")) {
                result.append(line).append("\n");
            }
        }

        // Удаляем все комментарии за O(n)
        String withoutComments = removeComments(result.toString());

        // Удаляем символы с кодом <33 в начале и конце
        String trimmedContent = trimLowAscii(withoutComments);

        // Удаляем пустые строки
        StringBuilder finalResult = new StringBuilder();
        for (String line : trimmedContent.split("\n")) {
            if (!line.trim().isEmpty()) {
                finalResult.append(line).append("\n");
            }
        }

        return finalResult.toString();
    }

    // Удаляет комментарии из текста за O(n)
    private static String removeComments(String content) {
        StringBuilder result = new StringBuilder();
        boolean inBlockComment = false;
        boolean inLineComment = false;

        for (int i = 0; i < content.length(); i++) {
            if (inBlockComment) {
                if (i + 1 < content.length() && content.charAt(i) == '*' && content.charAt(i + 1) == '/') {
                    inBlockComment = false;
                    i++; // Пропустить '/'
                }
            } else if (inLineComment) {
                if (content.charAt(i) == '\n') {
                    inLineComment = false;
                }
            } else {
                if (i + 1 < content.length() && content.charAt(i) == '/' && content.charAt(i + 1) == '*') {
                    inBlockComment = true;
                    i++; // Пропустить '*'
                } else if (i + 1 < content.length() && content.charAt(i) == '/' && content.charAt(i + 1) == '/') {
                    inLineComment = true;
                    i++; // Пропустить '/'
                } else {
                    result.append(content.charAt(i));
                }
            }
        }

        return result.toString();
    }

    // Удаляет символы с кодом <33 в начале и конце текста
    private static String trimLowAscii(String content) {
        int start = 0;
        int end = content.length();

        while (start < end && content.charAt(start) < 33) {
            start++;
        }
        while (end > start && content.charAt(end - 1) < 33) {
            end--;
        }
        return content.substring(start, end);
    }

    // Вспомогательный класс для хранения информации о файле
    static class FileInfo {
        private final String path;
        private final int size;

        public FileInfo(String path, int size) {
            this.path = path;
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public int getSize() {
            return size;
        }
    }
}