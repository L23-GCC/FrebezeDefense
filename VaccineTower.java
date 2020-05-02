package towers;
public class VaccineTower extends AirTowers{
	
	public VaccineTower(int posX, int posY) {
		super(posX, posY);
		cost = 15;
		range = 1;
		damage = 8;
		fireDelay = 1;
		name = "Vaccine Tower";
		single = true;
	}
	
	public VaccineTower() {
		cost = 15;
		range = 1;
		damage = 8;
		fireDelay = 1;
		name = "Vaccine Tower";
		single = true;
	}
	
	@Override
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 10;
			cost = 12;
		}
		
		else if (upgradeLevel == 2) {
			damage = 15;
			cost = 22;
		}
		
		else if (upgradeLevel == 3) {
			damage = 20;
			cost = 27;
		}
	}
}
