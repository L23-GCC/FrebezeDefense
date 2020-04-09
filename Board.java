import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	private char[][] board;
	private int playerHealth;
	private int toiletPaper;
	private ArrayList<Towers> towersBuilt;
	private ArrayList<Enemies> foes;
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
		towersBuilt = new ArrayList<>();
		playerHealth = 50;
		toiletPaper = 25;
	}
	
	/**
	 * need to add y coordinate to range check
	 */
	public void updateBoard() {
		for (int i = 0; i < foes.size(); i++) {
			for(int j = 0; j < towersBuilt.size(); j++) {
				int range = towersBuilt.get(i).getRange();
				if (foes.get(i).getPosX() <= towersBuilt.get(i).getPosX() + range && foes.get(i).getPosX() >= towersBuilt.get(i).getPosX() - range) {
					foes.get(i).takeDamage(towersBuilt.get(i).getDmg());
				}
			}
			board[foes.get(i).getPosX()][foes.get(i).getPosY()] = '.';
			foes.get(i).move();
			
			if (foes.get(i).isDie()) {
				foes.remove(i);
				i--;
			}
			else if (foes.get(i).getPosX() >= finish) {
				playerHealth -= foes.get(i).getHealth();
				foes.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < foes.size(); i++) {
			board[foes.get(i).getPosX()][foes.get(i).getPosY()] = 'E';
		}
	}
	
	public void buildTower(Towers tower) {
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
}
