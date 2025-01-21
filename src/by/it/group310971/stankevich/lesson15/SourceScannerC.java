package by.it.group310971.stankevich.lesson15;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class SourceScannerC {

    // Информация о файле: путь + «обработанный» текст
    private static class FileData {
        String relativePath;
        String processedText;
        FileData(String path, String text) {
            this.relativePath = path;
            this.processedText = text;
        }
    }

    public static void main(String[] args) {
        try {
            // Каталог src
            String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
            Path start = Paths.get(src);

            // Список (путь, обработанный текст)
            ArrayList<FileData> list = new ArrayList<>();

            // Рекурсивный обход каталога src
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String pathStr = file.toAbsolutePath().toString();
                    // Интересуют только .java-файлы
                    if (!pathStr.endsWith(".java")) {
                        return FileVisitResult.CONTINUE;
                    }
                    try {
                        // Считываем все байты
                        byte[] bytes = Files.readAllBytes(file);

                        // 1) Попробуем "строго" декодировать UTF-8 с REPORT
                        String content;
                        try {
                            content = decodeStrictUTF8(bytes);
                        } catch (CharacterCodingException e) {
                            // 2) fallback: декодер с REPLACE
                            content = decodeReplacingUTF8(bytes);
                        }

                        // Пропускаем файл, если содержит @Test или org.junit.Test
                        if (content.contains("@Test") || content.contains("org.junit.Test")) {
                            return FileVisitResult.CONTINUE;
                        }

                        // Удаляем комментарии (//... и /*...*/) за O(n)
                        String noComments = removeComments(content);

                        // Удаляем строку package, строки import, склеиваем всё
                        String[] lines = noComments.split("\r?\n");
                        StringBuilder sb = new StringBuilder(noComments.length());
                        for (String line : lines) {
                            String trimmed = line.trim();
                            if (trimmed.startsWith("package ")) {
                                continue;
                            }
                            if (trimmed.startsWith("import ")) {
                                continue;
                            }
                            sb.append(line).append('\n');
                        }

                        // Заменяем все последовательности символов <33 на один пробел
                        String replaced = replaceControlSeq(sb.toString());

                        // trim() в конце
                        String finalText = replaced.trim();

                        // Получаем относительный путь, убирая префикс src
                        String relPath = pathStr.replace(src, "");

                        // Сохраняем
                        list.add(new FileData(relPath, finalText));

                    } catch (Exception e) {
                        // Любые проблемы чтения — не падаем
                        // Можно что-то залогировать, но здесь просто пропустим файл
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            // Теперь ищем «копии» по расстоянию Левенштейна < 10
            Map<String, List<String>> copiesMap = new HashMap<>();
            for (FileData fd : list) {
                copiesMap.put(fd.relativePath, new ArrayList<>());
            }

            int n = list.size();
            for (int i = 0; i < n; i++) {
                FileData fdi = list.get(i);
                for (int j = i+1; j < n; j++) {
                    FileData fdj = list.get(j);
                    // Быстрая проверка |len1-len2| < 10
                    if (couldBeClose(fdi.processedText, fdj.processedText)) {
                        int dist = levenshteinLimit(fdi.processedText, fdj.processedText, 10);
                        if (dist < 10) {
                            copiesMap.get(fdi.relativePath).add(fdj.relativePath);
                            copiesMap.get(fdj.relativePath).add(fdi.relativePath);
                        }
                    }
                }
            }

            // Сортируем пути, выводим, у кого есть копии
            ArrayList<String> allPaths = new ArrayList<>(copiesMap.keySet());
            Collections.sort(allPaths);
            for (String path : allPaths) {
                List<String> cList = copiesMap.get(path);
                if (!cList.isEmpty()) {
                    System.out.println(path);
                    Collections.sort(cList);
                    for (String cp : cList) {
                        System.out.println(cp);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------
    // Методы для декодирования
    //--------------------------------------------------------------------------

    /**
     * Строгая декодировка UTF-8: при ошибках бросает CharacterCodingException.
     */
    private static String decodeStrictUTF8(byte[] bytes) throws CharacterCodingException {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        CharsetDecoder dec = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT);
        CharBuffer cb = dec.decode(bb); // может бросить CharacterCodingException
        return cb.toString();
    }

    /**
     * Декодировка UTF-8 с заменой (REPLACE) неверных байтов.
     * Не бросает ошибок.
     */
    private static String decodeReplacingUTF8(byte[] bytes) {
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);
        try (InputStreamReader isr = new InputStreamReader(
                new ByteArrayInputStream(bytes), decoder)) {
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[4096];
            int len;
            while ((len = isr.read(buf)) != -1) {
                sb.append(buf, 0, len);
            }
            return sb.toString();
        } catch (Exception e) {
            // Вряд ли случится, но если всё-таки — вернём пустую строку
            return "";
        }
    }

    //--------------------------------------------------------------------------
    // Удаление комментариев за O(n) одним проходом (конечный автомат)
    //--------------------------------------------------------------------------
    private static String removeComments(String content) {
        StringBuilder sb = new StringBuilder(content.length());
        final int NORMAL = 0;
        final int SLASH = 1;
        final int LINE_COMMENT = 2;
        final int BLOCK_COMMENT = 3;

        int state = NORMAL;
        int length = content.length();
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
                        state = LINE_COMMENT;
                    } else if (c == '*') {
                        state = BLOCK_COMMENT;
                    } else {
                        sb.append('/').append(c);
                        state = NORMAL;
                    }
                    break;
                case LINE_COMMENT:
                    if (c == '\n') {
                        sb.append('\n');
                        state = NORMAL;
                    }
                    // иначе пропускаем (не добавляем)
                    break;
                case BLOCK_COMMENT:
                    if (c == '*' && i+1 < length && content.charAt(i+1) == '/') {
                        i++; // пропускаем '*', а '/' будет обработан тут же
                        state = NORMAL;
                    }
                    break;
            }
        }
        return sb.toString();
    }

    //--------------------------------------------------------------------------
    // Заменяем все последовательности символов с кодом <33 на один пробел
    //--------------------------------------------------------------------------
    private static String replaceControlSeq(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        final int NORMAL = 0;
        final int CONTROL = 1;
        int state = NORMAL;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 33) {
                if (state == NORMAL) {
                    sb.append(' ');
                    state = CONTROL;
                }
            } else {
                sb.append(c);
                state = NORMAL;
            }
        }
        return sb.toString();
    }

    //--------------------------------------------------------------------------
    // Быстрая проверка, стоит ли считать расстояние Левенштейна
    //--------------------------------------------------------------------------
    private static boolean couldBeClose(String s1, String s2) {
        return (Math.abs(s1.length() - s2.length()) < 10);
    }

    //--------------------------------------------------------------------------
    // Левенштейн с порогом limit=10 и ранним прерыванием
    //--------------------------------------------------------------------------
    private static int levenshteinLimit(String s1, String s2, int limit) {
        int len1 = s1.length();
        int len2 = s2.length();
        if (Math.abs(len1 - len2) >= limit) {
            return limit; // точно >= limit
        }
        int[] prev = new int[len2 + 1];
        int[] cur = new int[len2 + 1];
        for (int j = 0; j <= len2; j++) {
            prev[j] = j;
        }
        for (int i = 1; i <= len1; i++) {
            cur[0] = i;
            char c1 = s1.charAt(i - 1);
            int minValThisRow = Integer.MAX_VALUE;
            for (int j = 1; j <= len2; j++) {
                char c2 = s2.charAt(j - 1);
                int cost = (c1 == c2) ? 0 : 1;
                int del = prev[j] + 1;
                int ins = cur[j - 1] + 1;
                int sub = prev[j - 1] + cost;
                int val = Math.min(Math.min(del, ins), sub);
                cur[j] = val;
                if (val < minValThisRow) {
                    minValThisRow = val;
                }
            }
            if (minValThisRow >= limit) {
                return limit;
            }
            int[] tmp = prev;
            prev = cur;
            cur = tmp;
        }
        return prev[len2];
    }
}
