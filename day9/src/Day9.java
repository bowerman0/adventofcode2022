import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9 {
    static class Rope {
        List<Integer> head;
        List<Integer> tail;
    }
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            Set<List<Integer>> tailPositions = new HashSet<>();
            Rope rope = new Rope();
            rope.head = List.of(0,0);
            rope.tail = List.of(0,0);
            tailPositions.add(rope.tail);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                switch (line.charAt(0)) {
                    case 'L':
                        for (int i=0; i<Integer.parseInt(line.substring(2)); ++i) {
                            moveLeft(tailPositions, rope);
                            if (Math.abs(rope.head.get(0) - rope.tail.get(0)) > 1 ||
                                    Math.abs(rope.head.get(1) - rope.tail.get(1)) > 1) {
                                System.out.println(rope.head + ", " + rope.tail);
                            }
                        }
                        break;
                    case 'R':
                        for (int i=0; i<Integer.parseInt(line.substring(2)); ++i) {
                            moveRight(tailPositions, rope);
                            if (Math.abs(rope.head.get(0) - rope.tail.get(0)) > 1 ||
                                    Math.abs(rope.head.get(1) - rope.tail.get(1)) > 1) {
                                System.out.println(rope.head + ", " + rope.tail);
                            }
                        }
                        break;
                    case 'U':
                        for (int i=0; i<Integer.parseInt(line.substring(2)); ++i) {
                            moveUp(tailPositions, rope);
                            if (Math.abs(rope.head.get(0) - rope.tail.get(0)) > 1 ||
                                    Math.abs(rope.head.get(1) - rope.tail.get(1)) > 1) {
                                System.out.println(rope.head + ", " + rope.tail);
                            }
                        }
                        break;
                    case 'D':
                        for (int i=0; i<Integer.parseInt(line.substring(2)); ++i) {
                            moveDown(tailPositions, rope);
                            if (Math.abs(rope.head.get(0) - rope.tail.get(0)) > 1 ||
                                    Math.abs(rope.head.get(1) - rope.tail.get(1)) > 1) {
                                System.out.println(rope.head + ", " + rope.tail);
                            }
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("wrong direction: " + line);
                }

            }

            System.out.println(tailPositions);
            return tailPositions.size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void moveLeft(Set<List<Integer>> tailPositions, Rope rope) {
        rope.head = List.of(rope.head.get(0)-1, rope.head.get(1));
        if (rope.tail.get(0) - rope.head.get(0) > 1) {
            switch (rope.head.get(1) - rope.tail.get(1)) {
                case -1: // move down
                    rope.tail = List.of(rope.tail.get(0)-1, rope.tail.get(1)-1);
                    break;
                case 1: // move up
                    rope.tail = List.of(rope.tail.get(0)-1, rope.tail.get(1)+1);
                    break;
                default:
                    rope.tail = List.of(rope.tail.get(0)-1, rope.tail.get(1));
                    break;
            }
            tailPositions.add(rope.tail);
        }
    }
    private static void moveRight(Set<List<Integer>> tailPositions, Rope rope) {
        rope.head = List.of(rope.head.get(0)+1, rope.head.get(1));
        if (rope.head.get(0) - rope.tail.get(0) > 1) {
            switch (rope.head.get(1) - rope.tail.get(1)) {
                case -1: // move down
                    rope.tail = List.of(rope.tail.get(0)+1, rope.tail.get(1)-1);
                    break;
                case 1: // move up
                    rope.tail = List.of(rope.tail.get(0)+1, rope.tail.get(1)+1);
                    break;
                default:
                    rope.tail = List.of(rope.tail.get(0)+1, rope.tail.get(1));
                    break;
            }
            tailPositions.add(rope.tail);
        }
    }

    private static void moveUp(Set<List<Integer>> tailPositions, Rope rope) {
        rope.head = List.of(rope.head.get(0), rope.head.get(1)+1);
        if (rope.head.get(1) - rope.tail.get(1) > 1) {
            switch (rope.head.get(0) - rope.tail.get(0)) {
                case -1: // move left
                    rope.tail = List.of(rope.tail.get(0)-1, rope.tail.get(1)+1);
                    break;
                case 1: // move right
                    rope.tail = List.of(rope.tail.get(0)+1, rope.tail.get(1)+1);
                    break;
                default:
                    rope.tail = List.of(rope.tail.get(0), rope.tail.get(1)+1);
                    break;
            }
            tailPositions.add(rope.tail);
        }
    }

    private static void moveDown(Set<List<Integer>> tailPositions, Rope rope) {
        rope.head = List.of(rope.head.get(0), rope.head.get(1)-1);
        if (rope.tail.get(1) - rope.head.get(1) > 1) {
            switch (rope.head.get(0) - rope.tail.get(0)) {
                case -1: // move left
                    rope.tail = List.of(rope.tail.get(0)-1, rope.tail.get(1)-1);
                    break;
                case 1: // move right
                    rope.tail = List.of(rope.tail.get(0)+1, rope.tail.get(1)-1);
                    break;
                default:
                    rope.tail = List.of(rope.tail.get(0), rope.tail.get(1)-1);
                    break;
            }
            tailPositions.add(rope.tail);
        }
    }
}
