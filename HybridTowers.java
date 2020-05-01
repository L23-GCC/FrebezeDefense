package towers;
public class HybridTowers extends Towers {
	public HybridTowers(int x, int y) {
		this.posX = x;
		this.posY = y;
		groundTower = true;
		airTower = true;
		single = false;
		AOE = false;
		chain = false;
		poison = false;
	}
	
	public HybridTowers() {
		groundTower = true;
		airTower = true;
		single = false;
		AOE = false;
		chain = false;
		poison = false;
	}
}