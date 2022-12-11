import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class DayOne {

	public static void main(String[] args) {
		PriorityQueue<Integer> heap = read();
		for (Integer cur = heap.poll(), level = 0; cur != null; cur = heap.poll(), ++level) {
			System.out.println(level + ": " + cur);
		}
	}


	private static PriorityQueue<Integer> read() {
		try(BufferedReader reader =
					new BufferedReader(new FileReader("input.txt"))) {
			PriorityQueue<Integer> heap = new PriorityQueue<>();
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				int carrying = 0;
				for (; line != null && !line.isBlank() && !line.isEmpty(); line = reader.readLine()) {
					carrying += Integer.parseInt(line);
				}
				heap.offer(carrying);
			}
			return heap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
  }
}

