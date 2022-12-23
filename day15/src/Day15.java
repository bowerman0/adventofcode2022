import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {

    private static final int TARGET_Y = 2_000_000;

    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        Pattern pattern = Pattern.compile(
                "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            List<List<Integer>> ranges = new ArrayList<>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("no match found on line: " + line);
                }
                int sensorX = Integer.parseInt(matcher.group(1));
                int sensorY = Integer.parseInt(matcher.group(2));
                int beaconX = Integer.parseInt(matcher.group(3));
                int beaconY = Integer.parseInt(matcher.group(4));
                int dx = Math.abs(beaconX - sensorX);
                int dy = Math.abs(beaconY - sensorY);
                int distanceY = Math.abs(TARGET_Y - sensorY);
                int distanceX = dx+dy-distanceY;
                if (distanceX >0) {
                    List<Integer> range = Arrays.asList(sensorX - distanceX, sensorX + distanceX);
                    ranges.add(range);
                }
            }
            ranges.sort(Comparator.comparingInt(a -> a.get(0)));
            System.out.println(ranges);
            int count = 0;
            for (int i=0; i<ranges.size(); ++i) {
                if (ranges.get(i).get(0) > ranges.get(i).get(1)) {
                    if (i+1 < ranges.size() && ranges.get(i).get(0) > ranges.get(i+1).get(0)) {
                        ranges.get(i+1).set(0, ranges.get(i).get(0));
                    }
                    continue;
                }
                count += ranges.get(i).get(1) - ranges.get(i).get(0);
                if (i+1 < ranges.size() && ranges.get(i).get(1) > ranges.get(i+1).get(0)) {
                    ranges.get(i+1).set(0, ranges.get(i).get(1));
                }
            }

            return count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
