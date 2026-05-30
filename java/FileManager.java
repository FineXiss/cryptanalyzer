import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void writeFile(String content, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }

    public boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}
