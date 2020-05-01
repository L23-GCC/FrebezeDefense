package towers;
public class TumsTower extends GroundTowers {
	
	//This is the strong and overall better version of IbuprofenTower
	public TumsTower(int posX, int posY) {
		super(posX, posY);
		cost = 25;
		range = 3;
		damage = 15;
		fireDelay = 3;
		name = "Tums Tower";
		single = true;
	}

	public TumsTower() {
		cost = 25;
		range = 3;
		damage = 15;
		fireDelay = 3;
		name = "Tums Tower";
		single = true;
	}
	
	@Override
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 20;
			cost = 15;
		}
		
		else if (upgradeLevel == 2) {
			range = 4;
			cost = 20;
		}
		
		else if (upgradeLevel == 3) {
			damage = 30;
			range = 5;
			fireDelay = 2;
			cost = 30;
		}
	}
}