
public class ProbioticTower extends AirTowers{

	public ProbioticTower(int posX, int posY) {
		super(posX, posY);
		cost = 32;
		range = 4;
		damage = 40;
		fireDelay = 6;
		upgradeLevel = 0;
		name = "Probiotic Tower";
		single = true;
	}
	
	@Override
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 50;
			cost = 12;
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
