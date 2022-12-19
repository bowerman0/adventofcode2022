import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class day11 {
    enum Operation {
        MULTIPLY,
        ADD,
        SQUARE
    }
    static class Monkey {
        Deque<Integer> items;
        Operation operation;
        int operationAmount;
        int test;
        int trueMonkey;
        int falseMonkey;
        int count;

        @Override
        public String toString() {
            return "Monkey{" +
                    "items=" + items +
                    ", operation=" + operation +
                    ", operationAmount=" + operationAmount +
                    ", test=" + test +
                    ", trueMonkey=" + trueMonkey +
                    ", falseMonkey=" + falseMonkey +
                    ", count=" + count +
                    '}';
        }
    }
    public static void main(String[] args) {
        System.out.println("total: " + read());
    }

    private static int read() {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("input.txt"))) {
            List<Monkey> monkeys = new ArrayList<>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Monkey monkey = new Monkey();
                monkeys.add(monkey);

                line = reader.readLine();
                String[] itemStrings = line.substring(17).split(",");
                monkey.items = Arrays.stream(itemStrings).map(s -> Integer.parseInt(s.trim())).collect(Collectors.toCollection(ArrayDeque::new));

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

                System.out.println("Monkey:" + monkey);
                reader.readLine(); // newline
            }

            for (int round=0; round<20; ++round) {
                for (Monkey monkey : monkeys) {
                    while (!monkey.items.isEmpty()) {
                        Integer worryPointer = monkey.items.pollFirst();
                        if (worryPointer == null) {
                            throw new IllegalArgumentException("bad monkey!");
                        }
                        int worry = worryPointer;
                        ++monkey.count;
                        switch (monkey.operation) {
                            case ADD:
                                worry += monkey.operationAmount;
                                break;
                            case MULTIPLY:
                                worry *= monkey.operationAmount;
                                break;
                            case SQUARE:
                                worry *= worry;
                                break;
                            default:
                                throw new IllegalArgumentException("Unexpected operation: " + monkey.operation);
                        }

                        worry /= 3;

                        if (worry % monkey.test == 0) {
                            monkeys.get(monkey.trueMonkey).items.offerLast(worry);
                        } else {
                            monkeys.get(monkey.falseMonkey).items.offerLast(worry);
                        }
                    }
                }
            }

            monkeys.sort(Comparator.comparingInt(a -> a.count));
            for (Monkey monkey : monkeys) {
                System.out.println("count: " + monkey.count);
            }

            return monkeys.get(monkeys.size()-2).count * monkeys.get(monkeys.size()-1).count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
