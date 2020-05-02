import java.util.ArrayList;
import java.util.Scanner;

import enemies.Enemies;
import towers.*;

import java.util.Random;

public class Board {
	private char[][] board;
	private char[][] boardTemplate;
	private int playerHealth;
	private int toiletPaper;
	private ArrayList<Towers> towersBuilt;
	private ArrayList<Towers> allTowers;
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
		boardTemplate = new char[width][height];
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
				
				if (line.charAt(i) == 'S' || line.charAt(i) == 'F') {
					boardTemplate[i][j] = '.';
				}
				else {
					boardTemplate[i][j] = line.charAt(i);
				}
			}
		}
		
		foes = new ArrayList<>();
		onBoardFoes = new ArrayList<>();
		towersBuilt = new ArrayList<>();
		playerHealth = 50;
		toiletPaper = 50000;
		
		mapScan.close();
	}
	
	
	public void updateBoard() {

		//Enemies take damage
		for(int j = 0; j < towersBuilt.size(); j++) {
			//Single type attack
			if (towersBuilt.get(j).attackType().equals("single")) {
				for (int i = 0; i < onBoardFoes.size(); i++) {
					//Checks if ground enemy
					if (towersBuilt.get(j).isGroundAttack() && !onBoardFoes.get(i).getAir()) {
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
					//checks if air enemy
					else if (!towersBuilt.get(j).isGroundAttack() && onBoardFoes.get(i).getAir()) {
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
					//Checks if ground enemy
					if (towersBuilt.get(j).isGroundAttack() && !onBoardFoes.get(i).getAir()) {
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
					
					//Checks if air enemy
					else if (!towersBuilt.get(j).isGroundAttack() && onBoardFoes.get(i).getAir()) {
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
										if (onBoardFoes.size() > (i + k)) {
											onBoardFoes.get(i + k).takeDamage(towersBuilt.get(j).getDmg());
										}
										else {
											break;
										}
										
									}
								}
								towersBuilt.get(j).startDelay();
								break;
							}
						}
					}
				}
				towersBuilt.get(j).incCurDelay();
			}

		}

		for (int i = 0; i < onBoardFoes.size(); i++) {
			board[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()] = boardTemplate[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()];
			if (onBoardFoes.get(i).getAir()) {
				if(onBoardFoes.get(i).getPosX() < width - 1) {
					onBoardFoes.get(i).move();
				}
				else {
					onBoardFoes.get(i).setXPos(onBoardFoes.get(i).getPosX() + 1);
				}
			}
			else {
				moveEnemy(onBoardFoes.get(i));
				if(onBoardFoes.get(i).getSpeed() && onBoardFoes.get(i).getPosX() < width - 1) {
					moveEnemy(onBoardFoes.get(i));
				}
			}

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
		else if (getBoardIndex(tower.getPosX(), tower.getPosY()) != '+') {
			throw new Exception("Cannot build a tower there");
		}
		board[tower.getPosX()][tower.getPosY()] = tower.getName().charAt(0);
		boardTemplate[tower.getPosX()][tower.getPosY()] = tower.getName().charAt(0);
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
		
		if (!onBoardFoes.get(onBoardFoes.size() - 1).getAir()) {
			if (onBoardFoes.get(onBoardFoes.size() - 1).getSpeed()) {
				onBoardFoes.get(onBoardFoes.size() - 1).setYPos(start + 1);
			}
			else {
				onBoardFoes.get(onBoardFoes.size() - 1).setYPos(start);
			}
		}
		else {
			Random rand = new Random();
			int y = rand.nextInt(height - 1);
			onBoardFoes.get(onBoardFoes.size() - 1).setYPos(y);
		}
		foes.remove(0);
	}
	
	public int getPlayerHealth() {
		return playerHealth;
	}
	
	public ArrayList<Enemies> getOnBoardFoes(){
		return onBoardFoes;
	}
	
	public ArrayList<Enemies> getFoes() {
		return foes;
	}

	private void moveEnemy(Enemies e) {
		if (e.getDirection() == 'R') {
			if (boardTemplate[e.getPosX() + 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]) {
				e.setXPos(e.getPosX() + 1);
			}
			else if (boardTemplate[e.getPosX()][e.getPosY() + 1] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setYPos(e.getPosY() + 1);
				e.setDirection('D');
			}
			else if (boardTemplate[e.getPosX()][e.getPosY() - 1] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setYPos(e.getPosY() - 1);
				e.setDirection('U');
			}
		}
		else if (e.getDirection() == 'L') {
			if (boardTemplate[e.getPosX() - 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]) {
				e.setXPos(e.getPosX() - 1);
			}
			else if (boardTemplate[e.getPosX()][e.getPosY() + 1] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setYPos(e.getPosY() + 1);
				e.setDirection('D');
			}
			else if (boardTemplate[e.getPosX()][e.getPosY() - 1] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setYPos(e.getPosY() - 1);
				e.setDirection('U');
			}
		}
		else if (e.getDirection() == 'U') {
			if (boardTemplate[e.getPosX()][e.getPosY() - 1] == boardTemplate[e.getPosX()][e.getPosY()]) {
				e.setYPos(e.getPosY() - 1);
			}
			else if (boardTemplate[e.getPosX() + 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setXPos(e.getPosX() + 1);
				e.setDirection('R');
			}
			else if (boardTemplate[e.getPosX() - 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setXPos(e.getPosX() - 1);
				e.setDirection('L');
			}
		}
		else if (e.getDirection() == 'D') {
			if (boardTemplate[e.getPosX()][e.getPosY() + 1] == boardTemplate[e.getPosX()][e.getPosY()]) {
				e.setYPos(e.getPosY() + 1);
			}
			else if (boardTemplate[e.getPosX() + 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setXPos(e.getPosX() + 1);
				e.setDirection('R');
			}
			else if (boardTemplate[e.getPosX() - 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setXPos(e.getPosX() - 1);
				e.setDirection('L');
			}
		}
		
	}

	public int getToiletPaper() {
		return toiletPaper;
	}
	
	public char getBoardIndex(int x, int y) {
		return board[x][y];
	}
	
	public ArrayList<Towers> getTowersBuilt() {
		return towersBuilt;
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
			else {
				throw new Exception("You do not own a tower at that location.");
			}
		}
	}
}