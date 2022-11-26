import java.io.*;
import java.util.Optional;
import java.util.function.Consumer;

public class LineChanger implements Consumer<File> {

    @Override
    public void accept(File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File class is directory");
        }

        final Optional<String> extension = this.getExtension(file.getName());
        if (extension.isPresent() && extension.get().equals("txt")) {
            final StringBuilder toWrite = new StringBuilder();
            try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while (true) {
                    final String line = reader.readLine();
                    if (line == null) {
                        break;
                    }

                    final String[] words = line.split("\\s");
                    if (words.length >= 2) {
                        final String tmp = words[0];
                        words[0] = words[words.length - 1];
                        words[words.length - 1] = tmp;
                    }
                    toWrite.append(String.join(" ", words));
                    toWrite.append('\n');
                }
                toWrite.deleteCharAt(toWrite.length() - 1);
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }

            try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(toWrite.toString());
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        System.out.println(file.getName());
    }

    private Optional<String> getExtension(final String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
