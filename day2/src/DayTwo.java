import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DayTwo {
    public static void main(String[] args) {
        System.out.println("score: " + read());
    }
    private static int read() {
        try(BufferedReader reader =
                    new BufferedReader(new FileReader("input.txt"))) {
            int score = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                switch (line.charAt(0)) {
                    case 'A':
                        switch (line.charAt(2)) {
                            case 'X':
                                score += 3;
                                break;
                            case 'Y':
                                score += 1;
                                score += 3;
                                break;
                            case 'Z':
                                score += 2;
                                score += 6;
                                break;
                            default:
                                break;
                        }
                        break;
                    case 'B':
                        switch (line.charAt(2)) {
                            case 'X':
                                score += 1;
                                break;
                            case 'Y':
                                score += 2;
                                score += 3;
                                break;
                            case 'Z':
                                score += 3;
                                score += 6;
                                break;
                            default:
                                break;
                        }
                        break;
                    case 'C':
                        switch (line.charAt(2)) {
                            case 'X':
                                score += 2;
                                break;
                            case 'Y':
                                score += 3;
                                score += 3;
                                break;
                            case 'Z':
                                score += 1;
                                score += 6;
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + line.charAt(0));
                }
            }
            return score;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
