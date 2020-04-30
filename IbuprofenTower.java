
public class IbuprofenTower extends GroundTowers{
	public IbuprofenTower(int posX, int posY){
		super(posX, posY);
		cost = 10;
		range = 3;
		damage = 5;
		fireDelay = 4;
		upgradeLevel = 0;
		name = "Ibuprofen Tower";
		single = true;
	}
	
	//Conceptual Design of what upgrades might look like.
	//Three upgrades per tower to reach final form.
	//Upgrade 1: Increases damage and range.
	//Upgrade 2: Increases range.
	//Upgrade 3: Increases ROF and damage.
	//How cost works TBD
	@Override
	public void upgrade() {
		super.upgrade();
		
		if (upgradeLevel == 1) {
			damage = 8;
			range = 4;
			cost = 8;
		}
		
		if (upgradeLevel == 2) {
			range = 5;
			cost = 6;
		}
		
		if (upgradeLevel == 3) {
			damage = 10;
			fireDelay = 3;
			cost = 10;
		}
	}
	
}
