public class HandSanitizerTower extends GroundTowers {
	public HandSanitizerTower(int posX, int posY) {
		super(posX, posY);
		cost = 15;
		range = 1;
		damage = 8;
		fireDelay = 6;
		upgradeLevel = 0;
		name = "Hand Sanitizer Tower";
		single = true;
	}
}