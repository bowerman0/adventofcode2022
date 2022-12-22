import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day14 {
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                for (int pos=0; pos<line.length(); ) {
                    int commaPos = findComma(line, pos);
                    int x = Integer.parseInt(line.substring(pos, commaPos).trim());
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x);
                    int dashPos = findDash(line, pos);
                    int y = Integer.parseInt(line.substring(commaPos+1, dashPos).trim());
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                    pos = dashPos + 2; // skip past "->"
                }
            }

            System.out.println("X: " + minX + "," + maxX);
            System.out.println("Y: " + minY + "," + maxY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[][] grid = new int[maxY+1][maxX-minX+1];
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                int[] previous = null;
                for (int pos=0; pos<line.length(); ) {
                    int commaPos = findComma(line, pos);
                    int x = Integer.parseInt(line.substring(pos, commaPos).trim());
                    int dashPos = findDash(line, pos);
                    int y = Integer.parseInt(line.substring(commaPos + 1, dashPos).trim());
                    pos = dashPos + 2; // skip past "->"
                    if (previous == null) {
                        previous = new int[]{y,x};
                    } else {
//                        System.out.println("previous: " + Arrays.toString(previous));
                        for(int i=previous[1]-minX; i<=x-minX; ++i) {
//                            System.out.println("+x: " + (y) + "," + i);
                            grid[y][i] = -1;
                        }
                        for(int i=previous[1]-minX; x-minX<=i; --i) {
//                            System.out.println("-x: " + (y) + "," + i);
                            grid[y][i] = -1;
                        }
                        for(int i=previous[0]; i<=y; ++i) {
//                            System.out.println("+y: " + i + "," + (x-minX));
                            grid[i][x-minX] = -1;
                        }
                        for(int i=previous[0]; y<=i; --i) {
//                            System.out.println("-y: " + i + "," + (x-minX));
                            grid[i][x-minX] = -1;
                        }
                        previous[0] = y;
                        previous[1] = x;
                    }
                }
            }

//            for (int[] row : grid) {
//                System.out.println(Arrays.toString(row));
//            }
            return computeStacks(grid, minX);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int findDash(String line, int pos) {
        int lineEnd = line.indexOf('-', pos);
        return lineEnd != -1 ? lineEnd : line.length();
    }
    private static int findComma(String line, int pos) {
        int lineEnd = line.indexOf(',', pos);
        return lineEnd != -1 ? lineEnd : line.length();
    }

    private static int computeStacks(int[][] grid, int minX) {
        for (int count=1; count<10_000; ++count) {
//            System.out.println("count: " + count);
            for (int x=500-minX, y=0; grid[y][x] != -1; ) {
//                System.out.println("x,y: " + x + "," + y);
                if (grid.length <= y+1) {
                    return count-1;
                } else if (grid[y+1][x] == -1 && (x-1 < 0 || grid[y].length <= x+1)) {
                    return count-1;
                } else if (grid[y+1][x] != -1) {
                    ++y;
                } else if (grid[y+1][x-1] != -1) {
                    --x;
                    ++y;
                } else if (grid[y+1][x+1] != -1) {
                    ++x;
                    ++y;
                } else {
                    grid[y][x] = -1;
                    //break;
                }
//                for (int[] row : grid) {
//                    System.out.println(Arrays.toString(row));
//                }
            }
        }
        throw new IllegalArgumentException("too many iterations!");
    }
}
