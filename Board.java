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
		for(int j = 0; j < towersBuilt.size(); j++) {
			for (int i = 0; i < onBoardFoes.size(); i++) {
				int range = towersBuilt.get(j).getRange();
				if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
						&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
						&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
						&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) 
				{
					if (towersBuilt.get(i).getCurDelay() == 0) {
						onBoardFoes.get(i).takeDamage(towersBuilt.get(j).getDmg());
						towersBuilt.get(i).startDelay();
						break;
					}
				}
				towersBuilt.get(i).incCurDelay();
			}
		}
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
}
