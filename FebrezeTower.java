package towers;

/**
 * @author Liam and Tom
 * The legendary Febreze tower.  Area of Effect type tower,
 * i.e hits both air and ground enemies within its entire range.
 */
public class FebrezeTower extends HybridTowers {
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public FebrezeTower(int x, int y) {
		super(x, y);
		cost = 20;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Febreze Tower";
		AOE = true;
	}
	
	/**
	 * Default constructor
	 */
	public FebrezeTower() {
		cost = 20;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Febreze Tower";
		AOE = true;
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
			range = 4;
			cost = 15;
		}
		
		else if (upgradeLevel == 3) {
			fireDelay = 2;
			cost = 20;
		}
		else if (upgradeLevel == 4) {
			range = 25;
			damage = 50;
			cost = 100;
			fireDelay = 1;
		}
	}
}