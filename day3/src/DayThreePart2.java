import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class DayThreePart2 {
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
                Set<Character> setOne = line.chars()
                        .mapToObj(e->(char)e).collect(Collectors.toSet());
                line = reader.readLine();
                Set<Character> setTwo = line.chars()
                        .mapToObj(e->(char)e).collect(Collectors.toSet());
                line = reader.readLine();
                Set<Character> setThree = line.chars()
                        .mapToObj(e->(char)e).collect(Collectors.toSet());
                setOne.retainAll(setTwo);
                setOne.retainAll(setThree);
                if (setOne.size() != 1) {
                    throw new IllegalArgumentException("Multiple badge: " + setOne);
                }
                char a = setOne.stream().findAny().get();
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
