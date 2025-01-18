package DorofeyMI_310971.lesson15;

import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class SourceScannerA {
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        Path srcPath = Paths.get(src);
        List<FileData> fileDataList = new ArrayList<>();

        if (!Files.exists(srcPath)) {
            System.err.println("Каталог 'src' не найден: " + srcPath);
            return;
        }

        try {
            Files.walk(srcPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            String content = new String(Files.readAllBytes(path));
                            if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                                content = removePackageAndImports(content);
                                content = removeSpecialChars(content);
                                fileDataList.add(new FileData(path, content.getBytes().length));
                            }
                        } catch (MalformedInputException e) {
                            // Игнорируем ошибки MalformedInputException
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            fileDataList.sort(Comparator.comparingInt(FileData::getSize)
                    .thenComparing(fileData -> fileData.getPath().toString()));

            fileDataList.forEach(fileData ->
                    System.out.println(fileData.getSize() + " " + srcPath.relativize(fileData.getPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String removePackageAndImports(String content) {
        return Arrays.stream(content.split("\n"))
                .filter(line -> !line.startsWith("package") && !line.startsWith("import"))
                .collect(Collectors.joining("\n"));
    }

    private static String removeSpecialChars(String content) {
        return content.replaceAll("^[\\x00-\\x20]+|[\\x00-\\x20]+$", "");
    }

    private static class FileData {
        private final Path path;
        private final int size;

        public FileData(Path path, int size) {
            this.path = path;
            this.size = size;
        }

        public Path getPath() {
            return path;
        }

        public int getSize() {
            return size;
        }
    }
}
