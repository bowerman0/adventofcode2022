import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13 {
    interface ElfListItem extends Comparable<ElfListItem> {

    }
    static class ElfList implements ElfListItem {
        List<ElfListItem> list;

        public ElfList() {
            this.list = new ArrayList<>();
        }

        @Override
        public int compareTo(ElfListItem o) {
            if (o instanceof ElfList) {
                return compareTo((ElfList)o);
            } else if (o instanceof ElfItem) {
                return compareTo((ElfItem)o);
            }
            throw new IllegalArgumentException("Not comparable. " + o);
        }

        public int compareTo(ElfList o) {
            for (int i=0; i<list.size() && i<o.list.size(); ++i) {
                int result = list.get(i).compareTo(o.list.get(i));
                if (0 != result) {
                    return result;
                }
            }
            return Integer.compare(list.size(), o.list.size());
        }

        public int compareTo(ElfItem o) {
            ElfList temp = new ElfList();
            temp.list.add(o);
            return compareTo(temp);
        }
    }
    static class ElfItem implements ElfListItem {
        int value;

        public ElfItem(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(ElfListItem o) {
            if (o instanceof ElfList) {
                return compareTo((ElfList)o);
            } else if (o instanceof ElfItem) {
                return compareTo((ElfItem)o);
            }
            throw new IllegalArgumentException("Uncomparable. " + o);
        }

        public int compareTo(ElfList o) {
            ElfList temp = new ElfList();
            temp.list.add(this);
            return temp.compareTo(o);
        }

        public int compareTo(ElfItem o) {
            return Integer.compare(value, o.value);
        }
    }
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            List<Integer> correct = new ArrayList<>();
            int pair = 1;
            for (String line = reader.readLine(); line != null; line = reader.readLine(), ++pair) {
                ElfListItem itemLeft = generateElfTree(line);

                String lineTwo = reader.readLine();
                ElfListItem itemRight = generateElfTree(lineTwo);

                if (itemLeft.compareTo(itemRight) <= 0) {
                    correct.add(pair);
                }

                reader.readLine(); // newline
            }

            System.out.println(correct);

            return correct.stream()
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ElfListItem generateElfTree(String line) {
        if (line.charAt(0) == '[') {
            return generateElfList(line);
        } else if (Character.isDigit(line.charAt(0))) {
            return generateElfItem(line);
        } else {
            throw new IllegalArgumentException("Bad line: " + line);
        }
    }
    private static int findMatchingBracket(String line, int pos) {
        if (line.charAt(pos) != '[') {
            throw new RuntimeException("Bad bracket check(" + pos + "): " + line);
        }
        int count = 1;
        for (int i=pos+1; i<line.length(); ++i) {
            if (line.charAt(i) == '[') {
                ++count;
            } else if (line.charAt(i) == ']') {
                --count;
            }

            if (count == 0) {
                return i;
            }
        }
        throw new RuntimeException("Bad bracket check(" + pos + "): " + line);
    }
    private static int findComma(String line, int pos) {
        int lineEnd = line.indexOf(',', pos);
        return lineEnd != -1 ? lineEnd : line.length();
    }
    private static ElfListItem generateElfList(String line) {
        if (line == null) {
            return null;
        } else if (line.isBlank() || line.isEmpty()) {
            return new ElfList();
        }

        ElfList elfList = new ElfList();
        for (int pos=0; pos<line.length(); ) {
            if (line.charAt(pos) == '[') {
                int end = findMatchingBracket(line, pos);
                ElfListItem list = generateElfList(line.substring(pos+1, end));
                if (list != null) {
                    elfList.list.add(list);
                }
                int next = findComma(line, end);
                pos += next;
            } else if (line.charAt(pos) == ']') {
                elfList.list.add(new ElfList());
                ++pos;
            } else if (Character.isDigit(line.charAt(pos))) {
                int end = findComma(line, pos);
                elfList.list.add(generateElfItem(line.substring(pos, end)));
                pos = end + 1;
            } else if (line.charAt(pos) == ',') {
                ++pos;
            } else {
                throw new IllegalArgumentException("Bad input at: " + pos);
            }
        }
        return elfList;
    }
    private static ElfListItem generateElfItem(String line) {
        if (line == null || line.isBlank() || line.isEmpty()) {
            return null;
        }

        if (!Character.isDigit(line.charAt(0))) {
            throw new RuntimeException("Bad input: " + line);
        }
        int itemEnd = line.indexOf(',');
        if (itemEnd == -1) {
            itemEnd = line.indexOf(']');
        }
        int itemNum = Integer.parseInt(line.substring(0, itemEnd != -1 ? itemEnd : line.length()));
        return new ElfItem(itemNum);
    }
}
