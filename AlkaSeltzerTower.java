
public class AlkaSeltzerTower extends AirTowers {
	
	public AlkaSeltzerTower(int posX, int posY) {
		super(posX, posY);
		cost = 27;
		range = 3;
		damage = 15;
		fireDelay = 3;
		upgradeLevel = 0;
		name = "Alkaseltzer Tower";
		single = true;
		
		
	}

	@Override
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
