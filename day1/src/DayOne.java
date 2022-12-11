import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DayOne {

	public static void main(String[] args) {
		System.out.println("max: " + read());
	}


	private static int read() {
		try(BufferedReader reader =
					new BufferedReader(new FileReader("input.txt"))) {
			int max = 0;
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				int carrying = 0;
				for (; line != null && !line.isBlank() && !line.isEmpty(); line = reader.readLine()) {
					carrying += Integer.parseInt(line);
				}
				max = Math.max(max, carrying);
			}
			return max;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
  }
}

