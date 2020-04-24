
public class HandSanitizerTower extends GroundTowers {
	
	public HandSanitizerTower(int posX, int posY) {
		super(posX, posY);
		cost = 15;
		range = 1;
		damage = 8;
		fireDelay = 6;
		upgradeLevel = 0;
		name = "Hand Sanitizer Tower";
	}
	
//	@Override
//	public void upgrade() {
//		super.upgrade();
//		
//		if (upgradeLevel == 1) {
//			damage = 10;
//			cost = 8;
//		}
//		
//		else if (upgradeLevel == 2) {
//			damage = 15;
//			cost = 13;
//		}
//		
//		else if (upgradeLevel == 3) {
//			damage = 17;
//			fireDelay = 8;
//		}
//	}

}
