import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day9 {
    private static final int TOTAL_KNOTS = 10;
    static class Rope {
        List<List<Integer>> knots;
    }
    public static void main(String[] args) {
        System.out.println("total    : " + read());
        System.out.println("target(2): 5710");
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            Set<List<Integer>> tailPositions = new HashSet<>();
            Rope rope = new Rope();
            rope.knots = new ArrayList<>(Collections.nCopies(TOTAL_KNOTS, List.of(0,0)));
            tailPositions.add(rope.knots.get(TOTAL_KNOTS-1));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                switch (line.charAt(0)) {
                    case 'L':
                        for (int i=0; i<Integer.parseInt(line.substring(2)); ++i) {
                            moveLeft(tailPositions, rope);
                        }
                        break;
                    case 'R':
                        for (int i=0; i<Integer.parseInt(line.substring(2)); ++i) {
                            moveRight(tailPositions, rope);
                        }
                        break;
                    case 'U':
                        for (int i=0; i<Integer.parseInt(line.substring(2)); ++i) {
                            moveUp(tailPositions, rope);
                        }
                        break;
                    case 'D':
                        for (int i=0; i<Integer.parseInt(line.substring(2)); ++i) {
                            moveDown(tailPositions, rope);
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
        rope.knots.set(0, List.of(rope.knots.get(0).get(0)-1, rope.knots.get(0).get(1)));
        for (int i=1; i < rope.knots.size() && gap(rope, i); ++i) {
            int xIncr = xIncrement(rope, i);
            int yIncr = yIncrement(rope, i);
            rope.knots.set(i, List.of(rope.knots.get(i).get(0)+xIncr, rope.knots.get(i).get(1)+yIncr));
        }
        tailPositions.add(rope.knots.get(TOTAL_KNOTS-1));
    }

    private static void moveRight(Set<List<Integer>> tailPositions, Rope rope) {
        rope.knots.set(0, List.of(rope.knots.get(0).get(0)+1, rope.knots.get(0).get(1)));
        for (int i=1; i < rope.knots.size() && gap(rope, i); ++i) {
            int xIncr = xIncrement(rope, i);
            int yIncr = yIncrement(rope, i);
            rope.knots.set(i, List.of(rope.knots.get(i).get(0)+xIncr, rope.knots.get(i).get(1)+yIncr));
        }
        tailPositions.add(rope.knots.get(TOTAL_KNOTS-1));
    }

    private static void moveUp(Set<List<Integer>> tailPositions, Rope rope) {
        rope.knots.set(0, List.of(rope.knots.get(0).get(0), rope.knots.get(0).get(1)+1));
        for (int i=1; i < rope.knots.size() && gap(rope, i); ++i) {
            int xIncr = xIncrement(rope, i);
            int yIncr = yIncrement(rope, i);
            rope.knots.set(i, List.of(rope.knots.get(i).get(0)+xIncr, rope.knots.get(i).get(1)+yIncr));
        }
        tailPositions.add(rope.knots.get(TOTAL_KNOTS-1));
    }

    private static void moveDown(Set<List<Integer>> tailPositions, Rope rope) {
        rope.knots.set(0, List.of(rope.knots.get(0).get(0), rope.knots.get(0).get(1)-1));
        for (int i=1; i < rope.knots.size() && gap(rope, i); ++i) {
            int xIncr = xIncrement(rope, i);
            int yIncr = yIncrement(rope, i);
            rope.knots.set(i, List.of(rope.knots.get(i).get(0)+xIncr, rope.knots.get(i).get(1)+yIncr));
        }
        tailPositions.add(rope.knots.get(TOTAL_KNOTS-1));
    }

    private static int xIncrement(Rope rope, int tail) {
        int dx = rope.knots.get(tail-1).get(0) - rope.knots.get(tail).get(0);
        if (dx <= -1) {
            return -1;
        } else if (dx >= 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int yIncrement(Rope rope, int tail) {
        int dy = rope.knots.get(tail-1).get(1) - rope.knots.get(tail).get(1);
        if (dy <= -1) {
            return -1;
        } else if (dy >= 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private static boolean gap(Rope rope, int tail) {
        return gapAbove(rope, tail) || gapBelow(rope, tail) || gapLeft(rope, tail) || gapRight(rope, tail);
    }
    private static boolean gapRight(Rope rope, int tail) {
        return rope.knots.get(tail-1).get(0) - rope.knots.get(tail).get(0) > 1;
    }
    private static boolean gapLeft(Rope rope, int tail) {
        return rope.knots.get(tail).get(0) - rope.knots.get(tail-1).get(0) > 1;
    }
    private static boolean gapAbove(Rope rope, int tail) {
        return rope.knots.get(tail-1).get(1) - rope.knots.get(tail).get(1) > 1;
    }
    private static boolean gapBelow(Rope rope, int tail) {
        return rope.knots.get(tail).get(1) - rope.knots.get(tail-1).get(1) > 1;
    }
}
