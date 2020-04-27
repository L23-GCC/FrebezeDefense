
public class CloroxTower extends GroundTowers {
	
	//Like a catapult, fires hard shots with long range, but slow rate of fire.
	public CloroxTower(int posX, int posY) {
		super(posX, posY);
		cost = 30;
		range = 4;
		damage = 40;
		fireDelay = 1;
		name = "Clorox Tower";
	}

}
