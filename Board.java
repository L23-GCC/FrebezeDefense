import java.util.ArrayList;
import java.util.Scanner;

import enemies.Enemies;
import towers.*;

import java.util.Random;

/**
 * This class generates a double array to display the game board. It keeps track of the player's health, currency, currently built towers,
 * the enemies on the board, and any remaining enemies needed for the wave. 
 * This class governs the attack of towers on enemies and updating the board as enemies move and are defeated.
 * @author Levi Conrad, Liam Hillebrand, Thomas St. Jean 
 *
 */
public class Board {
	private char[][] board; //Double char array for the board the user sees.
	private char[][] boardTemplate; //Double char array for a board that only updates for towers, not enemies. It is used for enemy pathfinding.
	private int playerHealth; //Player's remaining health for the level.
	private int toiletPaper; // Player's currently held currency.
	private ArrayList<Towers> towersBuilt; //ArrayList of currently built towers.
	private ArrayList<Towers> allTowers; //ArrayList of all possible towers.
	private ArrayList<Enemies> foes; //ArrayList for enemies waiting to be spawned onto the board.
	private ArrayList<Enemies> onBoardFoes; //ArrayList for enemies currently on the board.
	private int finish; //int for the y coordinate of the end of enemy path.
	private int start; // int for the y coordinate of enemy spawn.
	private int width; // int for width of board.
	private int height; // int for height of board.
	
	/**
	 * This constructor scans through its String parameter to fill the double array. Initializes starting values for health and toiletPaper.
	 * Initializes ArrayLists.
	 * @param map String containing the map of the board. It will be ported into the double array.
	 * @param width of board.
	 * @param height of board.
	 */
	public Board(String map, int width, int height) {
		this.width = width;
		this.height = height;
		
		//Initializes double arrays with given width and height. 
		board = new char[width][height];
		boardTemplate = new char[width][height];
		Scanner mapScan = new Scanner(map);
		
		//for loop scans through String param and fills double array accordingly.
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
				
				//If statement to ensure the boardTemplate does not receive the S and F chars.
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
	 * This method applies to damage to enemies based on locations and type, 
	 * moves enemies across board, and lowers player health for enemies that reach the finish.
	 */
	public void updateBoard() {

		//Enemies take damage (Liam Hillebrand)
				for(int j = 0; j < towersBuilt.size(); j++) {
					//Single type attack
					if (towersBuilt.get(j).attackType().equals("single")) {
						for (int i = 0; i < onBoardFoes.size(); i++) {
							//Checks if ground enemy
							if (towersBuilt.get(j).isGroundAttack() && !onBoardFoes.get(i).getAir()) {
								int range = towersBuilt.get(j).getRange();
								//checks if enemy is in range.
								if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
										&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
										&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
										&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) 
								{
									//checks tower is ready to fire.
									if (towersBuilt.get(j).getCurDelay() == 0) {
										//if all checks are met, tower fires upon enemy.
										onBoardFoes.get(i).takeDamage(towersBuilt.get(j).getDmg());
										towersBuilt.get(j).startDelay(); //fire delay is initiated
										break; //Break out of loop so tower can only attack one enemy since it is single target.
									}
								}
							}
							//checks if air enemy
							else if (!towersBuilt.get(j).isGroundAttack() && onBoardFoes.get(i).getAir()) {
								int range = towersBuilt.get(j).getRange();
								//checks if enemy is in range
								if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
										&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
										&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
										&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) 
								{
									//checks if tower is ready to fire.
									if (towersBuilt.get(j).getCurDelay() == 0) {
										//if all checks are met enemy takes damage.
										onBoardFoes.get(i).takeDamage(towersBuilt.get(j).getDmg());
										towersBuilt.get(j).startDelay();//Initiate fire delay.
										break;//break out of loop so single target tower only attacks 1 enemy.
									}
								}
							}
							towersBuilt.get(j).incCurDelay(); //Increment fire delay on towers that did not fire.
						}
					}

					//AOE type attack
					if (towersBuilt.get(j).attackType().equals("AOE")) {
						if (towersBuilt.get(j).getCurDelay() == 0) {
							int range = towersBuilt.get(j).getRange();
							//loops through all enemies.
							for (int i = 0; i < onBoardFoes.size(); i++) {
								//check if enemy is in range.
								if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
										&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
										&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
										&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) {
									onBoardFoes.get(i).takeDamage(towersBuilt.get(j).getDmg()); //deals damage to all enemies in range.
								}
							}
							towersBuilt.get(j).startDelay(); //starts fire delay
						}
						else {
							towersBuilt.get(j).incCurDelay(); //increment fire delay if tower did not fire.
						}
					}

					//Chain type attack
					if (towersBuilt.get(j).attackType().equals("chain")) {
						for (int i = 0; i < onBoardFoes.size(); i++) {
							//Checks if ground enemy
							if (towersBuilt.get(j).isGroundAttack() && !onBoardFoes.get(i).getAir()) {
								int range = towersBuilt.get(j).getRange();
								//checks if enemy is in range
								if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
										&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
										&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
										&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) 
								{
									//checks if tower is ready to fire.
									if (towersBuilt.get(j).getCurDelay() == 0) {
										//checks if number of enemies on board if less that range of chain effect on tower.
										if (onBoardFoes.size() < towersBuilt.get(j).getChainEffect()) {
											//loops through every foe on the board to apply chain damage.
											for (int k = 0; k < onBoardFoes.size(); k++) {
												onBoardFoes.get(k).takeDamage(towersBuilt.get(j).getDmg());
											}
										}
										//if number of enemies on board is larger than the chain range, only loop through number of enemies
										//equal to chain effect length.
										else {
											//loops through enemies to apply chain effect.
											for (int k = 0; k < towersBuilt.get(j).getChainEffect(); k++) {
												if (onBoardFoes.size() > (i + k)) {
													onBoardFoes.get(i + k).takeDamage(towersBuilt.get(j).getDmg());
												}
												else {
													break;
												}
												
											}
										}
										towersBuilt.get(j).startDelay(); //start fire delay
										break;
									}
								}
							}
							
							//Checks if air enemy
							else if (!towersBuilt.get(j).isGroundAttack() && onBoardFoes.get(i).getAir()) {
								int range = towersBuilt.get(j).getRange();
								//checks if enemy is in range.
								if (onBoardFoes.get(i).getPosX() <= towersBuilt.get(j).getPosX() + range 
										&& onBoardFoes.get(i).getPosX() >= towersBuilt.get(j).getPosX() - range
										&& onBoardFoes.get(i).getPosY() <= towersBuilt.get(j).getPosY() + range 
										&& onBoardFoes.get(i).getPosY() >= towersBuilt.get(j).getPosY() - range) 
								{
									//checks if tower is ready to fire.
									if (towersBuilt.get(j).getCurDelay() == 0) {
										//checks if number of enemies on board is less than chain effect length
										if (onBoardFoes.size() < towersBuilt.get(j).getChainEffect()) {
											for (int k = 0; k < onBoardFoes.size(); k++) {
												onBoardFoes.get(k).takeDamage(towersBuilt.get(j).getDmg());
											}
										}
										//otherwise, loop only to end of chain effect length.
										else {
											//loops through enemies to apply chain effect.
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
						towersBuilt.get(j).incCurDelay(); //increment delay for towers that did not fire.
					}

				}

				//for loop to move enemies. (Levi Conrad)
				for (int i = 0; i < onBoardFoes.size(); i++) {
					//update char double array with board template location to overrite old enemy position.
					board[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()] = boardTemplate[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()];
					//checks if enemy is Air.
					if (onBoardFoes.get(i).getAir()) {
						//if enemy is more than 2 spaces away from end of board call move method.
						if(onBoardFoes.get(i).getPosX() < width - 1) {
							onBoardFoes.get(i).move();
						}
						//otherwise, increment position by 1 to avoid out of bounds movement.
						else {
							onBoardFoes.get(i).setXPos(onBoardFoes.get(i).getPosX() + 1);
						}
					}
					//Ground enemy movement.
					else {
						//call moveEnemy method
						moveEnemy(onBoardFoes.get(i));
						//if enemy has a fast speed, and is more than 2 spaces away from end of board, call moveEnemy again.
						if(onBoardFoes.get(i).getSpeed() && onBoardFoes.get(i).getPosX() < width - 1) {
							moveEnemy(onBoardFoes.get(i));
						}
					}
					
					//if enemy is dead.
					if (onBoardFoes.get(i).isDie()) {
						toiletPaper += onBoardFoes.get(i).getWorth(); //add enemy worth to player's toiletPaper total.
						onBoardFoes.remove(i); //remove enemy from onBoardFoes list.
						i--; //decrement i to stay on track with enemies.
					}
					//if enemy reaches end of board
					else if (onBoardFoes.get(i).getPosX() >= width - 1) {
						playerHealth -= onBoardFoes.get(i).getHealth(); //subtract current enemy health from player health.
						onBoardFoes.remove(i); //remove enemy from onBoardFoes list.
						i--; //decrement i to stay on track with enemies.
					}
				}
				//for loop to update each enemies' new position with first char in their name.
				for (int i = 0; i < onBoardFoes.size(); i++) {
					board[onBoardFoes.get(i).getPosX()][onBoardFoes.get(i).getPosY()] = onBoardFoes.get(i).getName().charAt(0);
				}
			}

	/**
	 * Method to build a tower onto the board. (Thomas St. Jean)
	 * @param tower to build
	 * @throws Exception if chosen coordinate is invalid for building or player does not have enough currency to build tower.
	 */
	public void buildTower(Towers tower) throws Exception {
		//if player does not have enough toiletPaper to build tower.
		if (tower.getCost() > toiletPaper) {
			throw new Exception("You're too broke");
		}
		//if coordinates of tower are not a valid building location.
		else if (getBoardIndex(tower.getPosX(), tower.getPosY()) != '+') {
			throw new Exception("Cannot build a tower there");
		}
		board[tower.getPosX()][tower.getPosY()] = tower.getName().charAt(0); //put char symbolizing tower on chosen board location.
		boardTemplate[tower.getPosX()][tower.getPosY()] = tower.getName().charAt(0); //update boardTemplate with tower char.
		toiletPaper -= tower.getCost(); //subtract cost from toiletPaper
		towersBuilt.add(tower); //add built tower to ArrayList.
		
		//print success message.
		System.out.println(tower.getName() + " successfully built!"); 
	}
	
	/**
	 * Function used to add enemies to foe list. (Levi Conrad)
	 * @param foe to be added
	 */
	public void addEnemy(Enemies foe) {
		foes.add(foe);
	}
	
	/**
	 * Method to return a String of the current state of board double array.
	 */
	public String toString() {
		StringBuilder boardStr = new StringBuilder();
		//loops through double array, appending each char to StringBuilder.
		for (int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				boardStr.append(board[j][i]);
			}
			//appends a new line character at the end for each layer of double array.
			boardStr.append("\n");
		}
		return boardStr.toString();
	}
	
	/**
	 * Method to spawn an enemy from foes ArrayList and place it into the onBoardFoes ArrayList.
	 */
	public void spawnEnemy() {
		//if foes list is empty end the method.
		if (foes.isEmpty()) {
			return;
		}
		//adds enemy from first index of foes into onBoardFoes
		onBoardFoes.add(foes.get(0));
		onBoardFoes.get(onBoardFoes.size() - 1).setXPos(0); //sets x Position to 0 to start on left side of the board.
		
		//if it is a ground enemy.
		if (!onBoardFoes.get(onBoardFoes.size() - 1).getAir()) {
			//if it is a fast speed enemy.
			if (onBoardFoes.get(onBoardFoes.size() - 1).getSpeed()) {
				onBoardFoes.get(onBoardFoes.size() - 1).setYPos(start + 1); //spawns enemy one index below start position.
			}
			//if it is a normal speed enemy.
			else {
				//spawns enemy on start position.
				onBoardFoes.get(onBoardFoes.size() - 1).setYPos(start);
			}
		}
		//If it is an air enemy.
		else {
			Random rand = new Random();
			int y = rand.nextInt(height - 1);
			onBoardFoes.get(onBoardFoes.size() - 1).setYPos(y); //spawns enemy at a random y coordinate on the left side of the board, within the height limits.
		}
		//remove the spawned enemy from foes to prevent duplicates.
		foes.remove(0);
	}
	
	/**
	 * Getter method for playerHealth
	 * @return current playerHealth
	 */
	public int getPlayerHealth() {
		return playerHealth;
	}
	
	/**
	 * Getter method for onBoardFoes
	 * @return ArrayList of currently on board enemies.
	 */
	public ArrayList<Enemies> getOnBoardFoes(){
		return onBoardFoes;
	}
	
	/**
	 * Getter method for foes.
	 * @return ArrayList of enemies waiting to be added to the board.
	 */
	public ArrayList<Enemies> getFoes() {
		return foes;
	}

	/**
	 * Method that takes and enemy and updates their position according to boardTemplate to allow for correct movement. (Levi Conrad)
	 * @param e enemy to be moved.
	 */
	private void moveEnemy(Enemies e) {
		//Checks if current direction is right.
		if (e.getDirection() == 'R') {
			//checks if next position in boardTemplate is the same as current position.
			if (boardTemplate[e.getPosX() + 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]) {
				e.setXPos(e.getPosX() + 1); // moves enemy onto position to its right.
			}
			//checks if position below the enemy matches its boardTemplate path.
			else if (boardTemplate[e.getPosX()][e.getPosY() + 1] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setYPos(e.getPosY() + 1);//moves onto the position below current location.
				e.setDirection('D'); //changes direction to down.
			}
			//checks if position above the enemy matches its boardTemplate path.
			else if (boardTemplate[e.getPosX()][e.getPosY() - 1] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setYPos(e.getPosY() - 1);//moves onto the position above current location.
				e.setDirection('U');//changes direction to up.
			}
		}
		//checks if current position is left.
		else if (e.getDirection() == 'L') {
			//if current direction contains another valid path location, move onto it.
			if (boardTemplate[e.getPosX() - 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]) {
				e.setXPos(e.getPosX() - 1);
			}
			//if next square for valid path is below current location, move to that location and change direction accordingly.
			else if (boardTemplate[e.getPosX()][e.getPosY() + 1] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setYPos(e.getPosY() + 1);
				e.setDirection('D');
			}
			//if next square for valid path is above current location, move to that location and change direction accordingly.
			else if (boardTemplate[e.getPosX()][e.getPosY() - 1] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setYPos(e.getPosY() - 1);
				e.setDirection('U');
			}
		}
		//checks if current position is up.
		else if (e.getDirection() == 'U') {
			//if current direction contains another valid path location, move onto it.
			if (boardTemplate[e.getPosX()][e.getPosY() - 1] == boardTemplate[e.getPosX()][e.getPosY()]) {
				e.setYPos(e.getPosY() - 1);
			}
			//if next square for valid path is to the right current location, move to that location and change direction accordingly.
			else if (boardTemplate[e.getPosX() + 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setXPos(e.getPosX() + 1);
				e.setDirection('R');
			}
			//if next square for valid path is to the left current location, move to that location and change direction accordingly.
			else if (boardTemplate[e.getPosX() - 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setXPos(e.getPosX() - 1);
				e.setDirection('L');
			}
		}
		//checks if current direction is down.
		else if (e.getDirection() == 'D') {
			//if current direction contains another valid path location, move onto it.
			if (boardTemplate[e.getPosX()][e.getPosY() + 1] == boardTemplate[e.getPosX()][e.getPosY()]) {
				e.setYPos(e.getPosY() + 1);
			}
			//if next square for valid path is to the right current location, move to that location and change direction accordingly.
			else if (boardTemplate[e.getPosX() + 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setXPos(e.getPosX() + 1);
				e.setDirection('R');
			}
			//if next square for valid path is to the left current location, move to that location and change direction accordingly.
			else if (boardTemplate[e.getPosX() - 1][e.getPosY()] == boardTemplate[e.getPosX()][e.getPosY()]){
				e.setXPos(e.getPosX() - 1);
				e.setDirection('L');
			}
		}
		
	}

	/**
	 * Getter method for toiletPaper
	 * @return current amount of toiletPaper.
	 */
	public int getToiletPaper() {
		return toiletPaper;
	}
	
	/**
	 * Getter method for an index of the board double array at given coordinates.
	 * @param x coordinate for desired element of double array.
	 * @param y coordinate for desired element of double array.
	 * @return char at chosen board double array index.
	 */
	public char getBoardIndex(int x, int y) {
		return board[x][y];
	}

	/**
	 * Getter method for towersBuilt
	 * @return ArrayList of currently built towers.
	 */
	public ArrayList<Towers> getTowersBuilt() {
		return towersBuilt;
	}

	/**
	 * Method used to upgrade a currently built tower.
	 * @param index of chosen tower in towersBuilt ArrayList.
	 * @throws Exception if entered index was invalid or player does not have enough toiletPaper to upgrade tower.
	 */
	public void upgradeTower(int index) throws Exception {
		//if index was within ArrayList.
		if (index >= 0 && index < towersBuilt.size()) {
			towersBuilt.get(index).upgrade(); //upgrade tower.
			//if player did not have enough toiletPaper
			if (towersBuilt.get(index).getCost() > toiletPaper) {
				towersBuilt.get(index).deUpgrade(); //retract upgrade
				throw new Exception ("You're too broke."); //throw exception.
			}
			//subtract cost of upgrade from toiletPaper.
			toiletPaper -= towersBuilt.get(index).getCost();
			//Print successful upgrade message.
			System.out.println(towersBuilt.get(index).getName() + " successfully upgraded!");
		}
		//if index was not within ArrayList.
		else {
			//Throw exception.
			throw new Exception ("Index was invalid.");
		}
	}
}