package towers;

/**
 * @author Liam and Tom
 * Rapid fire single target ground tower.  Small range, but enemies are liquidated when in proximity.
 */
public class HandSanitizerTower extends GroundTowers {
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public HandSanitizerTower(int posX, int posY) {
		super(posX, posY);
		cost = 15;
		range = 1;
		damage = 8;
		fireDelay = 1;
		upgradeLevel = 0;
		name = "Hand Sanitizer Tower";
		single = true;
	}
	
	/**
	 * Default constructor
	 */
	public HandSanitizerTower() {
		cost = 15;
		range = 1;
		damage = 8;
		fireDelay = 1;
		upgradeLevel = 0;
		name = "Hand Sanitizer Tower";
		single = true;
	}
	
	@Override
	/**
	 * List of upgrades
	 */
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 10;
			cost = 10;
		}
		
		else if (upgradeLevel == 2) {
			damage = 15;
			cost = 20;
		}
		
		else if (upgradeLevel == 3) {
			damage = 20;
			cost = 25;
		}
	}
}
