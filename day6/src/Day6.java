import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day6 {

    public static final int MESSAGE_LENGTH = 14;

    public static void main(String[] args) {
        System.out.println("position: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            String line = reader.readLine();
            char[] counts = new char[256];
            int duplicates = 0;
            int pos = 0;
            for ( ; pos<line.length(); ++pos) {
                ++counts[line.charAt(pos)];
                if (2 == counts[line.charAt(pos)]) {
                    ++duplicates;
                }
                if (MESSAGE_LENGTH <= pos) {
                    int oldPos = pos - MESSAGE_LENGTH;
                    --counts[line.charAt(oldPos)];
                    if (1 == counts[line.charAt(oldPos)]) {
                        --duplicates;
                    }
                }

                if (MESSAGE_LENGTH-1 <= pos && 0 == duplicates) {
                    System.out.println(line.substring(Math.max(pos - (MESSAGE_LENGTH-1), 0),pos+1));
                    return pos + 1; // 1-indexed
                }
            }
            return -1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
