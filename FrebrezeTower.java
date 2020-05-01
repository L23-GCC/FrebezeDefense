package towers;
public class FrebrezeTower extends HybridTowers {
	public FrebrezeTower(int x, int y) {
		super(x, y);
		cost = 20;
		range = 10; //change me
		damage = 5;
		fireDelay = 3;
		upgradeLevel = 0;
		name = "Frebreze Tower";
		AOE = true;
	}
	
	public FrebrezeTower() {
		cost = 20;
		range = 10; //change me
		damage = 5;
		fireDelay = 3;
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
	}
}