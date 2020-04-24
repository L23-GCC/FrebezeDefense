import java.io.FileNotFoundException;

public class GameLoop {
	public static void main (String [] args) throws FileNotFoundException, InterruptedException {
		Level lvl1 = new Level("L1.txt");
		while (!lvl1.getWonStatus()) {
			lvl1.runWave();
		}
	}
}
