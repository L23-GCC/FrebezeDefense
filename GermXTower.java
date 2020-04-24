
public class GermXTower extends GroundTowers {
	
	//This is the "spike machine" like in BTD.  Damage is 0 because it will be programmed to drop spikes.
	//(Most likely to get deleted, will be harder to program than a tower that targets an enemy)
	public GermXTower(int posX, int posY) {
		super(posX, posY);
		cost = 15;
		range = 2;
		damage = 0;
		fireDelay = 3;
		name = "GermX Tower";
	}

}
