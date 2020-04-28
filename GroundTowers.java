
public class GroundTowers extends Towers{
	
	public GroundTowers(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public void upgrade() {
		upgradeLevel++;
	}
	
	@Override
	public void deUpgrade() {
		upgradeLevel--;
	}
}
