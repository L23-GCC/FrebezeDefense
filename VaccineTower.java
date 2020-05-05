package towers;

/**
 * @author Liam and Tom
 * Rapid fire single target air tower.  Small range, but enemies are liquidated when in proximity.
 */
public class VaccineTower extends AirTowers{
	
	/**
	 * Constructor for placing tower on board.
	 * Outlines the towers various statistics.
	 * @param posX is the towers x position.
	 * @param posY is the towers y position.
	 */
	public VaccineTower(int posX, int posY) {
		super(posX, posY);
		cost = 15;
		range = 1;
		damage = 8;
		fireDelay = 1;
		name = "Vaccine Tower";
		single = true;
	}
	
	/**
	 * Default constructor
	 */
	public VaccineTower() {
		cost = 15;
		range = 1;
		damage = 8;
		fireDelay = 1;
		name = "Vaccine Tower";
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
			cost = 12;
		}
		
		else if (upgradeLevel == 2) {
			damage = 15;
			cost = 22;
		}
		
		else if (upgradeLevel == 3) {
			damage = 20;
			cost = 27;
		}
	}
}