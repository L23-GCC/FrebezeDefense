
public abstract class Towers {
	protected int cost;
	protected int range;
	protected int damage;
	protected int posX;
	protected int posY;
	protected int rateOfFire;
	protected String name;
	
	public int getDmg() {
		return damage;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getRange() {
		return range;
	}
	
	public int getRateOfFire() {
		return rateOfFire;
	}
	
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("Type: " + name + "\nCost: " + cost
				+ "\nDamage: " + damage + "\nRange: " + range
				+ "\nRoF: " + rateOfFire);
		return data.toString();
	}
}
