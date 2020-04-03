
public class EColiEnemy extends GroundEnemies{

	public EColiEnemy(int posX, int posY) {
		super(posX, posY);
		health = 10;
		fast = false;
		worth = 1;
		name = "E-Coli";
	}
	
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("Disease: " + name + "\nWorth: " + worth
				+ "\nHealth: " + health + "\nFast: " + fast);
		return data.toString();
	}

}
