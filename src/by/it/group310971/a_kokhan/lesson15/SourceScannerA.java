package by.it.group310971.a_kokhan.lesson15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SourceScannerA {
    public static void main(String[] args) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        System.out.println(src.toString());
        try {
            for (var filePath : findAllFiles(src)) {
                var content = new String(Files.readAllBytes(filePath));
                var index = content.indexOf("Test");
                try {
                    if (content.charAt(index - 1) == '@' || 
                        content.substring(index - 10, index - 1).equals("org.junit")){
                        continue;
                    }
                } catch (Exception e) {}
                var sb = new StringBuilder(content.substring(content.lastIndexOf('\n', content.indexOf("class"))));
                var i = 0;
                var length = sb.length()-1;
                while (i < length) {
                    if (sb.charAt(i) < 33) {
                        sb.deleteCharAt(i);
                        length--;
                    } else {
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public static ArrayList<Path> findAllFiles(String directory) {
        var fileList = new ArrayList<Path>();
        Path dirPath = Paths.get(directory);

        try {
            Files.walk(dirPath)
                 .filter(Files::isRegularFile).filter(file -> file.toString().endsWith(".java"))
                 .forEach(fileList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileList;
    }
}
