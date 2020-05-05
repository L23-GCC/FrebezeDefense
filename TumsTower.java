package towers;

/**
 * @author Liam and Tom
 * Advanced single-target ground tower.
 */
public class TumsTower extends GroundTowers {
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public TumsTower(int posX, int posY) {
		super(posX, posY);
		cost = 25;
		range = 3;
		damage = 15;
		fireDelay = 3;
		name = "Tums Tower";
		single = true;
	}

	/**
	 * Default constructor
	 */
	public TumsTower() {
		cost = 25;
		range = 3;
		damage = 15;
		fireDelay = 3;
		name = "Tums Tower";
		single = true;
	}
	
	@Override
	/**
	 * List of upgrades
	 */
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 20;
			cost = 15;
		}
		
		else if (upgradeLevel == 2) {
			range = 4;
			cost = 20;
		}
		
		else if (upgradeLevel == 3) {
			damage = 30;
			range = 5;
			fireDelay = 2;
			cost = 30;
		}
	}
}