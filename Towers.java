package towers;

/**
 * @author Levi, Liam, and Tom
 * Abstract Tower class that contains all the variables
 * later children will use.
 */
public class Towers {
	//Type of Tower:
	protected boolean groundTower;
	protected boolean airTower;
	//General Tower Variables:
	protected int cost;
	protected int range;
	protected int damage;
	protected int posX;
	protected int posY;
	protected int fireDelay;
	protected int curDelay = 0;
	protected int upgradeLevel;
	protected String name;
	//Tower Attacks:
	protected boolean single;
	protected boolean AOE;
	protected boolean chain;
	//Tower Attack Variables:
	protected int chainEffect;
	
	/**
	 * Default constructor
	 */
	public Towers() {
	}
	
	
	//A whole list of getters for all the different variables.
	
	/**
	 * @return Air tower true or false
	 */
	public boolean getAir() {
		return airTower;
	}
	
	/**
	 * @return Ground tower true or false
	 */
	public boolean getGround() {
		return groundTower;
	}
	
	/**
	 * @return Tower damage
	 */
	public int getDmg() {
		return damage;
	}
	
	/**
	 * @return Tower y position
	 */
	public int getPosY() {
		return posY;
	}
	
	/**
	 * @return Tower x position
	 */
	public int getPosX() {
		return posX;
	}
	
	/**
	 * @return Tower name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return Tower cost
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * @return Tower range
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * @return Tower fire delay
	 */
	public int getFireDelay() {
		return fireDelay;
	}
	
	/**
	 * @return Tower's current delay
	 */
	public int getCurDelay() {
		return curDelay;
	}
	
	/**
	 * @return Tower upgrade level
	 */
	public int getUpgradeLevel() {
		return upgradeLevel;
	}
	
	/**
	 * @return Chain attack type tower's chain effect.  a.k.a. how many targets are hit by the chain attack.
	 */
	public int getChainEffect() {
		return chainEffect;
	}
	
	//--------------------------------------------
	
	//The method for determining ROF of a tower
	//When curDelay == fireDelay, the tower fires.
	//The higher the fireDelay, the lower the ROF.
	public void incCurDelay() {
		if (curDelay == fireDelay) {
			curDelay = 0;
		}
		
		else if (curDelay != 0) {
			curDelay++;
		}
	}
	
	//increments delay
	public void startDelay() {
		curDelay++;
	}
	
	//used to upgrade a tower
	public void upgrade() {
		upgradeLevel++;
	}
	
	//used to down-grade a tower
	public void deUpgrade() {
		upgradeLevel--;
	}
	
	/**
	 * @return Tower's attack type
	 */
	public String attackType() {
		if (single) {
			return "single";
		}
		
		else if (chain) {
			return "chain";
		}
		
		else if (AOE) {
			return "AOE";
		}
		return "null";
	}
	
	/**
	 * @return String of all the important general variables.
	 */
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("Type: " + name + "\nCost: " + cost
				+ "\nDamage: " + damage + "\nRange: " + range
				+ "\nFireDelay: " + fireDelay);
		return data.toString();
	}
	/**
	 * @return True if the tower is a groundTower.  Else, return false
	 */
	public boolean isGroundAttack() {
		if (groundTower) {
			return true;
		}
		return false;
	}
}