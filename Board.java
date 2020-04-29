import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	private char[][] board;
	private int playerHealth;
	private int toiletPaper;
	private ArrayList<Towers> towersBuilt;
	private ArrayList<Enemies> foes;
	private ArrayList<Enemies> onBoardFoes;
	private int finish;
	private int start;
	private int width;
	private int height;
	
	public Board(String map, int width, int height) {
		this.width = width;
		this.height = height;
		
		board = new char[width][height];
		Scanner mapScan = new Scanner(map);
		
		for(int j = 0; j < height; j++) {
			String line = mapScan.nextLine();
			for (int i = 0; i < width; i++) {
				if (line.charAt(i) == 'S') {
					start = j;
				}
				if (line.charAt(i) == 'F') {
					finish = j;
				}
				board[i][j] = line.charAt(i);
			}
		}
		
		foes = new ArrayList<>();
		onBoardFoes = new ArrayList<>();
		towersBuilt = new ArrayList<>();
		playerHealth = 50;
		toiletPaper = 25;
	}
	
	/**
	 * need to add y coordinate to range check
	 */
	public void updateBoard() {
		
		//Enemies take damage
		for(int j = 0; j < towersBuilt.size(); j++) {
			//Single type attack
			if (towersBuilt.get(j).attackType().equals("single")) {
				for (int i = 0; i < onBoardFoes.size(); i++) {
					int range = towersBuilt.get(j).getRange();
					if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
							&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
							&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
							&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) 
					{
						if (towersBuilt.get(j).getCurDelay() == 0) {
							onBoardFoes.get(i).takeDamage(towersBuilt.get(j).getDmg());
							towersBuilt.get(j).startDelay();
							break;
						}
					}
				}
				towersBuilt.get(j).incCurDelay();
			}
			
			//AOE type attack
			if (towersBuilt.get(j).attackType().equals("AOE")) {
				if (towersBuilt.get(j).getCurDelay() == 0) {
					int range = towersBuilt.get(j).getRange();
					for (int i = 0; i < onBoardFoes.size(); i++) {
						if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
								&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
								&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
								&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) {
							onBoardFoes.get(i).takeDamage(towersBuilt.get(j).getDmg());
						}
					}
					towersBuilt.get(j).startDelay();
				}
				else {
					towersBuilt.get(j).incCurDelay();
				}
			}
			
			//Chain type attack
			if (towersBuilt.get(j).attackType().equals("chain")) {
				for (int i = 0; i < onBoardFoes.size(); i++) {
					int range = towersBuilt.get(j).getRange();
					if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
							&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
							&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
							&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) 
					{
						if (towersBuilt.get(j).getCurDelay() == 0) {
							if (onBoardFoes.size() < towersBuilt.get(j).getChainEffect()) {
								for (int k = 0; k < onBoardFoes.size(); k++) {
									onBoardFoes.get(k).takeDamage(towersBuilt.get(j).getDmg());
								}
							}
							else {
								for (int k = 0; k < towersBuilt.get(j).getChainEffect(); k++) {
									onBoardFoes.get(i + k).takeDamage(towersBuilt.get(j).getDmg());
								}
							}
							towersBuilt.get(j).startDelay();
							break;
						}
					}
				}
				towersBuilt.get(j).incCurDelay();
			}
				
		}
			//Chain type attack
			//if (towersBuilt.get(j).attackType().equals("chain"))
			//Poison type attack
			//if (towersBuilt.get(j).attackType().equals("poison"))
		
		for (int i = 0; i < onBoardFoes.size(); i++) {
			board[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()] = '.';
			onBoardFoes.get(i).move();
			
			if (onBoardFoes.get(i).isDie()) {
				toiletPaper += onBoardFoes.get(i).getWorth();
				onBoardFoes.remove(i);
				i--;
			}
			else if (onBoardFoes.get(i).getPosX() >= width - 1) {
				playerHealth -= onBoardFoes.get(i).getHealth();
				onBoardFoes.remove(i);
				i--;
			}
		}
		for (int i = 0; i < onBoardFoes.size(); i++) {
			board[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()] = onBoardFoes.get(i).getName().charAt(0);
		}
	}
	
	public void buildTower(Towers tower) throws Exception {
		if (tower.getCost() > toiletPaper) {
			throw new Exception("You're too broke");
		}
		board[tower.getPosX()][tower.getPosY()] = tower.getName().charAt(0);
		toiletPaper -= tower.getCost();
		towersBuilt.add(tower);
		
		System.out.println(tower.getName() + " successfully built!");
	}
	
	public void addEnemy(Enemies foe) {
		foes.add(foe);
	}
	
	public String toString() {
		StringBuilder boardStr = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				boardStr.append(board[j][i]);
			}
			boardStr.append("\n");
		}
		return boardStr.toString();
	}
	
	public void spawnEnemy() {
		if (foes.isEmpty()) {
			return;
		}
		onBoardFoes.add(foes.get(0));
		onBoardFoes.get(onBoardFoes.size() - 1).setXPos(0);
		
		if (onBoardFoes.get(onBoardFoes.size() - 1).getSpeed()) {
			onBoardFoes.get(onBoardFoes.size() - 1).setYPos(start + 1);
		}
		else {
			onBoardFoes.get(onBoardFoes.size() - 1).setYPos(start);
		}
		foes.remove(0);
	}
	
	public int getPlayerHealth() {
		return playerHealth;
	}
	
	public ArrayList<Enemies> getOnBoardFoes(){
		return onBoardFoes;
	}
	
	public int getToiletPaper() {
		return toiletPaper;
	}
	
	public char getBoardIndex(int x, int y) {
		return board[x][y];
	}
	
	public void upgradeTower(int x, int y) throws Exception {
		for (int i = 0; i < towersBuilt.size(); i++) {
			if (towersBuilt.get(i).getPosX() == x 
				&& towersBuilt.get(i).getPosY() == y) {
				towersBuilt.get(i).upgrade();
				if (towersBuilt.get(i).getCost() > toiletPaper) {
					towersBuilt.get(i).deUpgrade();
					throw new Exception ("You're too broke.");
				}
				toiletPaper -= towersBuilt.get(i).getCost();
				System.out.println(towersBuilt.get(i).getName() + " successfully upgraded!");
			}
		}
	}
	
	public ArrayList<Enemies> getFoes() {
		return foes;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
