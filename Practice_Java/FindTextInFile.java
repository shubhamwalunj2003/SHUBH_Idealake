import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FindTextInFile {

    public static void main(String[] args) {
        try {
            boolean isFound = findStringInFile("C:\\Users\\shubham_walunj\\Desktop\\Shubh Idealake\\Practice_Java\\sample", "shubham");
            if (isFound) {
                System.out.println("String found in the file.");
            } else {
                System.out.println("String not found in the file.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public static boolean findStringInFile(String filePath, String str) throws FileNotFoundException {
        File file = new File(filePath);

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(str)) {
                scanner.close();
                return true;
            }
        }
        scanner.close();

        return false;
    }
}
