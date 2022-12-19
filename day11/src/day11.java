import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class day11 {

    public static final int TOTAL_ROUNDS = 10_000;

    enum Operation {
        MULTIPLY,
        ADD,
        SQUARE
    }

    private static final int[] TESTS = new int[] {2, 3, 5, 7, 11, 13, 17, 19};
    static class Worry {
        int[] worryRings = new int[20];

        @Override
        public String toString() {
            return "Worry{" +
                    "worryRings=" + Arrays.toString(worryRings) +
                    '}';
        }
    }
    static class Monkey {
        Deque<Worry> items;
        Operation operation;
        int operationAmount;
        int test;
        int trueMonkey;
        int falseMonkey;
        int count;

        @Override
        public String toString() {
            return "Monkey{" +
                    "operation=" + operation +
                    ", operationAmount=" + operationAmount +
                    ", test=" + test +
                    ", trueMonkey=" + trueMonkey +
                    ", falseMonkey=" + falseMonkey +
                    ", count=" + count +
                    ", items=" + items +
                    '}';
        }
    }
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static long read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            List<Monkey> monkeys = new ArrayList<>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Monkey monkey = new Monkey();
                monkeys.add(monkey);

                line = reader.readLine();
                String[] itemStrings = line.substring(17).split(",");
                monkey.items = Arrays.stream(itemStrings).map(s -> {
                    int value = Integer.parseInt(s.trim());
                    Worry worry = new Worry();
                    for (int test : TESTS) {
                        worry.worryRings[test] = value % test;
                    }
                    return worry;
                }).collect(Collectors.toCollection(ArrayDeque::new));

                line = reader.readLine();
                char op = line.charAt(23);
                switch (op) {
                    case '+':
                        monkey.operation = Operation.ADD;
                        monkey.operationAmount = Integer.parseInt(line.substring(24).trim());
                        break;
                    case '*':
                        if (line.charAt(25) == 'o') {
                            monkey.operation = Operation.SQUARE;
                        } else {
                            monkey.operation = Operation.MULTIPLY;
                            monkey.operationAmount = Integer.parseInt(line.substring(24).trim());
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("not found: " + op);
                }

                line = reader.readLine();
                monkey.test = Integer.parseInt(line.substring(21).trim());

                line = reader.readLine();
                monkey.trueMonkey = Integer.parseInt(line.substring(29).trim());

                line = reader.readLine();
                monkey.falseMonkey = Integer.parseInt(line.substring(30).trim());

                System.out.println(monkey);
                reader.readLine(); // newline
            }

            for (int round = 0; round<TOTAL_ROUNDS; ++round) {
                for (Monkey monkey : monkeys) {
                    while (!monkey.items.isEmpty()) {
                        Worry worry = monkey.items.pollFirst();
                        if (worry == null) {
                            throw new IllegalArgumentException("bad monkey!");
                        }
                        ++monkey.count;
                        if (monkey.count < 0) {
                            throw new RuntimeException("Worry: " + worry + "Overflow: " + monkey);
                        }
                        switch (monkey.operation) {
                            case ADD:
                                for (int test : TESTS) {
                                    worry.worryRings[test] += monkey.operationAmount;
                                    worry.worryRings[test] %= test;
                                }
                                break;
                            case MULTIPLY:
                                for (int test : TESTS) {
                                    worry.worryRings[test] *= monkey.operationAmount;
                                    worry.worryRings[test] %= test;
                                }
                                break;
                            case SQUARE:
                                for (int test : TESTS) {
                                    worry.worryRings[test] *= worry.worryRings[test];
                                    worry.worryRings[test] %= test;
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Unexpected operation: " + monkey.operation);
                        }

                        if (worry.worryRings[monkey.test] == 0) {
                            monkeys.get(monkey.trueMonkey).items.offerLast(worry);
                        } else {
                            monkeys.get(monkey.falseMonkey).items.offerLast(worry);
                        }
                    }
                }
            }

            monkeys.sort(Comparator.comparingInt(a -> a.count));
            for (Monkey monkey : monkeys) {
                System.out.println(monkey);
                System.out.println("count: " + monkey.count);
            }

            return ((long)monkeys.get(monkeys.size()-2).count) * monkeys.get(monkeys.size()-1).count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
