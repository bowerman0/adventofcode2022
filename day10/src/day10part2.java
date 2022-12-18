import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class day10part2 {
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

            int xRegister = 1;
            int counter = 0;
            while (!deque.isEmpty()) {
//                System.out.println("x(" + counter + "): " + xRegister);
                int renderPixel = counter % 40;
                if (counter != 0 && renderPixel == 0) {
                    System.out.println();
                }
                if (xRegister-1 <= renderPixel && renderPixel <= xRegister+1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
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
            System.out.println();

            return 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
