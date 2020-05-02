package towers;
public class LysolTower extends GroundTowers {
	
	//Like a catapult, fires hard shots with long range, but slow rate of fire.
	public LysolTower(int posX, int posY) {
		super(posX, posY);
		cost = 30;
		range = 4;
		damage = 40;
		fireDelay = 6;
		name = "Lysol Tower";
		single = true;
	}
	
	public LysolTower() {
		cost = 30;
		range = 4;
		damage = 40;
		fireDelay = 6;
		name = "Lysol Tower";
		single = true;
	}
	
	@Override
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