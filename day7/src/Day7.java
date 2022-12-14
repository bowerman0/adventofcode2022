import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    private static final Pattern pattern = Pattern.compile("(\\d+) ([\\w\\-. ]+)");
    private static final Directory root = new Directory(null,"/", 0);
    private static Directory current = root;

    private static class Directory {
        int size;
        String path;
        Directory parent;
        List<Directory> children;

        Directory(Directory parent, String path, int size) {
            this.parent = parent;
            if (this.parent != null) {
                this.parent.addChild(this);
            }
            this.path = path;
            this.size = size;
            this.children = new ArrayList<>();
        }
        public int getSize() {
            int totalSize = size;
            for (Directory child : children) {
                totalSize += child.getSize();
            }
            return totalSize;
        }
//
//        public String getPath() {
//            if (parent == null) {
//                return path;
//            }
//
//            return parent.getPath() + path + "/";
//        }

        public List<Directory> getChildren() {
            return children;
        }

        public Directory getParent() {
            return parent;
        }

        public void addChild(Directory child) {
            this.children.add(child);
        }

        public boolean isDir() {
            return size == 0;
        }
    }
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.isEmpty()) {
                    continue;
                }

                switch (line.charAt(0)) {
                    case '$':
                        processCommand(line);
                        break;
                    case 'd':
                        // skip
                        break;
                    default:
                        new Directory(current, line, processDirectoryContents(line));
                        break;
                }
            }


            //return addToTotal(root);
            System.out.println("root size: " + root.getSize());
            int freeSpace = 70_000_000 - root.getSize();
            System.out.println("free     : " + freeSpace);
            int target = 30_000_000 - freeSpace;
            System.out.println("target   : " + target);
            return findSmallest(root, target);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static private int findSmallest(Directory root, int min) {
        int smallestSub = Integer.MAX_VALUE;
        for (Directory d : root.getChildren()) {
            if (d.isDir()) {
                smallestSub = Math.min(smallestSub, findSmallest(d, min));
            }
        }
        if (min <= root.getSize()) {
            return Math.min(root.getSize(), smallestSub);
        }
        return smallestSub;
    }

//    static private int addToTotal(Directory root) {
//        //System.out.println("location: " + root.getPath() + " (" + root.getSize() + ")" );
//        int total = root.getSize();
//        int sum = 0;
//        if (total < 100_000) {
//            sum = total;
//        }
//        for (Directory d : root.getChildren()) {
//            if (d.isDir()) {
//                sum += addToTotal(d);
//            }
//        }
//
//        return sum;
//    }

    private static int processDirectoryContents(String line) {
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("no match found on line: " + line);
        }

        return Integer.parseInt(matcher.group(1));
    }
    private static void processCommand(String line) {
        switch (line.charAt(2)) {
            case 'c':
                processCd(line);
                break;
            case 'l':
                // skip
                break;
            default:
                throw new IllegalArgumentException("unknown command.");
        }
    }

    private static void processCd(String line) {
        switch (line.charAt(5)) {
            case '/':
                current = root;
                break;
            case '.':
                current = current.getParent();
                break;
            default:
                current = new Directory(current, line.substring(5), 0);
                break;
        }
//        System.out.println("location: " + current.getPath());
    }
}
