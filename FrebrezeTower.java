package towers;
public class FrebrezeTower extends HybridTowers {
	
	public FrebrezeTower(int x, int y) {
		super(x, y);
		cost = 20;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Frebreze Tower";
		AOE = true;
	}
	
	public FrebrezeTower() {
		cost = 20;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Frebreze Tower";
		AOE = true;
	}

	@Override
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 7;
			cost = 10;
		}
		
		else if (upgradeLevel == 2) {
			range = 4;
			cost = 15;
		}
		
		else if (upgradeLevel == 3) {
			fireDelay = 3;
			cost = 25;
		}
		
		else if (upgradeLevel == 4) {
			fireDelay = 1;
			damage = 50;
			range = 25;
			cost = 100;
		}
	}
}
