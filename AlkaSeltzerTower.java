package towers;

/**
 * @author Liam and Tom
 * Advanced single-target air tower
 */
public class AlkaSeltzerTower extends AirTowers {
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public AlkaSeltzerTower(int posX, int posY) {
		super(posX, posY);
		cost = 27;
		range = 3;
		damage = 15;
		fireDelay = 3;
		name = "Alkaseltzer Tower";
		single = true;
	}
	
	/**
	 * Default constructor
	 */
	public AlkaSeltzerTower() {
		super();
		cost = 27;
		range = 3;
		damage = 15;
		fireDelay = 3;
		name = "Alkaseltzer Tower";
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
			cost = 17;
		}
		
		else if (upgradeLevel == 2) {
			range = 4;
			cost = 22;
		}
		
		else if (upgradeLevel == 3) {
			damage = 30;
			range = 5;
			fireDelay = 2;
			cost = 32;
		}
	}
}