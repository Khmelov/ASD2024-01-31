package by.it.group310971.stankevich.lesson15;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * SourceScannerB
 * 1) Рекурсивно сканирует все *.java в src
 * 2) Пропускает файлы с @Test и org.junit.Test
 * 3) Удаляет из текста:
 *    - строку package
 *    - import строки
 *    - все комментарии (// ... и /* ... )
        *    - символы <33 в начале и в конце итогового текста
 *    - пустые строки (полностью)
 * 4) Выводит (size + относительный путь) в порядке возрастания size,
 *    а при равенстве size — по лексикографическому порядку пути.
        */
public class SourceScannerB {

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
            Path start = Paths.get(src);

            // Результаты
            ArrayList<FileInfo> results = new ArrayList<>();

            // Рекурсивный обход
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String pathStr = file.toAbsolutePath().toString();
                    if (!pathStr.endsWith(".java")) {
                        return FileVisitResult.CONTINUE;
                    }
                    try {
                        // Чтение файла (UTF-8)
                        byte[] bytes = Files.readAllBytes(file);
                        String content = new String(bytes, StandardCharsets.UTF_8);

                        // Если содержит @Test или org.junit.Test — пропускаем
                        if (content.contains("@Test") || content.contains("org.junit.Test")) {
                            return FileVisitResult.CONTINUE;
                        }

                        // Удаляем все комментарии за O(n) одним проходом
                        String noComments = removeComments(content);

                        // Построчно обрабатываем удаление package, import, пустых строк
                        String[] lines = noComments.split("\r?\n");

                        StringBuilder sb = new StringBuilder(noComments.length());
                        for (String line : lines) {
                            String trimmed = line.trim();
                            // Пропускаем строку package ...
                            if (trimmed.startsWith("package ")) {
                                continue;
                            }
                            // Пропускаем строку import ...
                            if (trimmed.startsWith("import ")) {
                                continue;
                            }
                            // Также пропускаем, если строка стала пустой (или только пробелы)
                            if (trimmed.isEmpty()) {
                                continue;
                            }
                            // Иначе добавляем в sb
                            sb.append(line).append("\n");
                        }
                        // Теперь sb содержит текст без package, import, пустых строк, комментариев

                        // Удаляем символы < 33 в начале и в конце
                        String processed = sb.toString();
                        processed = trimControlChars(processed);

                        // Размер в байтах UTF-8
                        long size = processed.getBytes(StandardCharsets.UTF_8).length;

                        // Относительный путь
                        String relPath = pathStr.replace(src, "");

                        // Добавляем в results
                        results.add(new FileInfo(size, relPath));

                    } catch (MalformedInputException mie) {
                        // Пропускаем файл при проблемах чтения
                    } catch (Exception ex) {
                        // Любые другие проблемы чтения - тоже пропускаем
                        // ex.printStackTrace(); // или можно залогировать
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            // Сортируем results
            results.sort((o1, o2) -> {
                int cmp = Long.compare(o1.size, o2.size);
                if (cmp == 0) {
                    cmp = o1.relativePath.compareTo(o2.relativePath);
                }
                return cmp;
            });

            // Вывод
            for (FileInfo fi : results) {
                System.out.println(fi.size + " " + fi.relativePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем все комментарии ( //...  и  /* ... *) за O(n) одним проходом.
            */
    private static String removeComments(String content) {
        StringBuilder sb = new StringBuilder(content.length());
        final int length = content.length();

        // Состояния
        final int NORMAL = 0;
        final int SLASH = 1;  // только что встретили '/'
        final int LINE_COMMENT = 2; // в режиме '//'
        final int BLOCK_COMMENT = 3; // в режиме '/*'

        int state = NORMAL;
        for (int i = 0; i < length; i++) {
            char c = content.charAt(i);

            switch (state) {
                case NORMAL:
                    if (c == '/') {
                        state = SLASH;
                    } else {
                        sb.append(c);
                    }
                    break;

                case SLASH:
                    if (c == '/') {
                        // переходим в LINE_COMMENT, не добавляя '/' в sb
                        state = LINE_COMMENT;
                    } else if (c == '*') {
                        // переходим в BLOCK_COMMENT, не добавляя '/' in sb
                        state = BLOCK_COMMENT;
                    } else {
                        // это оказался обычный символ
                        // нужно вернуть '/' + текущий символ
                        sb.append('/');
                        sb.append(c);
                        state = NORMAL;
                    }
                    break;

                case LINE_COMMENT:
                    // пропускаем все символы до конца строки
                    if (c == '\n') {
                        // возвращаемся к NORMAL, но добавим перенос строки
                        sb.append('\n');
                        state = NORMAL;
                    }
                    // иначе ничего не добавляем
                    break;

                case BLOCK_COMMENT:
                    // пропускаем все символы до '*/'
                    if (c == '*') {
                        // возможно, на следующем шаге увидим '/'
                        if (i + 1 < length && content.charAt(i + 1) == '/') {
                            // пропускаем '*' и '/'
                            i++;
                            state = NORMAL;
                        }
                    }
                    // иначе всё пропускаем
                    break;
            }
        }

        // Если вдруг блок-комментарий или slash не закрылся — это не страшно,
        // содержимое в любом случае удалено/не добавлено.
        return sb.toString();
    }

    /**
     * Удаляет все символы с кодом < 33 в начале и в конце строки.
     * (внутри строки ничего не трогаем)
     */
    private static String trimControlChars(String text) {
        int start = 0;
        int end = text.length() - 1;
        while (start <= end && text.charAt(start) < 33) {
            start++;
        }
        while (end >= start && text.charAt(end) < 33) {
            end--;
        }
        if (start > end) {
            return "";
        }
        return text.substring(start, end + 1);
    }
}
