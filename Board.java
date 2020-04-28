import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Board {
	private char[][] board;
	private char[][] boardTemplate;
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
		toiletPaper = 25;
		
		mapScan.close();
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
					if (towersBuilt.get(j).getCurDelay() == 0) {
						onBoardFoes.get(i).takeDamage(towersBuilt.get(j).getDmg());
						towersBuilt.get(j).startDelay();
						break;
					}
				}
				towersBuilt.get(j).incCurDelay();
			}
		}
		for (int i = 0; i < onBoardFoes.size(); i++) {
			board[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()] = boardTemplate[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()];
			if (onBoardFoes.get(i).getAir()) {
				onBoardFoes.get(i).move();
				if (onBoardFoes.get(i).getSpeed()) {
					onBoardFoes.get(i).move();
				}
			}
			else {
				moveEnemy(onBoardFoes.get(i));
				if (onBoardFoes.get(i).getSpeed() && (onBoardFoes.get(i).getPosX() < width - 1)) {
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
}
