import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {
	private final int numWaves = 10;
	private int curWave;
	private Board startingBoard;
	private ArrayList<String> buildableTowers;
	private ArrayList<Integer> lvlEnemies;
	private int[] enemiesPerWave;
	private String brdMap;
	
	public Level(String fileName) throws FileNotFoundException {
		enemiesPerWave = new int[10];
		curWave = 1;
		lvlEnemies = new ArrayList<>();
		buildableTowers = new ArrayList<>();
		
		File lvlFile = new File(fileName);
		Scanner fileScan = new Scanner(lvlFile);
		
		while (!fileScan.hasNextInt()) {
			buildableTowers.add(fileScan.nextLine());
		}
		
		for (int i = 0; i < numWaves; i++) {
			String line = fileScan.nextLine();
			Scanner lineScan = new Scanner(line);
			enemiesPerWave[i] = lineScan.nextInt();
			while (lineScan.hasNextInt()) {
				int num = lineScan.nextInt();
				lvlEnemies.add(num);
			}
		}
		
		int width = Integer.parseInt(fileScan.nextLine());
		int height = Integer.parseInt(fileScan.nextLine());
		
		StringBuilder map = new StringBuilder();
		while(fileScan.hasNextLine()) {
			map.append(fileScan.nextLine());
			map.append("\n");
		}
		
		startingBoard = new Board(map.toString(), width, height); 
		fileScan.close();
	}
	
	public void updateWave() {
		
	}
	
	public String printBoard() {
		return startingBoard.toString();
	}
	
	public String toString() {
		StringBuilder lvl = new StringBuilder();
		lvl.append("Available Towers" + buildableTowers.toString() + "\n");
		lvl.append("Current Wave: " + curWave);
		return lvl.toString();
	}
}