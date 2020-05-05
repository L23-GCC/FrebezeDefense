package towers;

/**
 * @author Liam and Tom
 * Catapult single-target ground tower.  Slow, but high damage.
 */
public class LysolTower extends GroundTowers {
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public LysolTower(int posX, int posY) {
		super(posX, posY);
		cost = 30;
		range = 4;
		damage = 40;
		fireDelay = 6;
		name = "Lysol Tower";
		single = true;
	}
	
	/**
	 * Default constructor
	 */
	public LysolTower() {
		cost = 30;
		range = 4;
		damage = 40;
		fireDelay = 6;
		name = "Lysol Tower";
		single = true;
	}
	
	@Override
	/**
	 * List of upgrades
	 */
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 50;
			cost = 10;
		}
		
		else if (upgradeLevel == 2) {
			damage = 60;
			range = 5;
			cost = 30;
		}
		
		else if (upgradeLevel == 3) {
			damage = 80;
			range = 6;
			fireDelay = 5;
			cost = 35;
		}
	}
}