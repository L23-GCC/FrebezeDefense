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
	public void upgradeRange(int tier) {
		if (tier == 1) {
			range++;
		}
		
		if (tier == 2) {
			range++;
		}	
	}
	
	public void upgradeDamage(int tier) {
		if (tier == 1) {
			damage += 2;
		}
		
		if (tier == 2) {
			damage += 3;
		}
	}
	
	public void upgradeROF(int tier) {
		if (tier == 1) {
			fireDelay--;
		}
	}
}
