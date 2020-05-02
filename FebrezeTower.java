package towers;
public class FebrezeTower extends HybridTowers {
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
	
	public FebrezeTower() {
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