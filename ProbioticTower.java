package towers;

/**
 * @author Liam and Tom
 * Catapult single-target air tower.  Slow, but high damage.
 */
public class ProbioticTower extends AirTowers{
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public ProbioticTower(int posX, int posY) {
		super(posX, posY);
		cost = 32;
		range = 4;
		damage = 40;
		fireDelay = 6;
		name = "Probiotic Tower";
		single = true;
	}
	
	/**
	 * Default constructor
	 */
	public ProbioticTower() {
		cost = 32;
		range = 4;
		damage = 40;
		fireDelay = 6;
		name = "Probiotic Tower";
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
			cost = 12;
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