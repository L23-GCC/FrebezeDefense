package towers;

/**
 * @author Liam and Tom
 * Basic single-targeting ground tower.
 */
public class IbuprofenTower extends GroundTowers{
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public IbuprofenTower(int posX, int posY){
		super(posX, posY);
		cost = 10;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Ibuprofen Tower";
		single = true;
	}
	
	/**
	 * Default constructor
	 */
	public IbuprofenTower() {
		cost = 10;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Ibuprofen Tower";
		single = true;
	}

	@Override
	/**
	 * List of upgrades
	 */
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 8;
			range = 4;
			cost = 8;
		}
		
		if (upgradeLevel == 2) {
			range = 5;
			cost = 6;
		}
		
		if (upgradeLevel == 3) {
			damage = 10;
			fireDelay = 3;
			cost = 10;
		}
	}
}