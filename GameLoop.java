import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Creates a map that links user input to Strings in order to decide which levels should be played. 
 * It then loops the chosen level(s) until game is concluded.
 * @author CONRADLJ19
 *
 */
public class GameLoop {
	/**
	 * Collects user input and runs the loop for each level.
	 * @param args the arguments (blank)
	 * @throws FileNotFoundException if File is not found.
	 * @throws InterruptedException if user input is invalid.
	 */
	public static void main (String [] args) throws FileNotFoundException, InterruptedException {
		TreeMap<Integer, String> levels = new TreeMap<>(); //Map for list of all levels
		ArrayList<Level> chosenLvls = new ArrayList<>(); //ArrayList for the levels chosen by the user to play.
		
		//Fills TreeMap levels with all file Strings currently available.
		levels.put(1, "L1.txt");
		levels.put(2, "L2.txt");
		levels.put(3, "L3.txt");
		levels.put(4, "L4.txt");
		levels.put(5, "L5.txt");
		
		Scanner cons = new Scanner(System.in);
		System.out.println(printMenu());
		int userChoice = -1;
		
		//while loop to ensure the user input is an integer and a valid menu choice.
		while (true) {
			try {
				userChoice = cons.nextInt();
				//if statement determines if userChoice matches a valid menu option.
				if (userChoice < 0 || userChoice > 5) {
					throw new Exception();
				}
				break;
			}
			catch(Exception e) {
				//Gets rid of invalid input in the scanner and prints message indicating the error.
				System.out.println("Please enter a valid menu option.");
				cons.nextLine();
			}
		}
		
		//if user chose to play all, adds every level to chosenLevels ArrayList.
		if (userChoice == 0) {
			for (int i = 0; i < levels.size(); i++) {
				chosenLvls.add(new Level(levels.get(i + 1)));
			}
		}
		//Otherwise, only add the level chosen by the user.
		else {
			Level playedLevel = new Level(levels.get(userChoice));
			chosenLvls.add(playedLevel);
		}
		
		//Outer for loop goes through each level chosen.
		for (int i = 0; i < chosenLvls.size(); i++) {
			//while loop loops through a single level until player wins or dies.
			while (!chosenLvls.get(i).getWonStatus()) {
				chosenLvls.get(i).runWave();
				//if user dies, decrement i to replay the level user failed on.
				if(chosenLvls.get(i).getDieStatus()) {
					i--;
					break;
				}
			}
		}
	}
	
	/**
	 * Method to print the level select menu.
	 * @return the menu in the form of a String.
	 */
	private static String printMenu() {
		StringBuilder menu = new StringBuilder();
		menu.append("Please enter your level selection\n\n");
		menu.append("0 - Play All\n");
		menu.append("1 - Play Level 1\n");
		menu.append("2 - Play Level 2\n");
		menu.append("3 - Play Level 3\n");
		menu.append("4 - Play Level 4\n");
		menu.append("5 - Play Level 5");
		return menu.toString();
	}
}