package by.it.group310971.ivankov.lesson15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class SourceScannerA {

    // Компаратор для сравнения строк с числами в начале
    protected static class MyStringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int num1 = extractFirstInt(s1);
            int num2 = extractFirstInt(s2);

            if (num1 == num2) {
                return s1.compareTo(s2);
            }
            return Integer.compare(num1, num2);
        }

        private int extractFirstInt(String str) {
            Scanner scanner = new Scanner(str);
            return scanner.hasNextInt() ? scanner.nextInt() : 0;
        }
    }

    // Метод для удаления нулевых символов в начале и в конце массива символов
    protected static char[] trimCharArray(char[] array) {
        int start = 0;
        int end = array.length - 1;

        // Пропускаем начальные нулевые символы
        while (start < array.length && array[start] == 0) {
            start++;
        }

        // Пропускаем конечные нулевые символы
        while (end >= start && array[end] == 0) {
            end--;
        }

        // Создаем новый массив без нулевых символов
        char[] trimmedArray = new char[end - start + 1];
        System.arraycopy(array, start, trimmedArray, 0, trimmedArray.length);
        return trimmedArray;
    }

    // Основной метод для получения информации о файлах
    private static void getInformation() throws IOException {
        List<String> fileInfoList = new ArrayList<>();
        Path sourceDir = Path.of(System.getProperty("user.dir"), "src");

        try (Stream<Path> fileStream = Files.walk(sourceDir)) {
            fileStream.forEach(path -> {
                if (path.toString().endsWith(".java")) {
                    try {
                        String fileContent = Files.readString(path);
                        if (!fileContent.contains("@Test") && !fileContent.contains("org.junit.Test")) {
                            fileContent = fileContent.replaceAll("package.+;", "")
                                    .replaceAll("import.+;", "");

                            if (!fileContent.isEmpty()) {
                                char[] charArr = fileContent.toCharArray();
                                charArr = trimCharArray(charArr);
                                fileContent = new String(charArr);
                            }

                            fileInfoList.add(fileContent.getBytes().length + " " + sourceDir.relativize(path));
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading file: " + path);
                    }
                }
            });
        }

        // Сортируем информацию по размеру файлов
        Collections.sort(fileInfoList, new MyStringComparator());

        // Выводим информацию о файлах
        fileInfoList.forEach(System.out::println);
    }

    public static void main(String[] args) throws IOException {
        getInformation();
    }
}
