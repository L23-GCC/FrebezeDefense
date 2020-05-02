import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import enemies.*;
import towers.*;

public class Level {
	private final int numWaves = 10;
	private int curWave;
	private Board startingBoard;
	private ArrayList<String> buildableTowers; //Towers available to be built 
	private ArrayList<Integer> lvlEnemies; //Stores all the enemies
	private ArrayList<ArrayList<String>> waveHints;
	private int[] enemiesPerWave; //Stores how many enemies are to be released from lvlEnemies per wave
	private boolean didYouWin = false;
	private boolean didYouDie = false;

	public Level(String fileName) throws FileNotFoundException {
		enemiesPerWave = new int[10];
		curWave = 1;
		lvlEnemies = new ArrayList<>();
		buildableTowers = new ArrayList<>();
		waveHints = new ArrayList<>();
		
		File lvlFile = new File(fileName);
		Scanner fileScan = new Scanner(lvlFile);
		
		while (!fileScan.hasNextInt()) {
			buildableTowers.add(fileScan.nextLine());
		}
		
		for (int i = 0; i < numWaves; i++) {
			waveHints.add(new ArrayList<>());
			String line = fileScan.nextLine();
			Scanner lineScan = new Scanner(line);
			enemiesPerWave[i] = lineScan.nextInt();
			while (lineScan.hasNext()) {
				if (lineScan.hasNextInt()) {
					int num = lineScan.nextInt();
					lvlEnemies.add(num);
				}
				else {
					waveHints.get(i).add(lineScan.next());
				}
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
			
			if (startingBoard.getOnBoardFoes().size() == 0 && startingBoard.getFoes().size() == 0) {
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
	
	public void convert(ArrayList<Towers> t) {
		
		for(int i = 0; i < buildableTowers.size(); i++) {
			String type = buildableTowers.get(i);
			
			switch (type) {
			case "IbuprofenTower":
				t.add(new IbuprofenTower());
				break;
			case "AlkaSeltzerTower":
				t.add(new AlkaSeltzerTower());
				break;
			case "CloroxTower":
				t.add(new CloroxTower());
				break;
			case "FrebrezeTower":
				t.add(new FrebrezeTower());
				break;
			case "HandSanitizerTower":
				t.add(new HandSanitizerTower());
				break;
			case "ProbioticTower":
				t.add(new ProbioticTower());
				break;
			case "TumsTower":
				t.add(new TumsTower());
				break;
			case "VaccineTower":
				t.add(new VaccineTower());
				break;
			case "VitaminB12Tower":
				t.add(new VitaminB12Tower());
				break;
			case "VitaminCTower":
				t.add(new VitaminCTower());
				break;
			case "VitaminDTower":
				t.add(new VitaminDTower());
				break;
			}
		}
	}
	
	public String towerInfo() {
		StringBuilder sb = new StringBuilder();
		ArrayList<Towers> allTowers = new ArrayList<>();
		convert(allTowers);
		
		sb.append("     Type      |  Cost  |  Damage  |  Range  | Rate of Fire");
		int max = 0;
		for(int i = 0; i < buildableTowers.size(); i++) {
			for(int j = 0; j < buildableTowers.size(); j++) {
				if(i != j && max < buildableTowers.get(i).length()) {
					max = Math.max(buildableTowers.get(i).length(), buildableTowers.get(j).length());
				}
			}
		}
		for (int i = 0; i < allTowers.size(); i++) {
			sb.append("\n===========================================================\n");
			sb.append(allTowers.get(i).getName());
			for(int j = 0; j < max - buildableTowers.get(i).length(); j++) {
				sb.append(" ");
			}
			sb.append("|   " + allTowers.get(i).getCost() + "   |");
			for(int j = 0; j < (5 - (allTowers.get(i).getDmg() / 10)); j++) {
				sb.append(" ");
			}
					
			sb.append(allTowers.get(i).getDmg() + "    |    " + allTowers.get(i).getRange() + 
					"    |      " + allTowers.get(i).getFireDelay());
		}
		return sb.toString();
	}
	
	public String upgradeTowers() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < startingBoard.getTowersBuilt().size(); i++) {
			Towers curTower = startingBoard.getTowersBuilt().get(i);
			sb.append(curTower.getName() + ": (" + curTower.getPosX() + ", " + curTower.getPosY() + ")");
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String menu() {
		StringBuilder options = new StringBuilder();
		
		options.append("Health: " + startingBoard.getPlayerHealth()
				+ "\nToilet Paper: " + startingBoard.getToiletPaper()
				+ "\nOptions menu: (enter the number in parentheses to make selection)"
				+ "\n 	(0) - Build Tower"
				+ "\n	(1) - Upgrade Tower"
				+ "\n	(2) - Tower Information"
				+ "\n	(3) - Run Wave");
		return options.toString();
	}
	private void updateTowers() {
		Scanner scan = new Scanner(System.in);
		System.out.println(printBoard());
		System.out.println(printWaveHints());
		System.out.println(menu());
		
		while(!(scan.hasNextInt())) {
			System.out.println("Invalid entry");
			System.out.println(menu());
			scan.next();
		}
		int userChoice = scan.nextInt();
		while(userChoice > 3 || userChoice < 0) {
			System.out.println("Invalid entry");
			System.out.println(menu());
			scan.next();
		}

		while (userChoice != 3) {
			if (userChoice == 0) {
				System.out.println(buildableTowers);
				System.out.println("Enter int for chosen tower, x coordinate, and y coordinate.");
				
				while(!(scan.hasNextInt())) {
					System.out.println("Invalid entry");
					System.out.println("Enter int for chosen tower, x coordinate, and y coordinate.");
					scan.next();
				}
				
				int chosenTwr = scan.nextInt();
				int x = scan.nextInt();
				int y = scan.nextInt();
				
				boolean validEntry = false;
				while(!validEntry) {
					try {
						if (buildableTowers.get(chosenTwr).equals("IbuprofenTower")) {
							startingBoard.buildTower(new IbuprofenTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("CloroxTower")) {
							startingBoard.buildTower(new CloroxTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("HandSanitizerTower")) {
							startingBoard.buildTower(new HandSanitizerTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("TumsTower")) {
							startingBoard.buildTower(new TumsTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("AlkaSeltzerTower")) {
							startingBoard.buildTower(new AlkaSeltzerTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("FrebrezeTower")) {
							startingBoard.buildTower(new FrebrezeTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("ProbioticTower")) {
							startingBoard.buildTower(new ProbioticTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("VaccineTower")) {
							startingBoard.buildTower(new VaccineTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("B12Tower")) {
							startingBoard.buildTower(new VitaminB12Tower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("CTower")) {
							startingBoard.buildTower(new VitaminCTower(x, y));
							validEntry = true;
						}
						else if (buildableTowers.get(chosenTwr).equals("DTower")) {
							startingBoard.buildTower(new VitaminDTower(x, y));
							validEntry = true;
						}
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Please re-enter values:");
						while(!(scan.hasNextInt())) {
							System.out.println("Invalid entry");
							System.out.println("Please re-enter values:");
							scan.next();
						}
						chosenTwr = scan.nextInt();
						x = scan.nextInt();
						y = scan.nextInt();
					}
				}
			}
			else if (userChoice == 1) {
				System.out.println("Select the tower you would like to upgrade");
				System.out.println(upgradeTowers());
				System.out.println("Enter the coordinates of the tower you would like to upgrade: ");
				while(!(scan.hasNextInt())) {
					System.out.println("Invalid entry");
					System.out.println("Enter int for x coordinate and y coordinate.");
					scan.next();
				}
				int x = scan.nextInt();
				int y = scan.nextInt();
				boolean validEntry = false;
				while(!validEntry) {
					try {
						startingBoard.upgradeTower(x, y);
						validEntry = true;
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Please Re-enter values:");
						while(!(scan.hasNextInt())) {
							System.out.println("Invalid entry");
							System.out.println("Please Re-enter values:");
							scan.next();
						}
						x = scan.nextInt();
						y = scan.nextInt();
					}
				}
			}
			else if (userChoice == 2) {
				System.out.println(towerInfo());
			}

			System.out.println(startingBoard);
			
			System.out.println(menu());
			
			while(!(scan.hasNextInt())) {
				System.out.println("Invalid entry");
				System.out.println(menu());
			}
			userChoice = scan.nextInt();
			while(userChoice > 3 || userChoice < 0) {
				System.out.println("Invalid entry");
				System.out.println(menu());
			}
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
				lvlEnemies.remove(0);
			}
			else if (lvlEnemies.get(0) == 6) {
				startingBoard.addEnemy(new CovidEnemy());
				lvlEnemies.remove(0);
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
	
	private String printWaveHints() {
		StringBuilder hints = new StringBuilder();
		for (int i = 0; i < waveHints.get(curWave - 1).size(); i++) {
			if (waveHints.get(curWave - 1).get(i).equals("poop")) {
				hints.append("\n");
			}
			else {
				hints.append(waveHints.get(curWave - 1).get(i));
				hints.append(" ");
			}
		}
		return hints.toString();
	}
}
