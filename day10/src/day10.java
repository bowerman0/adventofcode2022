import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class day10 {
    enum CommandType {
        ADDX1,
        ADDX2,
        NOOP
    }
    static class Command {
        CommandType type;
        int data;
        Command(CommandType type, int data) {
            this.type = type;
            this.data = data;
        }
    }
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            Deque<Command> deque = new ArrayDeque<>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                switch (line.charAt(0)) {
                    case 'a': //addx
                        deque.offerLast(new Command(CommandType.ADDX1, Integer.parseInt(line.substring(5))));
                        break;
                    case 'n': // noop
                        deque.offerLast(new Command(CommandType.NOOP, 0));
                        break;
                }
            }

            int result = 0;
            int xRegister = 1;
            int counter = 1;
            while (!deque.isEmpty()) {
//                System.out.println("x(" + counter + "): " + xRegister);
                if (counter == 20 ||
                        counter == 60 ||
                        counter == 100 ||
                        counter == 140 ||
                        counter == 180 ||
                        counter == 220) {
                    result += counter * xRegister;
                    // print
                }
                ++counter;
                Command c = deque.pollFirst();
                if (c != null) {
                    switch (c.type) {
                        case ADDX1:
                            deque.offerFirst(new Command(CommandType.ADDX2, c.data));
                            break;
                        case ADDX2:
                            xRegister += c.data;
                            break;
                        case NOOP:
                            break;
                        default:
                            throw new IllegalArgumentException("Not found: " + c);
                    }
                }
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
