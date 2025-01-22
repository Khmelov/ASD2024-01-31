package by.it.group310971.fedorenko.lesson15;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

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

                        if (content.contains("@Test") || content.contains("org.junit.Test")) {
                            return FileVisitResult.CONTINUE;
                        }

                        String noComments = removeComments(content);

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

                            if (trimmed.isEmpty()) {
                                continue;
                            }
                            sb.append(line).append("\n");
                        }

                        // Удаляем символы < 33 в начале и в конце
                        String processed = sb.toString();
                        processed = trimControlChars(processed);

                        // Размер в байтах UTF-8
                        long size = processed.getBytes(StandardCharsets.UTF_8).length;

                        String relPath = pathStr.replace(src, "");

                        results.add(new FileInfo(size, relPath));

                    } catch (MalformedInputException mie) {

                    } catch (Exception ex) {

                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            results.sort((o1, o2) -> {
                int cmp = Long.compare(o1.size, o2.size);
                if (cmp == 0) {
                    cmp = o1.relativePath.compareTo(o2.relativePath);
                }
                return cmp;
            });

            for (FileInfo fi : results) {
                System.out.println(fi.size + " " + fi.relativePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String removeComments(String content) {
        StringBuilder sb = new StringBuilder(content.length());
        final int length = content.length();

        // Состояния
        final int NORMAL = 0;
        final int SLASH = 1;
        final int LINE_COMMENT = 2;
        final int BLOCK_COMMENT = 3;

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
                        state = LINE_COMMENT;
                    } else if (c == '*') {
                        state = BLOCK_COMMENT;
                    } else {
                        sb.append('/');
                        sb.append(c);
                        state = NORMAL;
                    }
                    break;

                case LINE_COMMENT:
                    if (c == '\n') {

                        sb.append('\n');
                        state = NORMAL;
                    }
                    break;

                case BLOCK_COMMENT:
                    if (c == '*') {
                        if (i + 1 < length && content.charAt(i + 1) == '/') {

                            i++;
                            state = NORMAL;
                        }
                    }
                    break;
            }
        }

        return sb.toString();
    }

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