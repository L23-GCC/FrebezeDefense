import java.io.FileNotFoundException;

public class TestThisThing {
	public static void main (String [] args) throws FileNotFoundException {
		String name = "L1.txt";
		Level lvl1 = new Level(name);
		System.out.println(lvl1.toString());
		System.out.println(lvl1.printBoard());
	}
}
