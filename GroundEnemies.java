
public class GroundEnemies extends Enemies{
	public GroundEnemies(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public void move() {
		if(!fast) {
			posX += 1;
		}
		else {
			posX += 2;
		}
		
	}
}
