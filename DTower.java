
public class DTower extends AirTowers{
	
	public DTower(int posX, int posY) {
		super(posX, posY);
		cost = 22;
		range = 2;
		damage = 10;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Vitamin D Tower";
		chain = true;
		chainEffect = 2;
	}

	@Override
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
