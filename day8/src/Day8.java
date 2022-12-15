import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day8 {
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        List<List<Integer>> grid = new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                List<Integer> row = new ArrayList<>();
                for (int i=0; i<line.length(); ++i) {
                    row.add(Character.getNumericValue(line.charAt(i)));
                }
                grid.add(row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<List<Integer>> visible = new ArrayList<>();
        for (int i=0; i<grid.size(); ++i) {
            visible.add(new ArrayList<>());
            for (int j=0; j<grid.get(i).size(); ++j) {
                visible.get(i).add(0);
            }
        }
        approachLeft(grid, visible);
        approachRight(grid, visible);
        approachTop(grid, visible);
        approachBottom(grid, visible);
        System.out.println(grid);
        System.out.println(visible);

        return countVisible(visible);
    }

    private static int countVisible(List<List<Integer>> visible) {
        int count = 0;
        for (List<Integer> row : visible) {
            for (int x : row) {
                count += x;
            }
        }
        return count;
    }

    private static void approachLeft(List<List<Integer>> grid, List<List<Integer>> visible) {
        for (int i=0; i<grid.size(); ++i) {
            int highest = -1;
            for (int j=0; j<grid.get(i).size(); ++j) {
                if (highest < grid.get(i).get(j)) {
                    visible.get(i).set(j, 1);
                    highest = grid.get(i).get(j);
                }
            }
        }
    }
    private static void approachRight(List<List<Integer>> grid, List<List<Integer>> visible) {
        for (int i=0; i<grid.size(); ++i) {
            int highest = -1;
            for (int j=grid.get(i).size()-1; 0<=j; --j) {
                if (highest < grid.get(i).get(j)) {
                    visible.get(i).set(j, 1);
                    highest = grid.get(i).get(j);
                }
            }
        }
    }
    private static void approachTop(List<List<Integer>> grid, List<List<Integer>> visible) {
        for (int j=0; j<grid.get(0).size(); ++j) {
            int highest = -1;
            for (int i=0; i<grid.size(); ++i) {
                if (highest < grid.get(i).get(j)) {
                    visible.get(i).set(j, 1);
                    highest = grid.get(i).get(j);
                }
            }
        }
    }
    private static void approachBottom(List<List<Integer>> grid, List<List<Integer>> visible) {
        for (int j=0; j<grid.get(0).size(); ++j) {
            int highest = -1;
            for (int i=grid.size()-1; 0<=i; --i) {
                if (highest < grid.get(i).get(j)) {
                    visible.get(i).set(j, 1);
                    highest = grid.get(i).get(j);
                }
            }
        }
    }
}
