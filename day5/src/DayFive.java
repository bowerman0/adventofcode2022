import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayFive {
    public static void main(String[] args) {
        System.out.println("top: " + read());
    }
    private static String read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            List<Deque<Character>> stacks = new ArrayList<>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.charAt(1) == '1') {
                    reader.readLine(); // blank line.
                    break;
                }
                for (int i=1, stack=0; i<line.length(); i+=4, ++stack) {
                    if (stacks.size() <= stack) {
                        stacks.add(new ArrayDeque<>());
                    }
                    char c = line.charAt(i);
                    if (c != ' ') {
                        stacks.get(stack).offerLast(c);
                    }
                }
            }
            Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("no match found on line: " + line);
                }
                int count = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2))-1;
                int to = Integer.parseInt(matcher.group(3))-1;
                for ( ; count > 0; --count) {
                    stacks.get(to).offerFirst(stacks.get(from).pollFirst());
                }
            }
            StringBuilder sb = new StringBuilder();
            for (Deque<Character> stack : stacks) {
                sb.append(stack.peekFirst());
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
