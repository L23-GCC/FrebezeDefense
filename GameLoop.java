import java.io.FileNotFoundException;

public class GameLoop {
	public static void main (String [] args) throws FileNotFoundException, InterruptedException {
		Level lvl1 = new Level("L1.txt");
		while (!lvl1.getWonStatus()) {
			System.out.println("Current Wave: " + lvl1.getCurWave());
			lvl1.runWave();
		}
	}
}
