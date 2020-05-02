package towers;
public class VitaminB12Tower extends AirTowers{
	public VitaminB12Tower(int posX, int posY) {
		super(posX, posY);
		cost = 12;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "B12Tower";
		single = true;
	}
	
	public VitaminB12Tower() {
		cost = 12;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "B12Tower";
		single = true;
	}
	
	@Override
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 8;
			range = 4;
			cost = 8;
		}
		
		else if (upgradeLevel == 2) {
			range = 5;
			cost = 6;
			
		}
		
		else if (upgradeLevel == 3) {
			damage = 10;
			fireDelay = 3;
			cost = 10;
		}
	}
}