package enemies;

/**
 * @author Levi, Liam, Tom
 * Abstract enemy class that contains all the variables
 * later child class enemies will use.  Also outlines 
 * the enemy methods.
 */
public abstract class Enemies {
	protected int health;
	protected int posX;
	protected int posY;
	protected boolean fast;
	protected int worth;
	protected String name;
	protected boolean air;
	protected char direction = 'R';
	
	/**
	 * @return Enemy health
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * @return Enemy x position on board
	 */
	public int getPosX() {
		return posX;
	}
	
	/**
	 * @return Enemy y position on board
	 */
	public int getPosY() {
		return posY;
	}
	
	/**
	 * @return Enemy speed: true if fast or false if slow
	 */
	public boolean getSpeed() {
		return fast;
	}
	
	/**
	 * @return Enemy toilet paper worth
	 */
	public int getWorth() {
		return worth;
	}
	
	/**
	 * @return Enemy name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return Enemy type: true if air, false if ground
	 */
	public boolean getAir() {
		return air;
	}
	
	/**
	 * @return Enemy direction
	 */
	public char getDirection() {
		return direction;
	}
	
	/**
	 * Moves the enemy whether fast or slow
	 */
	public void move() {
		if(!fast) {
			posX += 1;
		}
		else {
			posX += 2;
		}
	}
	
	/**
	 * @param Damage the enemy will take
	 * Subtracts from enemy health the amount of damage
	 */
	public void takeDamage(int dmg) {
		health -= dmg;
	}
	
	/**
	 * @return Returns death status: true is dead, false is alive.
	 */
	public boolean isDie() {
		if (health <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return A string comprised of the enemies main variables
	 */
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("Disease: " + name + "\nWorth: " + worth
				+ "\nHealth: " + health + "\nFast: " + fast);
		return data.toString();
	}

	/**
	 * @param y position
	 * Sets the enemies y position
	 */
	public void setYPos(int y) {
		posY = y;
	}
	
	/**
	 * @param x position
	 * Sets the enemies x position
	 */
	public void setXPos(int x) {
		posX = x;
	}
	
	/**
	 * @param c is the direction char
	 * Sets direction of enemies to c (Which
	 * can be "R," "L," "U," or "D").
	 */
	public void setDirection(char c) {
		direction = c;
	}
}