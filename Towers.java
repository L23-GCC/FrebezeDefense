
public abstract class Towers {
	protected int cost;
	protected int range;
	protected int damage;
	protected int posX;
	protected int posY;
	protected int fireDelay;
	protected int curDelay = 0;
	protected int upgradeLevel;
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
	
	public int getFireDelay() {
		return fireDelay;
	}
	
	public int getCurDelay() {
		return curDelay;
	}
	
	public void incCurDelay() {
		if (curDelay == fireDelay) {
			curDelay = 0;
		}
		
		else if (curDelay != 0) {
			curDelay++;
		}
	}
	
	public void startDelay() {
		curDelay++;
	}
	
	public int getUpgradeLevel() {
		return upgradeLevel;
	}
	
	public abstract void upgrade();
	
	public abstract void deUpgrade();
	
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("Type: " + name + "\nCost: " + cost
				+ "\nDamage: " + damage + "\nRange: " + range
				+ "\nFireDelay: " + fireDelay);
		return data.toString();
	}
}
