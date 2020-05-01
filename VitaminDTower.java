package towers;
public class VitaminDTower extends AirTowers{
	public VitaminDTower(int posX, int posY) {
		super(posX, posY);
		cost = 22;
		range = 2;
		damage = 10;
		fireDelay = 4;
		name = "D Tower";
		chain = true;
		chainEffect = 2;
	}
	
	public VitaminDTower() {
		cost = 22;
		range = 2;
		damage = 10;
		fireDelay = 4;
		name = "D Tower";
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