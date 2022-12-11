import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayFour {
    static class Range {
        int left;
        int right;
        Range(int left, int right) {
            this.left = left;
            this.right = right;
        }

        boolean contains(Range r) {
            return left <= r.left && r.right <= right;
        }
    }
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            int count = 0;
            int total = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("no match found on line: " + line);
                }
                Range rangeOne = new Range(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                Range rangeTwo = new Range(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                if (rangeTwo.contains(rangeOne) || rangeOne.contains(rangeTwo)) {
                    ++count;
                }
                ++total;
            }
            System.out.println("total ful: " + total);

            return count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
