import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DayThree {
    public static void main(String[] args) {
        System.out.println("total priority: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            int totalPriority = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.length() % 2 != 0) {
                    throw new IllegalArgumentException("Line size: " + line.length());
                }
                Set<Character> setLeft = new HashSet<>();
                Set<Character> setRight = new HashSet<>();
                for (int i = 0; i < line.length() / 2; ++i) {
                    setLeft.add(line.charAt(i));
                    setRight.add(line.charAt(line.length() - 1 - i));
                }
                setLeft.retainAll(setRight);
                if (setLeft.size() != 1) {
                    throw new RuntimeException("Too Many overlap: " + setLeft);
                }
                char a = setLeft.stream().findAny().get();
                if (a < 91) {
                    totalPriority += a - 64 + 26;
                } else {
                    totalPriority += a - 96;
                }
            }
            return totalPriority;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}