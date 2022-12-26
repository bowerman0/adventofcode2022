import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day15part2 {
    private static final int TUNING_X_MULT = 4_000_000;
    private static final int MAX_BEACON_Y = 4_000_000;
    //private static final int MAX_BEACON_Y = 20;
    private static final int MAX_BEACON_X = MAX_BEACON_Y;


    static class Sensor {
        Sensor(int sensorX, int sensorY, int beaconX, int beaconY) {
            this.sensorX = sensorX;
            this.sensorY = sensorY;
            this.beaconX = beaconX;
            this.beaconY = beaconY;
            totalDistance = getTotalDistance();
            minY = getMinY();
            maxY = getMaxY();
//            frontier = computeFrontier();
        }
        int sensorX;
        int sensorY;
        int beaconX;
        int beaconY;

        int totalDistance;
        int minY;
        int maxY;

//        Set<List<Integer>> frontier;
//
//        Set<List<Integer>> computeFrontier() {
//            Set<List<Integer>> frontier = new HashSet<>();
//            frontier.add(List.of(sensorX, minY-1));
//            frontier.add(List.of(sensorX, maxY+1));
//            for (int i=minY; i<=maxY; ++i) {
//                frontier.add(List.of(getMaxX(i)+1, i));
//                frontier.add(List.of(getMinX(i)-1, i));
//            }
//
//            return frontier;
//        }

        boolean hits(int x, int y) {
            if (minY <= y && y <= maxY) {
                return getMinX(y) <= x && x <= getMaxX(y);
            } else {
//                System.out.println("out of range y: " + y);
                return false;
            }
        }

        int getTotalDistance() {
            int dx = Math.abs(beaconX - sensorX);
            int dy = Math.abs(beaconY - sensorY);
            return dx+dy;
        }

        int getMinY() {
            return sensorY-totalDistance;
        }

        int getMaxY() {
            return sensorY+totalDistance;
        }

        int getMinX(int y) {
            if (y < minY || maxY < y) {
                throw new IllegalArgumentException("y out of range: " + y);
            }
            int distanceY = Math.abs(y-sensorY);
            int distanceX = totalDistance-distanceY;
            return sensorX - distanceX;
        }
        int getMaxX(int y) {
            if (y < minY || maxY < y) {
                throw new IllegalArgumentException("y out of range: " + y);
            }
            int distanceY = Math.abs(y-sensorY);
            int distanceX = totalDistance-distanceY;
            return sensorX+distanceX;
        }
    }

    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static long read() {
        Pattern pattern = Pattern.compile(
                "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            List<Sensor> sensors = new ArrayList<>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("no match found on line: " + line);
                }
                Sensor sensor = new Sensor(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                sensors.add(sensor);
            }
//            for (int i=0; i<MAX_BEACON_Y; ++i) {
//                for (int j=0; j<MAX_BEACON_X; ++j) {
//                    boolean hits = false;
//                    for (Sensor s : sensors) {
//                        hits = s.hits(j, i);
//                        if (hits) {
//                            break;
//                        }
//                    }
//                    System.out.print(hits ? "#" : ".");
//                }
//                System.out.println();
//            }
            System.out.println("sensors");
            for (int i=0; i<sensors.size(); ++i) {
                for (int y = Math.max(0, sensors.get(i).minY); y <= Math.min(MAX_BEACON_Y, sensors.get(i).maxY); ++y) {
                    boolean hitsOne = false;
                    int x1 = sensors.get(i).getMaxX(y) + 1;
                    for (int k=0; k<sensors.size() && !hitsOne; ++k) {
                        if (k == i) {
                            continue;
                        }
                        if (0 <= x1 && x1 <= MAX_BEACON_X) {
                            hitsOne = sensors.get(k).hits(x1, y);
                        } else {
                            hitsOne = true;
                        }
                    }
                    if (!hitsOne) {
                        System.out.println("not hit 1: " + x1 + "," + y);
                        return (((long)x1) * TUNING_X_MULT) + y;
                    }

                    boolean hitsTwo = false;
                    int x2 = sensors.get(i).getMinX(y) - 1;
                    for (int k=0; k<sensors.size() && !hitsTwo; ++k) {
                        if (k == i) {
                            continue;
                        }
                        if (0 <= x2 && x2 <= MAX_BEACON_X) {
                            hitsTwo = sensors.get(k).hits(x2, y);
                        } else {
                            hitsTwo = true;
                        }
                    }
                    if (!hitsTwo) {
                        System.out.println("not hit 2: " + x2 + "," + y);
                        return (((long)x2) * TUNING_X_MULT) + y;
                    }
                }
            }
            System.out.println("sensors c");
            return 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
