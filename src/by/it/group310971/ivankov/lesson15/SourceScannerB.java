package by.it.group310971.ivankov.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SourceScannerB extends SourceScannerA {

    // Метод для получения информации о Java-файлах
    protected static void getInformation() throws IOException {
        List<String> sizeDirectory = new ArrayList<>();

        // Путь к директории src
        Path src = Path.of(System.getProperty("user.dir")
                + File.separator + "src" + File.separator);

        try (Stream<Path> fileTrees = Files.walk(src)) {
            fileTrees.forEach(directory -> {
                if (directory.toString().endsWith(".java")) {
                    try {
                        String content = Files.readString(directory);

                        // Пропуск файлов с аннотациями @Test
                        if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                            // Удаление пакета, импортов и комментариев
                            content = content.replaceAll("package.*;", "")
                                    .replaceAll("import.*;", "")
                                    .replaceAll("/\\*[\\w\\W\\r\\n\\t]*?\\*/", "") // Многострочные комментарии
                                    .replaceAll("//.*", "") // Однострочные комментарии
                                    .trim(); // Убираем лишние пробелы

                            // Если строка не пустая, добавляем размер и путь
                            if (!content.isEmpty()) {
                                sizeDirectory.add(content.getBytes().length + " " + src.relativize(directory));
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Ошибка чтения файла: " + directory);
                    }
                }
            });

            // Сортировка и вывод информации
            Collections.sort(sizeDirectory, new MyStringComparator());
            sizeDirectory.forEach(System.out::println);
        }
    }

    // Реализация метода move (удаляет пустые символы из массива символов)
    private static char[] move(char[] charArr) {
        StringBuilder result = new StringBuilder();
        for (char c : charArr) {
            if (c != 0) {
                result.append(c);
            }
        }
        return result.toString().toCharArray();
    }

    // Точка входа в программу
    public static void main(String[] args) throws IOException {
        getInformation();
    }
}

// Класс для сортировки строк (по первому числу в строке)
class MyStringComparator implements java.util.Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        int size1 = Integer.parseInt(o1.split(" ")[0]);
        int size2 = Integer.parseInt(o2.split(" ")[0]);
        return Integer.compare(size1, size2);
    }
}
