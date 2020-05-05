package towers;

/**
 * @author Liam and Tom
 * Chain attacking air tower.  Hits multiple enemies with a single shot.
 */
public class VitaminDTower extends AirTowers{
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public VitaminDTower(int posX, int posY) {
		super(posX, posY);
		cost = 22;
		range = 2;
		damage = 10;
		fireDelay = 4;
		name = "D Tower";
		chain = true;
		chainEffect = 2;
	}
	
	/**
	 * Default constructor
	 */
	public VitaminDTower() {
		cost = 22;
		range = 2;
		damage = 10;
		fireDelay = 4;
		name = "D Tower";
		chain = true;
		chainEffect = 2;
	}
	
	@Override
	/**
	 * List of upgrades
	 */
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			fireDelay = 3;
			damage = 12;
			cost = 17;
		}
		
		else if (upgradeLevel == 2) {
			damage = 15;
			chainEffect = 3;
			cost = 22;
		}
		
		else if (upgradeLevel == 3) {
			damage = 20;
			chainEffect = 4;
			cost = 25;
		}
	}
}