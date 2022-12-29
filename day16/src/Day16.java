import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 {
    static class Valve {
        String id;
        int flowRate;
        Map<String, Integer> valveDistance;

        @Override
        public String toString() {
            return "Valve{" +
                    "id='" + id + '\'' +
                    ", flowRate=" + flowRate +
                    ", valveDistance=" + valveDistance +
                    '}';
        }
    }
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            Map<String, Valve> valveMap = new HashMap<>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Valve v = new Valve();
                v.id = line.substring(6,8);
                int pressureEnd = line.indexOf(';', 23);
                v.flowRate = Integer.parseInt(line.substring(23, pressureEnd));
                String tunnels = line.substring(pressureEnd+1);
                int listPos = tunnels.charAt(22) == 's' ? 24 : 23;
                String list = line.substring(pressureEnd+1).substring(listPos);
                v.valveDistance = new HashMap<>();
                for (int i=0; i<list.length(); i+=4) {
                    v.valveDistance.put(list.substring(i,i+2), 1);
                }
                valveMap.put(v.id, v);
            }

            List<Valve> valves = new ArrayList<>(valveMap.values());
            computeShortestPaths(valves);
            System.out.println(valves);
            List<String> zeroValveIds = valves.stream().filter(v -> v.flowRate == 0).map(v -> v.id).collect(Collectors.toList());
            Valve start = valveMap.get("AA");
            for (String valveId : zeroValveIds) {
                valveMap.remove(valveId);
            }
            for (Valve v : valves) {
                for (String valveId : zeroValveIds) {
                    v.valveDistance.remove(valveId);
                }
            }
            System.out.println(start);
            valves = new ArrayList<>(valveMap.values());
            System.out.println(valves);
            return computeOptimalPath(valveMap, new HashMap<>(), start, 30);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static int computeOptimalPath(Map<String, Valve> valveMap, Map<String, Boolean> visited, Valve current, int remaining) {
        if (remaining <= 0 || Boolean.TRUE.equals(visited.put(current.id, true))) {
            //System.out.println();
            return 0;
        }
        int subtotalWith = 0;
        for (Map.Entry<String, Integer> valvePath : current.valveDistance.entrySet()) {
            Valve v = valveMap.get(valvePath.getKey());
            int cost = remaining - current.valveDistance.get(v.id) - 1;
            if (0 < cost) {
                subtotalWith = Math.max(subtotalWith,
                        computeOptimalPath(valveMap, visited, v, cost));
            }
        }
        subtotalWith += current.flowRate * remaining;
        visited.put(current.id, false);

        return subtotalWith;
    }

    private static void computeShortestPaths(List<Valve> valveList) {
        for (int i=0; i<valveList.size(); ++i) {
            for (int j=0; j<valveList.size(); ++j) {
                for (int k=0; k<valveList.size(); ++k) {
                    Integer jiPath = valveList.get(j).valveDistance.get(valveList.get(i).id);
                    Integer ikPath = valveList.get(i).valveDistance.get(valveList.get(k).id);
                    if (jiPath != null && ikPath != null) {
                        int distance = jiPath + ikPath;
                        if (valveList.get(j).valveDistance.get(valveList.get(k).id) == null ||
                                distance < valveList.get(j).valveDistance.get(valveList.get(k).id)) {
                            valveList.get(j).valveDistance.put(valveList.get(k).id, distance);
                        }
                    }
                }
            }
        }
    }
}
