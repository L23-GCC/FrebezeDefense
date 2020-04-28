import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Level {
	private final int numWaves = 10;
	private int curWave;
	private Board startingBoard;
	private ArrayList<String> buildableTowers; //Towers available to be built 
	private ArrayList<Integer> lvlEnemies; //Stores all the enemies
	private int[] enemiesPerWave; //Stores how many enemies are to be released from lvlEnemies per wave
	private boolean didYouWin = false;
	private boolean didYouDie = false;
	
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
	
	public void runWave() throws InterruptedException {
		updateTowers();
		fillWaveEnemies();
		
		boolean keepLoop = true;
		boolean hasLost = false;
		int count = 2;
		
		System.out.println(printBoard());

		while (keepLoop) {
			if (count % 2 == 0) {
				startingBoard.spawnEnemy();
			}
			startingBoard.updateBoard();
			TimeUnit.SECONDS.sleep(1);
			System.out.println(count - 1); //Used to see how many iterations the game runs
			System.out.println(startingBoard.toString());
			if (startingBoard.getPlayerHealth() <= 0) {
				keepLoop = false;
				hasLost = true;
			}
			
			if (startingBoard.getOnBoardFoes().size() == 0) {
				keepLoop = false;
			}
			count++;
		}
		
		if (hasLost) {
			System.out.println("You has died.");
			didYouDie = true;
		}
		else {
			if (curWave == 10) {
				didYouWin = true;
				System.out.println("Level Complete");
			}
			curWave++;
			System.out.println("Wave Complete!");
		}
	}
	
	private void updateTowers() {
		Scanner scan = new Scanner(System.in);
		System.out.println(printBoard());
		System.out.println("Health: " + startingBoard.getPlayerHealth());
		System.out.println("Toilet Paper: " + startingBoard.getToiletPaper());
		System.out.println("Do you want to build/upgrade a tower (Y/N)");
		char choice = scan.next().charAt(0);

		while (choice == 'Y') {
			System.out.println(buildableTowers);
			System.out.println("Enter int for chosen tower, x coordinate, and y coordinate.");
			int chosenTwr = scan.nextInt();
			int x = scan.nextInt();
			int y = scan.nextInt();
			try {
				if (buildableTowers.get(chosenTwr).equals("IbuprofenTower")) {
					if (startingBoard.getBoardIndex(x, y) == '+') {
						startingBoard.buildTower(new IbuprofenTower(x, y));
					}
					else {
						startingBoard.upgradeTower(x, y);
					}
				}
				else if (buildableTowers.get(chosenTwr).equals("CloroxTower")) {
					if (startingBoard.getBoardIndex(x, y) == '+') {
						startingBoard.buildTower(new CloroxTower(x, y));
					}
					else {
						startingBoard.upgradeTower(x, y);
					}
				}
				else if (buildableTowers.get(chosenTwr).equals("GermXTower")) {
					if (startingBoard.getBoardIndex(x, y) == '+') {
						startingBoard.buildTower(new GermXTower(x, y));
					}
					else {
						startingBoard.upgradeTower(x, y);
					}
				}
				else if (buildableTowers.get(chosenTwr).equals("HandSanitizerTower")) {
					if (startingBoard.getBoardIndex(x, y) == '+') {
						startingBoard.buildTower(new HandSanitizerTower(x, y));
					}
					else {
						startingBoard.upgradeTower(x, y);
					}
				}
				else if (buildableTowers.get(chosenTwr).equals("TumsTower")) {
					if (startingBoard.getBoardIndex(x, y) == '+') {
						startingBoard.buildTower(new TumsTower(x, y));
					}
					else {
						startingBoard.upgradeTower(x, y);
					}
				}
				else if (buildableTowers.get(chosenTwr).equals("VaccineTower")) {
					if (startingBoard.getBoardIndex(x, y) == '+') {
						startingBoard.buildTower(new VaccineTower(x, y));
					}
					else {
						startingBoard.upgradeTower(x, y);
					}
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (choice == 'Y') {
				System.out.println(printBoard());
			}
			System.out.println("Health: " + startingBoard.getPlayerHealth());
			System.out.println("Toilet Paper: " + startingBoard.getToiletPaper());
			System.out.println("Do you want to build/upgrade a tower (Y/N)");
			choice = scan.next().charAt(0);
		}

	}
	
	/**
	 * fix enemies constructor
	 * @param waveNum
	 */
	private void fillWaveEnemies() {
		for (int i = 0; i < enemiesPerWave[curWave - 1]; i++) {
			if (lvlEnemies.get(0) == 1) {
				startingBoard.addEnemy(new EColiEnemy());
				lvlEnemies.remove(0);
			}
			else if (lvlEnemies.get(0) == 2) {
				startingBoard.addEnemy(new BascillusEnemy());
				lvlEnemies.remove(0);
			}
			else if (lvlEnemies.get(0) == 3) {
				startingBoard.addEnemy(new StrepEnemy());
				lvlEnemies.remove(0);
			}
			else if (lvlEnemies.get(0) == 4) {
				startingBoard.addEnemy(new StaphEnemy());
				lvlEnemies.remove(0);
			}
			else if (lvlEnemies.get(0) == 5) {
				startingBoard.addEnemy(new FluEnemy());
			}
			else if (lvlEnemies.get(0) == 6) {
				startingBoard.addEnemy(new CovidEnemy());
			}
		}
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
	
	public boolean getWonStatus() {
		return didYouWin;
	}
	
	public int getCurWave() {
		return curWave;
	}
	
	public boolean getDieStatus() {
		return didYouDie;
	}
}
