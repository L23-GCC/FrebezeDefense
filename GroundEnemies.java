
public class GroundEnemies extends Enemies{
	public GroundEnemies() {
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
