package by.it.group310971.katsuba.lesson15;

import java.io.File;
import java.util.*;
import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.stream.*;

public class SourceScannerA {
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        Path startPath = Paths.get(src);

        try {
            List<FileData> fileDataList = Files.walk(startPath)
                    .filter(path -> path.toString().endsWith(".java")) // Фильтруем .java файлы
                    .map(SourceScannerA::processFile)
                    .filter(Objects::nonNull) // Игнорируем null (файлы с @Test)
                    .collect(Collectors.toList());

            // Сортируем файлы по размеру и по пути
            fileDataList.sort(Comparator.comparingLong(FileData::getSize)
                    .thenComparing(FileData::getRelativePath));

            // Выводим результаты
            for (FileData fileData : fileDataList) {
                System.out.printf("%d bytes - %s%n", fileData.getSize(), fileData.getRelativePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileData processFile(Path path) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            StringBuilder content = new StringBuilder();

            for (String line : lines) {
                // Пропускаем пакеты и импорты
                if (line.startsWith("package") || line.startsWith("import")) {
                    continue;
                }

                // Удаляем нежелательные символы
                content.append(line.trim()).append(System.lineSeparator());
            }

            String processedContent = content.toString().trim();
            processedContent = removeLeadingAndTrailingControlCharacters(processedContent);

            // Исключаем тестовые файлы
            if (processedContent.contains("@Test") || processedContent.contains("org.junit.Test")) {
                return null;
            }

            return new FileData(path, processedContent.getBytes(StandardCharsets.UTF_8).length);
        } catch (MalformedInputException e) {
            // Игнорируем ошибки MalformedInputException
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String removeLeadingAndTrailingControlCharacters(String content) {
        return content.replaceAll("^[\\x00-\\x1F]+|[\\x00-\\x1F]+$", "");
    }

    private static class FileData {
        private final Path path;
        private final long size;

        public FileData(Path path, long size) {
            this.path = path;
            this.size = size;
        }

        public long getSize() {
            return size;
        }

        public String getRelativePath() {
            return Paths.get(System.getProperty("user.dir"), "src").relativize(path).toString();
        }
    }
}
