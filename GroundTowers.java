package towers;

/**
 * @author Liam Hillebrand
 * Child class of towers.  Used to categorize ground towers.
 */
public class GroundTowers extends Towers{
	
	public GroundTowers(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		groundTower = true;
		airTower = false;
		single = false;
		AOE = false;
		chain = false;
	}
	
	public GroundTowers() {
		groundTower = true;
		airTower = false;
		single = false;
		AOE = false;
		chain = false;
	}
}