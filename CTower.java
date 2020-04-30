
public class CTower extends GroundTowers {
	
	public CTower(int x, int y) {
		super(x, y);
		cost = 20;
		range = 2;
		damage = 10;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Vitamin C Tower";
		chain = true;
		chainEffect = 2;
	}

	
	@Override
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			fireDelay = 3;
			damage = 12;
			cost = 15;
		}
		
		else if (upgradeLevel == 2) {
			damage = 15;
			chainEffect = 3;
			cost = 20;
		}
		
		else if (upgradeLevel == 3) {
			damage = 20;
			chainEffect = 4;
			cost = 25;
		}
	}
}
