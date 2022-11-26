import java.io.File;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    private static final Consumer<File> lineChanger = new LineChanger();

    public static void main(String[] args) {
        try {
            System.out.println("Enter directory path, please: ");
            final String path = in.nextLine();
            final DirectoryTraverser traverser = new DirectoryTraverser(path);
            traverser.traverseAndExecute(lineChanger);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
