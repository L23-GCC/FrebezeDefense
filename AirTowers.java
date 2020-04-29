
public class AirTowers extends Towers {
	public AirTowers(int x, int y) {
		this.posX = x;
		this.posY = y;
		groundTower = false;
		airTower = true;
		single = false;
		AOE = false;
		chain = false;
		poison = false;
	}
}
