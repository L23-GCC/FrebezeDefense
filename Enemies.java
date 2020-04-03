
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
}
