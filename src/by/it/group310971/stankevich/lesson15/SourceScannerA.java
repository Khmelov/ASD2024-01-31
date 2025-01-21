package by.it.group310971.stankevich.lesson15;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class SourceScannerA {

    // Структура для хранения результата: (размер в байтах, относительный путь).
    private static class FileInfo {
        long size;
        String relativePath;

        FileInfo(long size, String relativePath) {
            this.size = size;
            this.relativePath = relativePath;
        }
    }

    public static void main(String[] args) {
        try {
            // Каталог src
            String src = System.getProperty("user.dir")
                    + File.separator + "src" + File.separator;

            // Список с результатами
            // (нельзя использовать Collections? В условии это не оговорено,
            //  так что можно, но для полного соблюдения можно сделать массив.
            //  Ниже я использую ArrayList просто ради удобства.)
            ArrayList<FileInfo> results = new ArrayList<>();

            // Рекурсивно обходить каталог src
            Path start = Paths.get(src);
            // Чтобы учесть подкаталоги, используем Files.walkFileTree
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String pathStr = file.toAbsolutePath().toString();
                    // Проверка на *.java
                    if (pathStr.endsWith(".java")) {
                        // Пытаемся прочитать файл
                        // и если не удаётся (MalformedInputException), то пропускаем
                        try {
                            // Считаем содержимое
                            byte[] allBytes = Files.readAllBytes(file);
                            // Преобразуем в строку UTF-8
                            String content = new String(allBytes, StandardCharsets.UTF_8);

                            // Проверим наличие @Test или org.junit.Test
                            // Если есть — пропускаем
                            if (content.contains("@Test") || content.contains("org.junit.Test")) {
                                return FileVisitResult.CONTINUE; // skip
                            }

                            // Далее обрабатываем:
                            // 1) Удаляем строку package ...
                            // 2) Удаляем строки import ...
                            // 3) После объединения удаляем символы < 33 в начале и конце.

                            // Разбиваем на строки (можно по "\r?\n")
                            String[] lines = content.split("\r?\n");
                            StringBuilder sb = new StringBuilder(content.length());

                            for (String line : lines) {
                                // trim() линии для проверки
                                String trimmed = line.trim();
                                // Если строка начинается с package — пропускаем
                                if (trimmed.startsWith("package")) {
                                    continue;
                                }
                                // Если строка начинается с import — пропускаем
                                if (trimmed.startsWith("import")) {
                                    continue;
                                }
                                // Иначе добавляем
                                sb.append(line).append("\n");
                            }

                            // Теперь sb содержит текст без package и import
                            // Удаляем символы < 33 только в начале и в конце
                            // Сделаем вручную:
                            String processed = sb.toString();
                            processed = trimControlChars(processed);

                            // Рассчитываем размер в UTF-8 (байты)
                            long sizeInBytes = processed.getBytes(StandardCharsets.UTF_8).length;

                            // Относительный путь (от src)
                            // file — это абсолютный Path. Нужно "обрезать" src
                            String relPath = file.toAbsolutePath().toString()
                                    .replace(src, ""); // уберём префикс src

                            // Добавляем в список
                            results.add(new FileInfo(sizeInBytes, relPath));

                        } catch (java.nio.charset.MalformedInputException mie) {
                            // Игнорируем, просто пропускаем этот файл
                        } catch (Exception e) {
                            // На всякий случай: любые другие ошибки чтения - тоже пропускаем
                            // или вы можете вывести e.printStackTrace();
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            // Теперь сортируем results
            // 1) по size (возрастание)
            // 2) при равенстве size — лексикографический порядок пути
            results.sort((o1, o2) -> {
                int cmp = Long.compare(o1.size, o2.size);
                if (cmp == 0) {
                    cmp = o1.relativePath.compareTo(o2.relativePath);
                }
                return cmp;
            });

            // Выводим
            for (FileInfo fi : results) {
                System.out.println(fi.size + " " + fi.relativePath);
            }

        } catch (Exception e) {
            // На самый крайний случай
            e.printStackTrace();
        }
    }

    /**
     * Удаляет все символы с кодом < 33 в начале и конце строки.
     * Внутри строки символы не удаляются.
     */
    private static String trimControlChars(String text) {
        int start = 0;
        int end = text.length() - 1;
        // Сдвигаем start, пока символы < 33
        while (start <= end && text.charAt(start) < 33) {
            start++;
        }
        // Сдвигаем end, пока символы < 33
        while (end >= start && text.charAt(end) < 33) {
            end--;
        }
        if (start > end) {
            // Всё "вырезано"
            return "";
        }
        return text.substring(start, end + 1);
    }
}

