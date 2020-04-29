
public abstract class Enemies {
	protected int health;
	protected int posX;
	protected int posY;
	protected boolean fast;
	protected int worth;
	protected String name;
	
	public abstract void move();
	
	public void takeDamage(int dmg) {
		health -= dmg;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public boolean getSpeed() {
		return fast;
	}
	
	public int getWorth() {
		return worth;
	}
	
	public boolean isDie() {
		if (health <= 0) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("Disease: " + name + "\nWorth: " + worth
				+ "\nHealth: " + health + "\nFast: " + fast);
		return data.toString();
	}

	public void setYPos(int y) {
		posY = y;
	}
	
	public void setXPos(int x) {
		posX = x;
	}
	
	public String getName() {
		return name;
	}
}
