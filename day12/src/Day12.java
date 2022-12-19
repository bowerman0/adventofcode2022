import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day12 {
    private static final int START = 'S'-'a';
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            List<List<Integer>> heights = new ArrayList<>();
            int x = 0;
            int y = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                heights.add(new ArrayList<>());
                for (int i=0; i<line.length(); ++i) {
                    if (line.charAt(i) == 'E') {
                        x = heights.size()-1;
                        y = i;
//                        System.out.println("E: " + x + "," + y);
                    }
                    heights.get(heights.size()-1).add(line.charAt(i) - 'a');
                }
            }

//            System.out.println(heights);

            return BFS(x, y, heights);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int BFS(int x, int y, List<List<Integer>> heights) {
        Deque<List<Integer>> stack = new ArrayDeque<>();
        stack.offerLast(List.of(x,y));
        Set<List<Integer>> visited = new HashSet<>();
        for (int level=0; !stack.isEmpty(); ++level) {
            int levelSize = stack.size();
            for (int i=0; i<levelSize; ++i) {
                //System.out.println("level,i: " + level +"," + i);
                List<Integer> pos = stack.pollFirst();
                if (pos == null) {
                    continue;
                }
                if (heights.get(pos.get(0)).get(pos.get(1)) == START) {
                    return level;
                }
                if (visited.contains(pos)) {
                    continue;
                }
                visited.add(pos);
                //System.out.println("pos: " + pos);
                if (pos.get(1)+1 < heights.get(pos.get(0)).size()) {
                    List<Integer> up = List.of(pos.get(0), pos.get(1) + 1);
                    if (!visited.contains(up) &&
                            (heights.get(up.get(0)).get(up.get(1)) == START ||
                                    heights.get(pos.get(0)).get(pos.get(1))-1 <= heights.get(up.get(0)).get(up.get(1)))) {
                        stack.offerLast(up);
                    }
                }

                if (0 <= pos.get(1)-1) {
                    List<Integer> down = List.of(pos.get(0), pos.get(1) - 1);
                    if (!visited.contains(down) &&
                            (heights.get(down.get(0)).get(down.get(1)) == START ||
                                    heights.get(pos.get(0)).get(pos.get(1))-1 <= heights.get(down.get(0)).get(down.get(1)))) {
                        stack.offerLast(down);
                    }
                }

                if (0 <= pos.get(0)-1) {
                    List<Integer> left = List.of(pos.get(0) - 1, pos.get(1));
                    if (!visited.contains(left) &&
                            (heights.get(left.get(0)).get(left.get(1)) == START ||
                                    heights.get(pos.get(0)).get(pos.get(1))-1 <= heights.get(left.get(0)).get(left.get(1)))) {
                        stack.offerLast(left);
                    }
                }

                if (pos.get(0)+1 < heights.size()) {
                    List<Integer> right = List.of(pos.get(0) + 1, pos.get(1));
                    if (!visited.contains(right) &&
                            (heights.get(right.get(0)).get(right.get(1)) == START ||
                                    heights.get(pos.get(0)).get(pos.get(1))-1 <= heights.get(right.get(0)).get(right.get(1)))) {
                        stack.offerLast(right);
                    }
                }
            }
        }
        return -1;
    }
}
