import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day8Part2 {
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
        visible.add(new ArrayList<>());
        for (int i=0; i<grid.get(0).size(); ++i) {
            visible.get(0).add(0);
        }
        for (int i=1; i<grid.size()-1; ++i) {
            visible.add(new ArrayList<>());
            visible.get(i).add(0);
            for (int j=1; j<grid.get(i).size()-1; ++j) {
                visible.get(i).add(1);
            }
            visible.get(i).add(0);
        }
        visible.add(new ArrayList<>());
        for (int i=0; i<grid.get(0).size(); ++i) {
            visible.get(visible.size()-1).add(0);
        }
        //System.out.println(grid);
        System.out.println("------------");
        for (List<Integer> row : grid) {
            System.out.println(row);
        }
        System.out.println("------------");
        approachLeft(grid, visible);
        System.out.println("left------------");
        for (List<Integer> row : visible) {
            System.out.println(row);
        }
        System.out.println("left------------");
        approachRight(grid, visible);
        System.out.println("right------------");
        for (List<Integer> row : visible) {
            System.out.println(row);
        }
        System.out.println("right------------");
        approachTop(grid, visible);
        System.out.println("top------------");
        for (List<Integer> row : visible) {
            System.out.println(row);
        }
        System.out.println("top------------");
        approachBottom(grid, visible);
        //System.out.println(visible);
        System.out.println("bottom------------");
        for (List<Integer> row : visible) {
            System.out.println(row);
        }
        System.out.println("bottom------------");

        return findMax(visible);
    }

    private static int findMax(List<List<Integer>> visible) {
        int max = 0;
        for (List<Integer> row : visible) {
            for (int x : row) {
                max = Math.max(x, max);
            }
        }
        return max;
    }

    private static void approachLeft(List<List<Integer>> grid, List<List<Integer>> visible) {
        for (int i=0; i<grid.size(); ++i) {
            int[] highest = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//            if (i == 4) {
//                System.out.println(Arrays.toString(highest));
//                System.out.println("---------");
//            }
            for (int j=0; j<grid.get(i).size(); ++j) {
                int closestBlocker = 0;
                for (int k=grid.get(i).get(j); k<highest.length; ++k) {
                    closestBlocker = Math.max(closestBlocker, highest[k]);
                }
                int distance = j - closestBlocker;
                distance = 0 < distance ? distance : 1;
//                if (i == 3) {
//                    System.out.println("closest(" + j + "): " + closestBlocker);
//                    //System.out.println(Arrays.toString(highest));
//                    System.out.println("visible.get().get(): " + visible.get(i).get(j));
//                    System.out.println("distance: " + distance);
//                }
                visible.get(i).set(j, visible.get(i).get(j) * distance);
//                if (visible.get(3).get(4) == 3) {
//                    System.out.println("changes");
//                }
                highest[grid.get(i).get(j)] = j;
//                if (i == 3) {
//                    System.out.println("grid: " + grid.get(i).get(j));
//                    System.out.println(Arrays.toString(highest));
//                }
            }
        }
    }
    private static void approachRight(List<List<Integer>> grid, List<List<Integer>> visible) {
        for (int i=0; i<grid.size(); ++i) {
            int[] highest = new int[]{grid.get(i).size()-1, grid.get(i).size()-1, grid.get(i).size()-1,
                    grid.get(i).size()-1, grid.get(i).size()-1, grid.get(i).size()-1,
                    grid.get(i).size()-1, grid.get(i).size()-1, grid.get(i).size()-1, grid.get(i).size()-1};
            for (int j=grid.get(i).size()-1; 0<=j; --j) {
                int closestBlocker = grid.get(i).size()-1;
                for (int k=grid.get(i).get(j); k<highest.length; ++k) {
                    closestBlocker = Math.min(closestBlocker, highest[k]);
                }
                if (i == 4 && j == 12) {
                    System.out.println(Arrays.toString(highest));
                }
                int distance = closestBlocker - j;
                distance = 0 < distance ? distance : 1;
                visible.get(i).set(j, visible.get(i).get(j) * distance);
                highest[grid.get(i).get(j)] = j;
            }
        }
    }
    private static void approachTop(List<List<Integer>> grid, List<List<Integer>> visible) {
        for (int j=0; j<grid.get(0).size(); ++j) {
            int[] highest = new int[10];
            for (int i=0; i<grid.size(); ++i) {
                int closestBlocker = 0;
                for (int k=grid.get(i).get(j); k<highest.length; ++k) {
                    closestBlocker = Math.max(closestBlocker, highest[k]);
                }
                int distance = i - closestBlocker;
                distance = 0 < distance ? distance : 1;
                visible.get(i).set(j, visible.get(i).get(j) * distance);
                highest[grid.get(i).get(j)] = i;
            }
        }
    }
    private static void approachBottom(List<List<Integer>> grid, List<List<Integer>> visible) {
        for (int j=0; j<grid.get(0).size(); ++j) {
            int[] highest = new int[]{grid.size()-1, grid.size()-1, grid.size()-1,
                    grid.size()-1, grid.size()-1, grid.size()-1,
                    grid.size()-1, grid.size()-1, grid.size()-1,
                    grid.size()-1};
            for (int i=grid.size()-1; 0<=i; --i) {
                int closestBlocker = grid.size()-1;
                for (int k=grid.get(i).get(j); k<highest.length; ++k) {
                    closestBlocker = Math.min(closestBlocker, highest[k]);
                }
                int distance = closestBlocker - i;
                distance = 0 < distance ? distance : 1;
                visible.get(i).set(j, visible.get(i).get(j) * distance);
                highest[grid.get(i).get(j)] = i;
            }
        }
    }
}
