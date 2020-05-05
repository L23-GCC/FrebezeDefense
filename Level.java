import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import enemies.*;
import towers.*;

/**
 * This class deals with setting up levels including maps, enemies, towers, and so on
 * @author Thomas St.Jean, Levi Conrad, Liam Hillebrand
 */
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
	/**
	 * This constructor takes all the info stored in a text file (L1, L2, L3, L4, & L5) and converts it into the necessary class
	 * members required to play the game
	 * @param fileName - Takes in a file that contains all level information
	 * @throws FileNotFoundException
	 */
	public Level(String fileName) throws FileNotFoundException {
		enemiesPerWave = new int[10];	//Size ten because there are 10 waves per level
		curWave = 1;
		lvlEnemies = new ArrayList<>();
		buildableTowers = new ArrayList<>();
		waveHints = new ArrayList<>();
		
		File lvlFile = new File(fileName);
		Scanner fileScan = new Scanner(lvlFile);
		
		while (!fileScan.hasNextInt()) {
			buildableTowers.add(fileScan.nextLine());
		}
		
		for (int i = 0; i < numWaves; i++) {	//adding enemies and wave hints to their respective arrayLists
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
			lineScan.close();
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
	/**
	 * This method times the release of enemies onto the board, as well as determining whether the player has died, or if they won
	 * @throws InterruptedException - Exception thrown due to forcing the program to pause in order to aid the user's viewing of the game
	 */
	public void runWave() throws InterruptedException {
		updateTowers();		//deals w/ building and upgrading towers
		fillWaveEnemies();
		
		boolean keepLoop = true;
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
				didYouDie = true;
			}
			
			if (startingBoard.getOnBoardFoes().size() == 0 && startingBoard.getFoes().size() == 0) { //wave win condition
				keepLoop = false;
			}
			count++;
		}
		
		if (didYouDie) {
			System.out.println("You has died.");
		}
		else {
			if (curWave == 10) {	//10 waves per level, therefore at the end of the 10th wave, the level is won
				didYouWin = true;
				System.out.println("Level Complete");
			}
			curWave++;
			System.out.println("Wave Complete!");
		}
	}
	/**
	 * Converts Strings from buildableTowers to Towers, and adds them to another ArrayList
	 * @param t - an ArrayList to which Towers are added
	 */
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
			case "LysolTower":
				t.add(new LysolTower());
				break;
			case "FebrezeTower":
				t.add(new FebrezeTower());
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
			case "B12Tower":
				t.add(new VitaminB12Tower());
				break;
			case "CTower":
				t.add(new VitaminCTower());
				break;
			case "DTower":
				t.add(new VitaminDTower());
				break;
			}
		}
	}
	/**
	 * a toString that prints out tower stats in a table
	 * @return
	 */
	public String towerInfo() {
		StringBuilder sb = new StringBuilder();
		ArrayList<Towers> allTowers = new ArrayList<>();
		convert(allTowers);
		
		sb.append("     Name      |  Cost  |  Damage  |  Range  |  Fire Delay  |         Type");
		int max = 0;
		for(int i = 0; i < buildableTowers.size(); i++) { //Finds the largest tower name and adjusts spacing accordingly
			for(int j = 0; j < buildableTowers.size(); j++) {
				if(i != j && max < buildableTowers.get(i).length()) {
					max = Math.max(buildableTowers.get(i).length(), buildableTowers.get(j).length());
				}
			}
		}
		for (int i = 0; i < allTowers.size(); i++) {
			sb.append("\n========================================================================================\n");
			sb.append(allTowers.get(i).getName());
			for(int j = 0; j <= max - buildableTowers.get(i).length(); j++) {
				if(max == buildableTowers.get(i).length()) {	//No spaces added if tower has longest name
					break;
				}
				sb.append(" ");
			}
			sb.append("|   " + allTowers.get(i).getCost() + "   |");
			for(int j = 0; j < (5 - (allTowers.get(i).getDmg() / 10)); j++) {
				sb.append(" ");
			}
					
			sb.append(allTowers.get(i).getDmg() + "    |    " + allTowers.get(i).getRange() + 
					"    |      " + allTowers.get(i).getFireDelay() + "       | ");
			
			if (allTowers.get(i).getAir()) {
				sb.append("Air Tower - " + allTowers.get(i).attackType());
			}
			else if(allTowers.get(i).getGround()) {
				sb.append("Ground Tower - " + allTowers.get(i).attackType());
			}
			else {
				sb.append("Hybrid Tower - " + allTowers.get(i).attackType());
			}
			
		}
		return sb.toString();
	
	}
	/**
	 * @return - toString that prints out all towers on the board, making it easy for the user to upgrade them 
	 */
	public String printUpgradeTowers() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < startingBoard.getTowersBuilt().size(); i++) {
			Towers curTower = startingBoard.getTowersBuilt().get(i);
			sb.append(curTower.getName() + ": (" + curTower.getPosX() + ", " + curTower.getPosY() + ")");
			sb.append("\n");
		}
		return sb.toString();
	}
	/**
	 * @return - A menu that the user chooses from before each wave, printing out current health and wealth of user
	 */
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
	/**
	 * Deals with all changes made by the user regarding towers including building, upgrading, and printing out tower info
	 * Has the most exception catching because almost all user input is done here
	 */
	private void updateTowers() {
		Scanner scan = new Scanner(System.in);
		System.out.println(printBoard());
		System.out.println(printWaveHints());
		System.out.println(menu());
		int userChoice;
		
		while (true) { //error catching
			try {
				userChoice = scan.nextInt();
				if (userChoice < 0 || userChoice > 3) {
					throw new Exception();
				}
				break;
			}
			catch(Exception e) {
				System.out.println("Invalid Entry.");
				scan.nextLine();
			}
		}

		while (userChoice != 3) {	//while user chooses not to run wave
			if (userChoice == 0) {
				System.out.println(buildableTowers);
				System.out.println("Enter int for chosen tower, x coordinate, and y coordinate.");
				
				int x;
				int y;
				int chosenTwr;
				while (true) {
					try {
						chosenTwr = scan.nextInt();
						x = scan.nextInt();
						y = scan.nextInt();
						break;
					}
					catch(Exception e) {
						System.out.println("Invalid Entry.");
						scan.nextLine();
					}
				}
				
				while(true) {
					try {
						if (buildableTowers.get(chosenTwr).equals("IbuprofenTower")) {
							startingBoard.buildTower(new IbuprofenTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("LysolTower")) {
							startingBoard.buildTower(new LysolTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("HandSanitizerTower")) {
							startingBoard.buildTower(new HandSanitizerTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("TumsTower")) {
							startingBoard.buildTower(new TumsTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("AlkaSeltzerTower")) {
							startingBoard.buildTower(new AlkaSeltzerTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("FebrezeTower")) {
							startingBoard.buildTower(new FebrezeTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("ProbioticTower")) {
							startingBoard.buildTower(new ProbioticTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("VaccineTower")) {
							startingBoard.buildTower(new VaccineTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("B12Tower")) {
							startingBoard.buildTower(new VitaminB12Tower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("CTower")) {
							startingBoard.buildTower(new VitaminCTower(x, y));
						}
						else if (buildableTowers.get(chosenTwr).equals("DTower")) {
							startingBoard.buildTower(new VitaminDTower(x, y));
						}
						break;
					}
					catch (IOException e) {
						System.out.println(e.getMessage());
						System.out.println("Please re-enter values:");
						while (true) {
							try {
								chosenTwr = scan.nextInt();
								x = scan.nextInt();
								y = scan.nextInt();
								break;
							}
							catch(Exception e2) {
								System.out.println("Invalid Entry.");
								System.out.println(e2.getMessage());
								scan.nextLine();
							}
							break;
						}
					}
					catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			else if (userChoice == 1) {
				System.out.println("Select the tower you would like to upgrade");
				System.out.println(printUpgradeTowers());
				System.out.println("Enter the index of the tower you would like to upgrade: ");
				
				int index;

				while (true) {
					try {
						index = scan.nextInt();
						startingBoard.upgradeTower(index);
						break;
					}
					catch(Exception e) {
						System.out.println("Invalid Entry.");
						System.out.println(e.getMessage());
						scan.nextLine();
					}
					break;
				}
			}
			
			else if (userChoice == 2) {
				System.out.println(towerInfo());
			}

			System.out.println(startingBoard);
			
			System.out.println(menu());
			
			while (true) {
				try {
					userChoice = scan.nextInt();
					if (userChoice < 0 || userChoice > 3) {
						throw new Exception();
					}
					break;
				}
				catch(Exception e) {
					System.out.println("Invalid Entry.");
					scan.nextLine();
				}
			}
		}
		scan.close();
	}
	/**
	 * This method adds enemies to the board according to the wave
	 */
	private void fillWaveEnemies() {
		for (int i = 0; i < enemiesPerWave[curWave - 1]; i++) {	//each enemy added to startingBoard and removed from lvlEnemies
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
				startingBoard.addEnemy(new MeaslesEnemy());
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
	/**
	 * @return - toString for the board
	 */
	public String printBoard() {
		return startingBoard.toString();
	}
	@Override
	public String toString() {
		StringBuilder lvl = new StringBuilder();
		lvl.append("Available Towers" + buildableTowers.toString() + "\n");
		lvl.append("Current Wave: " + curWave);
		return lvl.toString();
	}
	/**
	 * @return - If the player won
	 */
	public boolean getWonStatus() {
		return didYouWin;
	}
	/**
	 * @return - Which wave it is
	 */
	public int getCurWave() {
		return curWave;
	}
	/**
	 * @return - If the player died
	 */
	public boolean getDieStatus() {
		return didYouDie;
	}
	/**
	 * @return - toString for the wave hints in each Level file
	 */
	private String printWaveHints() {
		StringBuilder hints = new StringBuilder();
		for (int i = 0; i < waveHints.get(curWave - 1).size(); i++) {
			if (waveHints.get(curWave - 1).get(i).equals("poop")) { //For some reason, putting \n in the text file did not make a new line
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
