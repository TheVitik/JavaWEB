import java.io.File;
import java.util.function.Consumer;

public class DirectoryTraverser {

    private final String root;

    public DirectoryTraverser(String root) {
        final File file = new File(root);
        if (!file.exists()) {
            throw new IllegalArgumentException("Directory doesn't exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Root path should point to directory");
        }
        this.root = root;
    }

    public void traverseAndExecute(Consumer<File> func) {
        this.traverseAndExecuteRecursive(new File(root), func);
    }

    private void traverseAndExecuteRecursive(final File file, final Consumer<File> func) {
        if (!file.isDirectory()) {
            func.accept(file);
        } else {
            for (File child : file.listFiles()) {
                final Thread thread = new Thread(() -> this.traverseAndExecuteRecursive(child, func));
                thread.start();
            }
        }
    }
}
